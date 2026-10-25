package com.winwintek.web.backend.api.repository.admin.attendanceTrackingCenter;

import com.winwintek.web.backend.api.dto.admin.attendanceTrackingCenter.request.AttendanceTrackingCenterReqDTO;
import com.winwintek.web.backend.api.entity.admin.attendanceTrackingCenter.AttendanceTrackingCenterEntity;
import com.winwintek.web.backend.api.entity.admin.attendanceTrackingCenter.IAttendaceTrackingCenterResponse;
import com.winwintek.web.backend.api.entity.admin.attendanceTrackingCenter.IAttendaceTrackingStudentCenterResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceTrackingCenterRepository extends JpaRepository<AttendanceTrackingCenterEntity, String> {
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
    List<IAttendaceTrackingCenterResponse> getLstAttendanceTracking(AttendanceTrackingCenterReqDTO dto);


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
    Optional<IAttendaceTrackingStudentCenterResponse> getAttendanceTrackingStudent(AttendanceTrackingCenterReqDTO dto);

    @Query ("""
                SELECT ate
                FROM AttendanceTrackingCenterEntity ate
                WHERE ate.internStdSeq = :internStdSeq
                AND ate.internDetailSeq = :internDetailSeq
                AND ate.delYn = :delYn
            """)
    List<AttendanceTrackingCenterEntity> findLstAttendanceByStd(
            @Param("internStdSeq") String internStdSeq,
            @Param("internDetailSeq") String internDetailSeq,
            @Param("delYn") String delYn
    );

}
