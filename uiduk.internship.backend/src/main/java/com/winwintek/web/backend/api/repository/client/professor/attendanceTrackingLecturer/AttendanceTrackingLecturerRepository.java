package com.winwintek.web.backend.api.repository.client.professor.attendanceTrackingLecturer;

import com.winwintek.web.backend.api.dto.client.professor.attendanceTrackingLecturer.request.AttendanceTrackingLecturerReqDTO;
import com.winwintek.web.backend.api.entity.client.professor.attendanceTrackingLecturer.AttendanceTrackingLecturerEntity;
import com.winwintek.web.backend.api.entity.client.professor.attendanceTrackingLecturer.IAttendanceTrackingLecturerResponse;
import com.winwintek.web.backend.api.entity.client.professor.attendanceTrackingLecturer.IAttendanceTrackingStudentLecturerResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceTrackingLecturerRepository extends JpaRepository<AttendanceTrackingLecturerEntity, String> {

    @Query (value = """
            SELECT
                sa.STD_ATTENDANCE_SEQ,
                sa.INTERN_DETAIL_SEQ,
                TO_CHAR(sa.ATTENDANCE_DATE, 'YYYY-MM-DD') AS ATTENDANCE_DATE,
                sa.STATUS
            FROM
            	STD_ATTENDANCE sa
            JOIN INTERN_STD iss
                ON iss.INTERN_STD_SEQ = sa.INTERN_STD_SEQ
            JOIN INTERN_PLAN_DETAIL ipd
                ON ipd.INTERN_DETAIL_SEQ = sa.INTERN_DETAIL_SEQ
            WHERE iss.INTERN_STD_SEQ = :#{#dto.internStdSeq}
            AND ipd.DEL_YN = :#{#dto.delYn}
            AND iss.DEL_YN = :#{#dto.delYn}
            AND sa.DEL_YN = :#{#dto.delYn}
            """, nativeQuery = true)
    List<IAttendanceTrackingLecturerResponse> getLstAttendanceTracking(AttendanceTrackingLecturerReqDTO dto);


    @Query (value = """
            SELECT
                vscs.COLG_CD_NM AS colgNm,
                vscs.SUST_CD_NM AS sustNm,
                vscs.MJ_CD_NM AS majorNm,
                vscs.STD_NO,
                vscs.KOR_NM,
                cm.CORM_NAME_KR,
                ip.INTERNSHIP_STR,
                ip.INTERNSHIP_END
            FROM INTERN_STD iss
            JOIN INTERN_PLAN ip
                ON iss.INTERN_PLAN_SEQ = ip.INTERN_PLAN_SEQ
            JOIN CORPORATE_MEMBERS cm
                ON cm.CORM_SEQ = ip.CORM_SEQ
            JOIN V_STD_CDP_SREG vscs
                ON vscs.STD_NO = iss.STD_NO
            WHERE
                iss.INTERN_STD_SEQ = :#{#dto.internStdSeq}
                AND ip.DEL_YN = :#{#dto.delYn}
                AND iss.DEL_YN = :#{#dto.delYn}
                AND cm.DEL_YN = :#{#dto.delYn}
            """, nativeQuery = true)
    Optional<IAttendanceTrackingStudentLecturerResponse> getAttendanceTrackingStudent(
            AttendanceTrackingLecturerReqDTO dto);

}
