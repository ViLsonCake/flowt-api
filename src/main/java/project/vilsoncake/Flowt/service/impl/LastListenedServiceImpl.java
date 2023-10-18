package project.vilsoncake.Flowt.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.vilsoncake.Flowt.entity.LastListenedEntity;
import project.vilsoncake.Flowt.entity.SongEntity;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.repository.LastListenedRepository;
import project.vilsoncake.Flowt.service.LastListenedService;

import java.util.List;

import static project.vilsoncake.Flowt.constant.NumberConst.LAST_LISTENED_SONG_COUNT;

@Service
@Slf4j
@RequiredArgsConstructor
public class LastListenedServiceImpl implements LastListenedService {

    private final LastListenedRepository lastListenedRepository;

    @Override
    public boolean addSongToLastListenedByUser(UserEntity user, SongEntity song) {
        LastListenedEntity lastListenedEntity = lastListenedRepository.findByUser(user);
        List<SongEntity> lastListenedSongs = lastListenedEntity.getSongs();
        if (!isSongAlreadyInLastListened(song, user)) {
            lastListenedSongs.add(song);
        }
        if (lastListenedSongs.size() > LAST_LISTENED_SONG_COUNT) {
            lastListenedSongs.remove(0);
        }
        lastListenedEntity.setSongs(lastListenedSongs);
        lastListenedRepository.save(lastListenedEntity);
        return true;
    }

    @Override
    public boolean isSongAlreadyInLastListened(SongEntity newSong, UserEntity user) {
        for (SongEntity song : user.getLastListened().getSongs()) {
            if (song.getSongId().equals(newSong.getSongId())) {
                return true;
            }
        }
        return false;
    }
}
