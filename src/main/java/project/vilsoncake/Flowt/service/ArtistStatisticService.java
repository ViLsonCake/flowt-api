package project.vilsoncake.Flowt.service;

import project.vilsoncake.Flowt.dto.OverallStatisticDto;
import project.vilsoncake.Flowt.dto.SongDto;
import project.vilsoncake.Flowt.dto.SongStatisticDto;
import project.vilsoncake.Flowt.entity.SongEntity;

import java.util.List;

public interface ArtistStatisticService {
    OverallStatisticDto getOverallStatistic(String authHeader);
    SongStatisticDto getSongStatistic(SongEntity song);
    List<SongDto> getMostPopularSongs(String authHeader);
}
