package project.vilsoncake.Flowt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.vilsoncake.Flowt.entity.ReportEntity;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportPageDto {
    private int count;
    private List<ReportEntity> reports;
}
