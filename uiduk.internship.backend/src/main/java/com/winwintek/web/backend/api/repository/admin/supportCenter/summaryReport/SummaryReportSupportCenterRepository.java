package com.winwintek.web.backend.api.repository.admin.supportCenter.summaryReport;

import com.winwintek.web.backend.api.entity.admin.supportCenter.summaryReport.ISummaryReportSupportCenterResponse;
import com.winwintek.web.backend.api.entity.admin.supportCenter.summaryReport.SummaryReportSupportCenterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SummaryReportSupportCenterRepository extends JpaRepository<SummaryReportSupportCenterEntity, String> {

    @Query (value = """
                SELECT
                    istd.INTERN_STD_SEQ AS internStdId,
                    ssr.STD_SUMMARY_REPORT_SEQ AS stdSummaryReportId,
                    vscs.KOR_NM AS stdNm,
                    vscd.DEPT_KOR_NM AS deptNm,
                    FN_GET_CODE_NAME(vscs.SHYR_CD) AS stdYear,
                    vscs.STD_NO AS stdNo,
                    ssr.CONTENT AS content
                FROM
                    INTERN_STD istd
                        JOIN V_STD_CDP_SREG vscs ON vscs.STD_NO = istd.STD_NO
                        LEFT JOIN V_STD_CDP_DEPT vscd ON vscd.DEPT_CD = vscs.SUST_CD
                        LEFT JOIN STD_SUMMARY_REPORT ssr ON ssr.INTERN_STD_SEQ = istd.INTERN_STD_SEQ
                WHERE istd.DEL_YN = :delYn
                    AND (ssr.DEL_YN IS NULL OR ssr.DEL_YN = :delYn)
                    AND istd.INTERN_STD_SEQ = :internStdId
            """, nativeQuery = true)
    ISummaryReportSupportCenterResponse getDetailSummaryReport(@Param ("internStdId") String internStdId,
            @Param ("delYn") String delYn);

    SummaryReportSupportCenterEntity findByStdSummaryReportSeqAndDelYn(String stdSummaryReportSeq, String delYn);
}