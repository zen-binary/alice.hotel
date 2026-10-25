package com.winwintek.web.backend.api.repository.client.student.reportWeek;

import com.winwintek.web.backend.api.entity.client.student.reportWeek.WeekReportStdAttendanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeekReportStdAttendanceRepository extends JpaRepository<WeekReportStdAttendanceEntity, String> {

    List<WeekReportStdAttendanceEntity> findAllByInternStdSeqAndInternDetailSeqAndDelYn(String internStdSeq,
            String internDetailSeq,
            String delYn);

}
