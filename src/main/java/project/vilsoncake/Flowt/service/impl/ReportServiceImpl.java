package project.vilsoncake.Flowt.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import project.vilsoncake.Flowt.dto.ReportPageDto;
import project.vilsoncake.Flowt.dto.SendReportDto;
import project.vilsoncake.Flowt.entity.ReportEntity;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.entity.enumerated.ReportContentType;
import project.vilsoncake.Flowt.entity.enumerated.WhomReportType;
import project.vilsoncake.Flowt.exception.ReportNotFoundException;
import project.vilsoncake.Flowt.repository.ReportRepository;
import project.vilsoncake.Flowt.service.*;
import project.vilsoncake.Flowt.utils.AuthUtils;

import java.util.Date;
import java.util.Map;

import static project.vilsoncake.Flowt.entity.enumerated.ReportContentType.AVATAR;
import static project.vilsoncake.Flowt.entity.enumerated.ReportContentType.PROFILE_HEADER;
import static project.vilsoncake.Flowt.entity.enumerated.WhomReportType.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final AuthUtils authUtils;
    private final UserService userService;
    private final UserManagementService userManagementService;
    private final SongService songService;
    private final PlaylistService playlistService;
    private final UserVerifyService userVerifyService;
    private final ProfileHeaderService profileHeaderService;
    private final ReportRepository reportRepository;

    @Override
    public Map<String, String> sendReportToEntity(String authHeader, SendReportDto sendReportDto) {
        String senderUsername = authUtils.getUsernameFromAuthHeader(authHeader);
        UserEntity sender = userService.getUserByUsername(senderUsername);
        UserEntity whomSentReport = userService.getUserByUsername(sendReportDto.getWhomName());

        ReportEntity report = new ReportEntity(
                sendReportDto.getWhomType(),
                sendReportDto.getContentType(),
                sendReportDto.getContentTypeName(),
                whomSentReport,
                sender
        );
        reportRepository.save(report);
        return Map.of("message", "Report added");
    }

    @Override
    public ReportPageDto getReportsByType(WhomReportType whomReportType, int page, int size) {
        Page<ReportEntity> reports = reportRepository.findAllByWhomType(whomReportType, PageRequest.of(page, size));

        return new ReportPageDto(
                reports.getTotalPages(),
                reports.getContent()
        );
    }

    @Override
    public Iterable<ReportEntity> getAllReports() {
        return reportRepository.findAll();
    }

    @Transactional
    @Override
    public Map<String, String> accessReportById(Long id) {
        if (reportRepository.findById(id).isEmpty()) {
            throw new ReportNotFoundException(String.format("Report with id %s not found", id));
        }
        ReportEntity report = reportRepository.findById(id).get();

        if (report.getContentType().equals(AVATAR)) {
            removeAvatarFromEntityFromReport(report);
        }
        if (report.getContentType().equals(PROFILE_HEADER)) {
            profileHeaderService.removeByUser(report.getWhom());
        }
        userVerifyService.sendWarningMessage(report);
        report.setChecked(true);
        report.setCheckedAt(new Date());
        reportRepository.save(report);

        return Map.of("message", "Report checked");
    }

    @Transactional
    @Override
    public Map<String, String> cancelReportById(Long id) {
        if (reportRepository.findById(id).isEmpty()) {
            throw new ReportNotFoundException(String.format("Report with id %s not found", id));
        }

        reportRepository.deleteById(id);
        return Map.of("message", String.format("Report with id %s canceled", id));
    }

    @Override
    public boolean cancelReportByWhomTypeAndContentTypeAndContentTypeNameAndWhom(WhomReportType whomReportType, ReportContentType reportContentType, String contentTypeName, UserEntity whom) {
        reportRepository.deleteAll(reportRepository.findAllByWhomTypeAndContentTypeAndContentTypeNameAndWhom(whomReportType, reportContentType, contentTypeName, whom));
        return true;
    }

    @Override
    public boolean cancelReportByWhomTypeAndContentTypeAndWhom(WhomReportType whomReportType, ReportContentType reportContentType, UserEntity whom) {
        reportRepository.deleteAll(reportRepository.findAllByWhomTypeAndContentTypeAndWhom(whomReportType ,reportContentType, whom));
        return true;
    }

    @Transactional
    @Override
    public boolean removeAvatarFromEntityFromReport(ReportEntity report) {
        if (report.getWhomType().equals(USER)) {
            userManagementService.removeUserAvatar(report.getWhom());
            log.info("Remove user \"{}\" avatar", report.getWhom().getUsername());
            return true;
        } else if (report.getWhomType().equals(SONG)) {
            songService.removeSongAvatarByUserAndName(report.getWhom(), report.getContentTypeName());
            log.info("Remove song \"{}\" by user \"{}\" avatar", report.getContentTypeName(), report.getWhom().getUsername());
            return true;
        } else if (report.getWhomType().equals(PLAYLIST)) {
            playlistService.removePlaylistAvatarByUserAndName(report.getWhom(), report.getContentTypeName());
            log.info("Remove playlist \"{}\" by user \"{}\" avatar", report.getContentTypeName(), report.getWhom().getUsername());
            return true;
        }
        return false;
    }
}
