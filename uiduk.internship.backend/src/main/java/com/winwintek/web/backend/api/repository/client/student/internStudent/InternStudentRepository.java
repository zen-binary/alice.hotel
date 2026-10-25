package com.winwintek.web.backend.api.repository.client.student.internStudent;

import com.winwintek.web.backend.api.dto.client.student.internStudent.InternStudentReqDTO;
import com.winwintek.web.backend.api.entity.client.student.internStudent.IInternStudentRegOutput;
import com.winwintek.web.backend.api.entity.client.student.internStudent.InternStudentEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InternStudentRepository extends JpaRepository<InternStudentEntity, String> {

    boolean existsByStdNoAndInternPlanSeqAndApproveStatusAndDelYn(String stdNo, String internPlanSeq,
            String approveStatus, String delYn);

    List<InternStudentEntity> findByStdNoAndInternPlanSeqAndDelYn(String stdno, String internPlanSeq, String delYn);

    @Query (value = """
                SELECT
                    IS2.INTERN_STD_SEQ AS internStdSeq,
                    IP.INTERN_PLAN_NM AS internshipName,
                    IS2.INTERN_PLAN_SEQ as internPlanSeq,
                    CM.CORM_NAME_KR AS cormName,
                    ip.INTERNSHIP_FIELD AS internshipField,
                    cm.ADDRESS AS address,
                    IS2.APPROVE_STATUS as approveStatusCd,
                    FN_GET_CODE_NAME(IS2.APPROVE_STATUS) AS approveStatusNm,
                    IS2.INTERNSHIP_STATUS as internshipStatusCd,
                    FN_GET_CODE_NAME(IS2.INTERNSHIP_STATUS) AS internshipStatusNm,
                    ip.INTERNSHIP_STR AS internshipStr,
                    IP.INTERNSHIP_END AS internshipEnd,
                    TA.CORPORATE_SIGNED_YN AS corporateSignYn,
                    TA.TRIPARTITE_AGREEMENT_SEQ as tripartiteAgreementSeq
                FROM
                    INTERN_STD is2
                LEFT JOIN
                    INTERN_PLAN ip
                ON IP.INTERN_PLAN_SEQ = IS2.INTERN_PLAN_SEQ
                LEFT JOIN
                    CORPORATE_MEMBERS cm
                ON ip.CORM_SEQ = CM.CORM_SEQ
                LEFT JOIN
                	TRIPARTITE_AGREEMENT ta 
                ON IS2.INTERN_STD_SEQ = TA.INTERN_STD_SEQ
                WHERE
                    IS2.DEL_YN = :delYn AND
                    IS2.STD_NO = :regId
                    AND (:#{#req.selectedYear} IS NULL OR EXTRACT(YEAR FROM IS2.REG_DATE) = :#{#req.selectedYear})
                    AND (:#{#req.internshipStatus} IS NULL OR IS2.INTERNSHIP_STATUS = :#{#req.internshipStatus})
                    AND (:#{#req.internshipName} IS NULL OR LOWER(IP.INTERN_PLAN_NM) LIKE LOWER(('%' || :#{#req.internshipName} || '%')))
                ORDER BY
                    EXTRACT(YEAR FROM IS2.REG_DATE) DESC,
                    IS2.INTERN_STD_SEQ DESC 
            """, nativeQuery = true)
    List<IInternStudentRegOutput> getInternStudentList(@Param ("req") InternStudentReqDTO req,
            @Param ("delYn") String delYn, @Param ("regId") String regId);

    Optional<InternStudentEntity> findByInternStdSeq(String id);

    @Modifying
    @Transactional
    @Query ("UPDATE InternStudentEntity i SET i.approveStatus = :newStatus WHERE i.internStdSeq = :id")
    int updateByApproveStatus(String id, String newStatus);

    @Query (value = "SELECT DISTINCT EXTRACT(YEAR FROM REG_DATE) AS internshipYear " + "FROM INTERN_STD " + "WHERE DEL_YN = :delYn " + "AND REG_ID = :regId " + "ORDER BY internshipYear DESC", nativeQuery = true)
    List<String> findAvailableInternshipYearsByStudent(@Param ("regId") String stdNo, @Param ("delYn") String delYn);


}
