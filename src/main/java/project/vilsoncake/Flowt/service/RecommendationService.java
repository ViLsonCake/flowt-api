package project.vilsoncake.Flowt.service;

import project.vilsoncake.Flowt.dto.SongDto;

import java.util.List;

public interface RecommendationService {
    List<SongDto> getSongsMightLike(String authHeader);
}
