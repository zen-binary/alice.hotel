package com.winwintek.web.backend.api.repository.client.internMng;

import com.winwintek.web.backend.api.dto.client.internMng.InternMngSearchDTO;
import com.winwintek.web.backend.api.entity.client.internMng.IInternMngResponeListDTO;
import com.winwintek.web.backend.api.entity.client.internMng.InternMngEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IInternMngRepository extends JpaRepository<InternMngEntity, String> {

    @Query(value = """
         SELECT
            ipt.INTERN_PLAN_SEQ,
            ipt.INTERN_PLAN_NM ,
            ipt.INTERNSHIP_FIELD ,
            TO_CHAR(ipt.INTERNSHIP_STR, 'YYYY-MM-DD') AS INTERNSHIP_STR ,
            TO_CHAR(ipt.INTERNSHIP_END, 'YYYY-MM-DD') AS INTERNSHIP_END ,
            cpm.CORM_NAME_KR ,
            cpm.CORM_NAME_EN ,
            cpm.CORM_TYPE ,
            cpm.ADDRESS ,
            CASE
                WHEN ipt.INTERNSHIP_END < SYSDATE THEN '실습완료'
                ELSE '실습중'
            END AS INTERNSHIP_STATUS ,
            CASE
                WHEN SYSDATE BETWEEN ipt.INTERNSHIP_STR AND (ipt.INTERNSHIP_END + 21) THEN 'Y'
                ELSE 'N'
            END AS IS_SHOW_REPORT
        FROM INTERN_PLAN ipt
            JOIN CORPORATE_MEMBERS cpm
                ON ipt.CORM_SEQ = CPM.CORM_SEQ
        WHERE ipt.DEL_YN = :stsDelN
            AND cpm.DEL_YN = :stsDelN
            AND ipt.STAFF_NO = :userId            
            AND (:#{#internMngSearchDTO.year} IS NULL OR ipt.INTERN_YEAR = :#{#internMngSearchDTO.year})
            AND (:#{#internMngSearchDTO.nameIntern} IS NULL OR LOWER(ipt.INTERN_PLAN_NM) LIKE LOWER('%' || :#{#internMngSearchDTO.nameIntern} || '%'))
            GROUP BY
             ipt.INTERN_PLAN_SEQ,
             ipt.INTERN_PLAN_NM,
             ipt.INTERNSHIP_FIELD,
             ipt.INTERNSHIP_STR,
             ipt.INTERNSHIP_END,
             cpm.CORM_NAME_KR,
             cpm.CORM_NAME_EN,
             cpm.CORM_TYPE,
             cpm.ADDRESS
    """, nativeQuery = true)
    List<IInternMngResponeListDTO> selectInternlist(InternMngSearchDTO internMngSearchDTO,
                                                    @Param("stsDelN") String stsDelN,
                                                    @Param("userId") String userId);

    @Query(value="""
        SELECT DISTINCT(INTERN_YEAR) 
        FROM  INTERN_PLAN
        WHERE DEL_YN = :stsDelN
        ORDER BY INTERN_YEAR
    """,nativeQuery = true)
    List<String> selectYearPlan(@Param("stsDelN") String stsDelN);
}
