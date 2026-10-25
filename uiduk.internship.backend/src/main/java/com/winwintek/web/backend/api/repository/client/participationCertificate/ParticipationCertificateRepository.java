package com.winwintek.web.backend.api.repository.client.participationCertificate;

import com.winwintek.web.backend.api.dto.client.participationCertificate.IParticipationCertificateCusDTO;
import com.winwintek.web.backend.api.entity.system.VSregEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ParticipationCertificateRepository extends JpaRepository<VSregEntity, Long> {

    @Query (value = """
            SELECT
                FN_GET_NAME_KR(ist.STD_NO) AS studentName,
                vscs.SUST_CD_NM AS department,
                vscs.BIRTH_DT AS birthDate,
                cm.CORM_NAME_KR AS internshipOrganization,
                ipl.INTERNSHIP_STR AS internshipStr,
                ipl.INTERNSHIP_END AS internshipEnd,
                ipl.INTERNSHIP_FIELD AS internshipField,
                ist.MODIFY_DATE AS completionDate
            FROM INTERN_PLAN ipl
            LEFT JOIN INTERN_STD ist
                    ON ist.INTERN_PLAN_SEQ = ipl.INTERN_PLAN_SEQ
            LEFT JOIN V_STD_CDP_SREG vscs
                    ON vscs.STD_NO = ist.STD_NO
            LEFT JOIN CORPORATE_MEMBERS cm
                    ON cm.CORM_SEQ = ipl.CORM_SEQ
            WHERE ipl.DEL_YN = :delYn
            AND ipl.INTERN_PLAN_SEQ = :internPlanSeq
            AND ist.STD_NO = :userId
            """, nativeQuery = true)
    IParticipationCertificateCusDTO getParticipationCertificateDetail(@Param ("internPlanSeq") String internPlanSeq,
            @Param ("delYn") String delYn, @Param ("userId") String userId);

}