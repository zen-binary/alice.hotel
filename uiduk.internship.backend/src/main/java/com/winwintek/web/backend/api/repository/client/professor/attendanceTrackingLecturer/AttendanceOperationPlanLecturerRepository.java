package com.winwintek.web.backend.api.repository.client.professor.attendanceTrackingLecturer;


import com.winwintek.web.backend.api.entity.client.professor.attendanceTrackingLecturer.AttendanceOperationPlanLecturerEntity;
import com.winwintek.web.backend.api.entity.client.professor.attendanceTrackingLecturer.IAttendanceOperationPlanLecturerResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceOperationPlanLecturerRepository
        extends JpaRepository<AttendanceOperationPlanLecturerEntity, String> {

    @Query (value = """
                SELECT
                    ipd.INTERN_DETAIL_SEQ,
                    ipd.INTERN_PLAN_SEQ,
                    ipd.WEEK,
                    TO_CHAR(ipd.START_DATE, 'YYYY-MM-DD') AS START_DATE,
                    TO_CHAR(ipd.END_DATE, 'YYYY-MM-DD') AS END_DATE,
                    ip.INTERNSHIP_DAY
                FROM
                    INTERN_PLAN_DETAIL ipd
                JOIN INTERN_PLAN ip
                    ON ip.INTERN_PLAN_SEQ = ipd.INTERN_PLAN_SEQ
                WHERE
                    ipd.INTERN_PLAN_SEQ = :internPlanSeq
                    AND ipd.DEL_YN = :delYn
                ORDER BY ipd.WEEK ASC
            """, nativeQuery = true)
    List<IAttendanceOperationPlanLecturerResponse> getAttendanceOperationPlan(
            @Param ("internPlanSeq") String internPlanSeq, @Param ("delYn") String delYn);

}
