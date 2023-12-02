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
public class OverallStatisticDto {
    private Long listens;
    private Long listeners;
    private Long songsCount;
    private Map<Country, Long> eachCountryListeningCount;
    private Map<Region, Long> eachRegionListeningCount;
}
