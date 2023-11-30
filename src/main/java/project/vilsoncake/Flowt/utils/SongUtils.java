package project.vilsoncake.Flowt.utils;

import org.springframework.stereotype.Component;
import project.vilsoncake.Flowt.entity.SongEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class SongUtils {

    public List<SongEntity> getRandomSongsFromList(List<SongEntity> songs, int count) {
        List<SongEntity> randomSongs = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            int randomIndex;
            do {
                randomIndex = new Random().nextInt(songs.size());
            } while (!songs.contains(songs.get(randomIndex)));
            randomSongs.add(songs.get(randomIndex));
        }

        return randomSongs;
    }

    public List<SongEntity> getRandomSongsFromList(List<SongEntity> songs) {
        return getRandomSongsFromList(songs, 5);
    }
}
