package project.vilsoncake.Flowt.service.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.vilsoncake.Flowt.entity.ReportEntity;
import project.vilsoncake.Flowt.service.*;
import project.vilsoncake.Flowt.utils.DateUtils;

import static java.util.concurrent.TimeUnit.HOURS;
import static project.vilsoncake.Flowt.constant.NumberConst.AUTO_BLOCK_DELAY_IN_HOURS;
import static project.vilsoncake.Flowt.entity.enumerated.WhomReportType.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduledServiceImpl implements ScheduledService {

    private final ChangeUserService changeUserService;
    private final SongService songService;
    private final PlaylistService playlistService;
    private final ReportService reportService;
    private final DateUtils dateUtils;

    @Transactional
    @PostConstruct
    @Scheduled(fixedDelay = AUTO_BLOCK_DELAY_IN_HOURS, timeUnit = HOURS)
    @Override
    public void autoBlockEntities() {
        Iterable<ReportEntity> reports = reportService.getAllReports();

        reports.forEach(report -> {
            if (report.isChecked() && dateUtils.isWaitingPeriodExpired(report.getCreatedAt())) {
                if (report.getWhomType().equals(USER)) {
                    changeUserService.changeUserActive(report.getWhom().getUsername());
                    log.info("User \"{}\" has been blocked", report.getWhom().getUsername());
                    reportService.cancelReportById(report.getId());
                } else if (report.getWhomType().equals(SONG)) {
                    songService.removeUserSongByUserAndName(report.getWhom(), report.getContentTypeName());
                    log.info("Song \"{}\" by user \"{}\" has been deleted", report.getContentTypeName(), report.getWhom().getUsername());
                    reportService.cancelReportById(report.getId());
                } else if (report.getWhomType().equals(PLAYLIST)) {
                    playlistService.removePlaylistByUserAndName(report.getWhom(), report.getContentTypeName());
                    log.info("Playlist \"{}\" by user \"{}\" has been deleted", report.getContentTypeName(), report.getWhom().getUsername());
                    reportService.cancelReportById(report.getId());
                }
            }
        });
    }
}
