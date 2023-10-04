package project.vilsoncake.Flowt.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import project.vilsoncake.Flowt.entity.ReportEntity;
import project.vilsoncake.Flowt.entity.enumerated.WhomReportType;

public interface ReportRepository extends CrudRepository<ReportEntity, Long> {
    Page<ReportEntity> findAllByWhomType(WhomReportType whomReportType, Pageable pageable);
}
