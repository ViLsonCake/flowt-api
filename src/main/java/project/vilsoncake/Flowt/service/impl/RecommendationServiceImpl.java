package project.vilsoncake.Flowt.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.vilsoncake.Flowt.dto.SongDto;
import project.vilsoncake.Flowt.entity.SongEntity;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.entity.enumerated.Genre;
import project.vilsoncake.Flowt.service.ListenedService;
import project.vilsoncake.Flowt.service.RecommendationService;
import project.vilsoncake.Flowt.service.SongService;
import project.vilsoncake.Flowt.service.UserService;
import project.vilsoncake.Flowt.utils.AuthUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private final UserService userService;
    private final SongService songService;
    private final ListenedService listenedService;
    private final AuthUtils authUtils;

    @Override
    public List<SongDto> getSongsMightLike(String authHeader) {
        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = userService.getUserByUsername(username);
        List<String> mostListenedArtists = listenedService.getMostListenedArtists(user);
        List<SongDto> songsMightLike = new ArrayList<>();

        mostListenedArtists.forEach(mostListenedArtist -> {
            UserEntity artist = userService.getUserByUsername(mostListenedArtist);
            SongEntity randomSong;
            int iteratedSongCount = 0;
            int artistSongCount = artist.getSongs().size();
            do {
                randomSong = songService.getRandomUserSong(artist);
                iteratedSongCount++;
            } while (iteratedSongCount >= artistSongCount || !listenedService.existsBySongs(randomSong));

            if (iteratedSongCount < artistSongCount) {
                songsMightLike.add(SongDto.fromSongEntity(randomSong));
            }
        });

        return songsMightLike;
    }

    @Override
    public List<SongDto> getRecommendationSongsByTopGenre(String authHeader) {
        String username = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity user = userService.getUserByUsername(username);
        List<Genre> mostListenedGenres = listenedService.getMostListenedGenres(user);
        List<SongEntity> recommendationSongs = songService.getRandomMostListenedSongsByGenres(mostListenedGenres);

        return recommendationSongs.stream().map(SongDto::fromSongEntity).toList();
    }
}
