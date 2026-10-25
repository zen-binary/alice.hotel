package com.winwintek.web.backend.api.repository.common.stdIntern;

import com.winwintek.web.backend.api.entity.common.stdIntern.IStdInternAdminResponse;
import com.winwintek.web.backend.api.entity.common.stdIntern.IStdInternClientResponse;
import com.winwintek.web.backend.api.entity.common.stdIntern.StudentInternEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentInternRepository extends JpaRepository<StudentInternEntity, String> {

    @Query (value = """
                SELECT 
                    istd.INTERN_STD_SEQ AS id,
                    vscs.KOR_NM AS name
                FROM 
                    INTERN_STD istd JOIN V_STD_CDP_SREG vscs ON vscs.STD_NO = istd.STD_NO
                WHERE istd.DEL_YN = :delYn
                    AND istd.APPROVE_STATUS = :approveStatus
                    AND istd.INTERN_PLAN_SEQ = :internPlanId
                ORDER BY vscs.KOR_NM ASC
            """, nativeQuery = true)
    List<IStdInternAdminResponse> getListStudentReportAdmin(@Param ("internPlanId") String internPlanId,
            @Param ("approveStatus") String approveStatus, @Param ("delYn") String delYn);

    @Query (value = """
                SELECT 
                    istd.INTERN_STD_SEQ AS id,
                    vscs.KOR_NM AS name
                FROM 
                    INTERN_STD istd JOIN V_STD_CDP_SREG vscs ON vscs.STD_NO = istd.STD_NO
                WHERE istd.DEL_YN = :delYn
                    AND istd.APPROVE_STATUS = :approveStatus
                    AND istd.INTERN_PLAN_SEQ = :internPlanId
                ORDER BY vscs.KOR_NM ASC
            """, nativeQuery = true)
    List<IStdInternClientResponse> getListStudentReportClient(@Param ("internPlanId") String internPlanId,
            @Param ("approveStatus") String approveStatus, @Param ("delYn") String delYn);

}
