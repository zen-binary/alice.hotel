package com.winwintek.web.backend.api.repository.admin.attendanceTrackingCenter;

import com.winwintek.web.backend.api.dto.admin.attendanceTrackingCenter.request.AttendanceTrackingCenterReqDTO;
import com.winwintek.web.backend.api.entity.admin.attendanceTrackingCenter.AttendanceStudentCenterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttendanceStudentCenterRepository extends JpaRepository<AttendanceStudentCenterEntity, String> {

    @Query ("""
                select s
                from AttendanceStudentCenterEntity s
                where
                    s.internStdSeq = :#{#dto.internStdSeq}
                and s.delYn = :#{#dto.delYn}
            """)
    Optional<AttendanceStudentCenterEntity> findByInternPlanAndStudent(AttendanceTrackingCenterReqDTO dto);
}
