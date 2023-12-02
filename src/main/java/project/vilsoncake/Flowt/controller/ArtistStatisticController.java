package project.vilsoncake.Flowt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.vilsoncake.Flowt.dto.OverallStatisticDto;
import project.vilsoncake.Flowt.dto.SongDto;
import project.vilsoncake.Flowt.service.ArtistStatisticService;

import java.util.List;

@RestController
@RequestMapping("/artist-statistic")
@RequiredArgsConstructor
public class ArtistStatisticController {

    private final ArtistStatisticService artistStatisticService;

    @GetMapping("/overall")
    public ResponseEntity<OverallStatisticDto> getOverallStatistic(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader) {
        return ResponseEntity.ok(artistStatisticService.getOverallStatistic(authHeader));
    }

    @GetMapping("/popular")
    public ResponseEntity<List<SongDto>> getMostPopularSongs(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader) {
        return ResponseEntity.ok(artistStatisticService.getMostPopularSongs(authHeader));
    }
}
