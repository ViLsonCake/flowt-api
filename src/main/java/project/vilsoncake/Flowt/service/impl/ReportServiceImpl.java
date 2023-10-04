package project.vilsoncake.Flowt.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import project.vilsoncake.Flowt.dto.ReportPageDto;
import project.vilsoncake.Flowt.dto.SendReportDto;
import project.vilsoncake.Flowt.entity.ReportEntity;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.entity.enumerated.WhomReportType;
import project.vilsoncake.Flowt.repository.ReportRepository;
import project.vilsoncake.Flowt.service.ReportService;
import project.vilsoncake.Flowt.service.UserService;
import project.vilsoncake.Flowt.utils.AuthUtils;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final AuthUtils authUtils;
    private final UserService userService;
    private final ReportRepository reportRepository;

    @Override
    public Map<String, String> sendReportToEntity(String authHeader, SendReportDto sendReportDto) {
        String senderUsername = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity sender = userService.getUserByUsername(senderUsername);
        UserEntity whomSentReport = userService.getUserByUsername(sendReportDto.getWhomName());

        ReportEntity report = new ReportEntity(
                sendReportDto.getWhomType(),
                sendReportDto.getContentType(),
                whomSentReport,
                sender
        );
        reportRepository.save(report);
        return Map.of("message", "Report added");
    }

    @Override
    public ReportPageDto getReportsByType(WhomReportType whomReportType, int page, int size) {
        Page<ReportEntity> usersReports = reportRepository.findAllByWhomType(WhomReportType.USER, PageRequest.of(page, size));

        return new ReportPageDto(
                usersReports.getTotalPages(),
                usersReports.getContent()
        );
    }
}
