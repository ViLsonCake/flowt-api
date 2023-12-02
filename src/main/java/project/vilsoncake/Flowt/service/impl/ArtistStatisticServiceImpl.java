package project.vilsoncake.Flowt.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.vilsoncake.Flowt.dto.OverallStatisticDto;
import project.vilsoncake.Flowt.dto.SongDto;
import project.vilsoncake.Flowt.dto.SongStatisticDto;
import project.vilsoncake.Flowt.entity.ListeningEntity;
import project.vilsoncake.Flowt.entity.SongEntity;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.entity.enumerated.Country;
import project.vilsoncake.Flowt.entity.enumerated.Region;
import project.vilsoncake.Flowt.exception.ArtistVerifyRequiredException;
import project.vilsoncake.Flowt.service.ArtistStatisticService;
import project.vilsoncake.Flowt.service.SongService;
import project.vilsoncake.Flowt.service.UserService;
import project.vilsoncake.Flowt.utils.AuthUtils;
import project.vilsoncake.Flowt.utils.SongUtils;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ArtistStatisticServiceImpl implements ArtistStatisticService {

    private final UserService userService;
    private final SongService songService;
    private final AuthUtils authUtils;
    private final SongUtils songUtils;

    @Override
    public OverallStatisticDto getOverallStatistic(String authHeader) {
        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = userService.getUserByUsername(username);

        if (!user.isArtistVerify()) {
            throw new ArtistVerifyRequiredException("Artist not verified");
        }

        Map<Country, Long> eachCountryListeningCount = new HashMap<>();
        Map<Region, Long> eachRegionListeningCount = new HashMap<>();
        Long listens = 0L;
        Long listeners = 0L;

        for (SongEntity song : user.getSongs()) {
            SongStatisticDto songStatistic = getSongStatistic(song);
            listens += song.getListens();
            listeners += songStatistic.getListeners();
            eachCountryListeningCount = songUtils.mergeCountryStatistic(eachCountryListeningCount, songStatistic.getEachCountryListeningCount());
            eachRegionListeningCount = songUtils.mergeRegionStatistic(eachRegionListeningCount, songStatistic.getEachRegionListeningCount());
        }

        return new OverallStatisticDto(
                listens,
                listeners,
                (long) user.getSongs().size(),
                eachCountryListeningCount,
                eachRegionListeningCount
        );
    }

    @Override
    public SongStatisticDto getSongStatistic(SongEntity song) {
        List<ListeningEntity> listeningEntities = song.getRegionStatistic().getListeningEntities();
        Map<Country, Long> eachCountryListeningCount = new HashMap<>();
        Map<Region, Long> eachRegionListeningCount = new HashMap<>();
        Set<UserEntity> uniqueListeners = new HashSet<>();

        listeningEntities.forEach(listeningEntity -> {
            UserEntity listener = userService.getUserByUsername(listeningEntity.getUsername());
            uniqueListeners.add(listener);

            if (!eachCountryListeningCount.containsKey(listeningEntity.getCountry())) {
                eachCountryListeningCount.put(listeningEntity.getCountry(), 0L);
            } else {
                eachCountryListeningCount.put(listeningEntity.getCountry(), eachCountryListeningCount.get(listeningEntity.getCountry()) + 1);
            }

            if (!eachRegionListeningCount.containsKey(listeningEntity.getRegion())) {
                eachRegionListeningCount.put(listeningEntity.getRegion(), 0L);
            } else {
                eachRegionListeningCount.put(listeningEntity.getRegion(), eachRegionListeningCount.get(listeningEntity.getRegion()) + 1);
            }
        });

        return new SongStatisticDto(
                song.getListens(),
                (long) uniqueListeners.size(),
                eachCountryListeningCount,
                eachRegionListeningCount,
                song.getName(),
                song.getUser().getUsername()
        );
    }

    @Override
    public List<SongDto> getMostPopularSongs(String authHeader) {
        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = userService.getUserByUsername(username);

        if (!user.isArtistVerify()) {
            throw new ArtistVerifyRequiredException("Artist not verified");
        }

        return songService.getMostPopularSongs(user).stream().map(SongDto::fromSongEntity).toList();
    }
}
