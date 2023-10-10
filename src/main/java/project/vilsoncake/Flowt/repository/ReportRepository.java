package project.vilsoncake.Flowt.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import project.vilsoncake.Flowt.entity.ReportEntity;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.entity.enumerated.ReportContentType;
import project.vilsoncake.Flowt.entity.enumerated.WhomReportType;

import java.util.List;

public interface ReportRepository extends CrudRepository<ReportEntity, Long> {
    Page<ReportEntity> findAllByWhomType(WhomReportType whomReportType, Pageable pageable);
    List<ReportEntity> findAllByWhomTypeAndContentTypeAndContentTypeNameAndWhom(WhomReportType whomReportType, ReportContentType reportContentType, String contentTypeName, UserEntity whom);
    List<ReportEntity> findAllByWhomTypeAndContentTypeAndWhom(WhomReportType whomReportType, ReportContentType reportContentType, UserEntity whom);
}
