package com.winwintek.web.backend.api.repository.client.student.reportWeek;

import com.winwintek.web.backend.api.entity.client.student.reportWeek.WeekReportInternStdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReportWeekInternStdRepository extends JpaRepository<WeekReportInternStdEntity, String> {

    Optional<WeekReportInternStdEntity> findByInternPlanSeqAndStdNoAndDelYnAndApproveStatus(
            String internStdSeq, String stdNo, String delYn, String approveStatus);

}
