package com.winwintek.web.backend.api.repository.admin.corporate.internAttendanceTracking;

import com.winwintek.web.backend.api.dto.admin.corporate.internAttendanceTracking.request.AttendanceTrackingReqDTO;
import com.winwintek.web.backend.api.entity.admin.corporate.internAttendanceTracking.AttendanceTrackingEntity;
import com.winwintek.web.backend.api.entity.admin.corporate.internAttendanceTracking.IAttendaceTrackingResponse;
import com.winwintek.web.backend.api.entity.admin.corporate.internAttendanceTracking.IAttendaceTrackingStudentResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceTrackingRepository extends JpaRepository<AttendanceTrackingEntity, String> {

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
    List<IAttendaceTrackingResponse> getLstAttendanceTracking(AttendanceTrackingReqDTO dto);

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
    Optional<IAttendaceTrackingStudentResponse> getAttendanceTrackingStuden(AttendanceTrackingReqDTO dto);


    @Query ("""
                SELECT ate
                FROM AttendanceTrackingEntity ate
                WHERE ate.internStdSeq = :internStdSeq
                AND ate.internDetailSeq = :internDetailSeq
                AND ate.delYn = :delYn
            """)
    List<AttendanceTrackingEntity> findLstAttendanceByStd(
            @Param("internStdSeq") String internStdSeq,
            @Param("internDetailSeq") String internDetailSeq,
            @Param("delYn") String delYn
    );


}
