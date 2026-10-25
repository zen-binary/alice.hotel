package com.winwintek.web.backend.api.repository.admin.corporate.internAttendanceTracking;

import com.winwintek.web.backend.api.dto.admin.corporate.internAttendanceTracking.request.AttendanceTrackingReqDTO;
import com.winwintek.web.backend.api.entity.admin.corporate.internAttendanceTracking.AttendanceStudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceStudentRepository extends JpaRepository<AttendanceStudentEntity, String> {

    @Query ("""
                select s
                from AttendanceStudentEntity s
                where
                    s.internStdSeq = :#{#dto.internStdSeq}
                and s.delYn = :#{#dto.delYn}
            """)
    Optional<AttendanceStudentEntity> findByInternPlanAndStudent(AttendanceTrackingReqDTO dto);

}
