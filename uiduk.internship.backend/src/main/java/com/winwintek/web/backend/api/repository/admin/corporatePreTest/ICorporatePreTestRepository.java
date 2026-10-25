package com.winwintek.web.backend.api.repository.admin.corporatePreTest;

import com.winwintek.web.backend.api.dto.admin.corporatePreTest.CorporatePreTestIdDTO;
import com.winwintek.web.backend.api.entity.admin.corporatePreTest.CorporatePreTestEntity;
import com.winwintek.web.backend.api.entity.admin.corporatePreTest.ICorporateMemberInfoDTO;
import com.winwintek.web.backend.api.entity.admin.corporatePreTest.IPreTestCategoryDTO;
import com.winwintek.web.backend.api.entity.admin.corporatePreTest.IPreTestDetailDTO;
import com.winwintek.web.backend.api.entity.admin.corporatePreTest.IPreTestDetailSelectDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICorporatePreTestRepository extends JpaRepository<CorporatePreTestEntity, Long> {

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
    List<IPreTestDetailDTO> selectPreTestDetail(@Param ("stsDelN") String stsDelN);


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
    List<IPreTestCategoryDTO> selectPreTestCategory(@Param ("stsDelN") String stsDelN);

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
    ICorporateMemberInfoDTO selectmemberInfo(CorporatePreTestIdDTO corporatePreTestDTO,
            @Param ("stsDelN") String stsDelN);

    @Query (value = """
                SELECT
                    PRE_TEST_DETAIL_SEQ ,
                    ADD_CONTENT
                FROM
                    PRE_TEST_ANS
                WHERE
                    CORPORATE_PRE_TEST_SEQ = :#{#corporatePreTestIdDTO.corporatePreTestSeq}
                    AND DEL_YN = :stsDelN
            """, nativeQuery = true)
    List<IPreTestDetailSelectDTO> selectDetailSelect(CorporatePreTestIdDTO corporatePreTestIdDTO,
            @Param ("stsDelN") String stsDelN);

    @Modifying
    @Transactional
    @Query (value = """
                UPDATE CORPORATE_PRE_TEST
                SET STANDARD_INDUSTRY_TYPE = :standardIndustryType
                WHERE CORPORATE_PRE_TEST_SEQ = :corporatePreTestSeq
            """, nativeQuery = true)
    void updateIndustry(@Param ("corporatePreTestSeq") String corporatePreTestSeq,
            @Param ("standardIndustryType") String standardIndustryType);

    //tuyendv
    @Query (value = """
                SELECT
                    PRE_TEST_DETAIL_SEQ ,
                    ADD_CONTENT
                FROM
                    PRE_TEST_ANS
                WHERE
                    CORPORATE_PRE_TEST_SEQ = :corpPreTestSeq
                    AND DEL_YN = :delYn
            """, nativeQuery = true)
    List<IPreTestDetailSelectDTO> getSelectDetailCategory(@Param ("corpPreTestSeq") String corpPreTestSeq,
            @Param ("delYn") String delYn);

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
                LEFT JOIN INTERN_PLAN ipn
                    ON cms.CORM_SEQ = ipn.CORM_SEQ
                LEFT JOIN CORPORATE_PRE_TEST cpt 
                    ON (ipn.INTERN_PLAN_SEQ = cpt.INTERN_PLAN_SEQ AND cpt.DEL_YN = :delYn AND cpt.REG_ID = :corpSeq)
                WHERE cms.DEL_YN = :delYn 
                  AND cms.CORM_STATUS = :corpApproveStatus
                   AND ipn.DEL_YN = :delYn 
                  AND ipn.STATUS = :planApproveStatus
                  AND ipn.INTERN_PLAN_SEQ = :internPlanSeq
            """, nativeQuery = true)
    Optional<ICorporateMemberInfoDTO> getCorporateInfoByInternPlanSeq(@Param ("internPlanSeq") String internPlanSeq,
            @Param ("corpSeq") String corpSeq, @Param ("corpApproveStatus") String corpApproveStatus,
            @Param ("planApproveStatus") String planApproveStatus, @Param ("delYn") String delYn);

}
