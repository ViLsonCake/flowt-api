package project.vilsoncake.Flowt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.vilsoncake.Flowt.entity.enumerated.Country;
import project.vilsoncake.Flowt.entity.enumerated.Region;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongStatisticDto {
    private Long listens;
    private Long listeners;
    private Map<Country, Long> eachCountryListeningCount;
    private Map<Region, Long> eachRegionListeningCount;
    private String songName;
    private String author;
}
