package project.vilsoncake.Flowt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.vilsoncake.Flowt.dto.SongDto;
import project.vilsoncake.Flowt.service.RecommendationService;

import java.util.List;

@RestController
@RequestMapping("/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping
    public ResponseEntity<List<SongDto>> getRecommendationSongs(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader) {
        return ResponseEntity.ok(recommendationService.getRecommendationSongsByTopGenre(authHeader));
    }

    @GetMapping("/might-like")
    public ResponseEntity<List<SongDto>> getMightLikeSongs(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader) {
        return ResponseEntity.ok(recommendationService.getSongsMightLike(authHeader));
    }
}
