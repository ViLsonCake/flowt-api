package project.vilsoncake.Flowt.utils;

import org.springframework.stereotype.Component;
import project.vilsoncake.Flowt.entity.SongEntity;
import project.vilsoncake.Flowt.entity.enumerated.Country;
import project.vilsoncake.Flowt.entity.enumerated.Region;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    public Map<Country, Long> mergeCountryStatistic(Map<Country, Long> map1, Map<Country, Long> map2) {
        map2.keySet().forEach(key -> {
            if (!map1.containsKey(key)) {
                map1.put(key, 0L);
            } else {
                map1.put(key, map1.get(key) + map2.get(key));
            }
        });

        return map1;
    }

    public Map<Region, Long> mergeRegionStatistic(Map<Region, Long> map1, Map<Region, Long> map2) {
        map2.keySet().forEach(key -> {
            if (!map1.containsKey(key)) {
                map1.put(key, 0L);
            } else {
                map1.put(key, map1.get(key) + map2.get(key));
            }
        });

        return map1;
    }
}
