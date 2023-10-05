package project.vilsoncake.Flowt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.vilsoncake.Flowt.dto.ReportPageDto;
import project.vilsoncake.Flowt.dto.SendReportDto;
import project.vilsoncake.Flowt.entity.enumerated.WhomReportType;
import project.vilsoncake.Flowt.service.ReportService;

import java.util.Map;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping
    public ResponseEntity<Map<String, String>> sendReport(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
            @RequestBody SendReportDto sendReportDto
    ) {
        return ResponseEntity.ok(reportService.sendReportToEntity(authHeader, sendReportDto));
    }

    @GetMapping("/{type}")
    public ResponseEntity<ReportPageDto> getReportsByType(
            @PathVariable("type") WhomReportType type,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(reportService.getReportsByType(type, page, size));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, String>> accessReport(@PathVariable("id") Long id) {
        return ResponseEntity.ok(reportService.accessReportById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> cancelReport(@PathVariable("id") Long id) {
        return ResponseEntity.ok(reportService.cancelReportById(id));
    }
}
