package project.vilsoncake.Flowt.service;

import project.vilsoncake.Flowt.dto.ReportPageDto;
import project.vilsoncake.Flowt.dto.SendReportDto;
import project.vilsoncake.Flowt.entity.ReportEntity;
import project.vilsoncake.Flowt.entity.enumerated.WhomReportType;

import java.util.List;
import java.util.Map;

public interface ReportService {
    Map<String, String> sendReportToEntity(String authHeader, SendReportDto sendReportDto);
    ReportPageDto getReportsByType(WhomReportType whomReportType, int page, int size);
    Iterable<ReportEntity> getAllReports();
    Map<String, String> accessReportById(Long id);
    Map<String, String> cancelReportById(Long id);
    boolean removeAvatarFromEntityFromReport(ReportEntity report);
}
