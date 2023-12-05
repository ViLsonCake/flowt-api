package project.vilsoncake.Flowt.dto;

import lombok.Data;
import project.vilsoncake.Flowt.entity.enumerated.ReportContentType;
import project.vilsoncake.Flowt.entity.enumerated.WhomReportType;

@Data
public class SendReportDto {
    private WhomReportType whomType;
    private ReportContentType contentType;
    private String contentTypeName;
    private String whomName;
}
