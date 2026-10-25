package com.winwintek.web.backend.api.repository.admin.preTest;

import com.winwintek.web.backend.api.dto.admin.preTest.CorporatePreTestSeqDTO;
import com.winwintek.web.backend.api.entity.admin.preTest.*;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreTestRepository extends JpaRepository<PreTestEntity, Long> {

    @Query (value = """
                SELECT
                	PRE_TEST_DETAIL_SEQ ,
                	PRE_TEST_CATEGORY_SEQ ,
                	CONTENT ,
                	DISPLAY_ORDER ,
                	ADD_CONTENT_YN
                FROM
                	PRE_TEST_DETAIL
                WHERE
                	DEL_YN = :stsDelN
                ORDER BY 
                    DISPLAY_ORDER
            """, nativeQuery = true)
    List<IPreTestDetailCusDTO> selectPreTestDetail(@Param ("stsDelN") String stsDelN);


    @Query (value = """
                SELECT
                    PRE_TEST_CATEGORY_SEQ ,
                    CATEGORY_INDEX ,
                    CONTENT ,
                    DISPLAY_ORDER
                 FROM
                    PRE_TEST_CATEGORY
                 WHERE
                    DEL_YN = :stsDelN
                 ORDER BY 
                    DISPLAY_ORDER
            """, nativeQuery = true)
    List<IPreTestCategoryCusDTO> selectPreTestCategory(@Param ("stsDelN") String stsDelN);

    @Query (value = """
                SELECT
                 cms.CORM_SEQ ,
                 cms.CORM_NAME_KR ,
                 cms.CORM_NAME_EN ,
                 cms.CORM_TYPE ,
                 cms.CORM_CATEGORIES ,
                 cpt.STANDARD_INDUSTRY_TYPE ,
                 cpt.CORPORATE_PRE_TEST_SEQ,
                 ipn.INTERN_PLAN_SEQ
                FROM CORPORATE_MEMBERS cms
                JOIN INTERN_PLAN ipn
                    ON cms.CORM_SEQ = ipn.CORM_SEQ
                LEFT JOIN CORPORATE_PRE_TEST cpt
                    ON ipn.INTERN_PLAN_SEQ = cpt.INTERN_PLAN_SEQ AND cpt.DEL_YN = :stsDelN
                WHERE cms.DEL_YN = :stsDelN
                  AND ipn.INTERN_PLAN_SEQ = :#{#corporatePreTestDTO.internPlanSeq}
                  AND ipn.DEL_YN = :stsDelN
            """, nativeQuery = true)
    ICorporateMemberInfoCusDTO selectmemberInfo(CorporatePreTestSeqDTO corporatePreTestDTO,
            @Param ("stsDelN") String stsDelN);

    @Query(value = """
    SELECT
        pta.PRE_TEST_DETAIL_SEQ,
        pta.ADD_CONTENT
    FROM
        PRE_TEST_ANS pta
    LEFT JOIN CORPORATE_PRE_TEST cpt
        ON cpt.CORPORATE_PRE_TEST_SEQ = pta.CORPORATE_PRE_TEST_SEQ
    WHERE
        pta.DEL_YN = :stsDelN
        AND cpt.INTERN_PLAN_SEQ = :#{#corporatePreTestIdDTO.internPlanSeq}
        AND cpt.DEL_YN= :stsDelN
    """, nativeQuery = true)
    List<IPreTestDetailSelectCusDTO> selectDetailSelect(CorporatePreTestSeqDTO corporatePreTestIdDTO,
            @Param ("stsDelN") String stsDelN);

}
