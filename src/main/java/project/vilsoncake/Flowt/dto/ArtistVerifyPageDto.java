package project.vilsoncake.Flowt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.vilsoncake.Flowt.entity.ArtistVerifyRequestEntity;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArtistVerifyPageDto {
    private int count;
    private List<ArtistVerifyRequestEntity> artistVerifyRequests;
}
