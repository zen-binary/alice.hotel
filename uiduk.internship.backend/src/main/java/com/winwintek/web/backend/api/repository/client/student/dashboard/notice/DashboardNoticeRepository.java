package com.winwintek.web.backend.api.repository.client.student.dashboard.notice;

import com.winwintek.web.backend.api.entity.client.student.dashboard.notice.DashboardNoticeEntity;
import com.winwintek.web.backend.api.entity.client.student.dashboard.notice.IDashboardNoticeOutput;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DashboardNoticeRepository extends JpaRepository<DashboardNoticeEntity, String> {

    @Query(value = """
        SELECT
        	n.NOTICE_SEQ AS noticeSeq,
        	n.TITLE AS title,
        	n.REG_DATE AS regDate
        FROM
        	NOTICE n
        WHERE
        	(TO_CHAR(START_DATE_SHOW) IS NULL
            OR TO_CHAR(START_DATE_SHOW)  <= TO_CHAR(SYSDATE))
            AND (TO_CHAR(END_DATE_SHOW) IS NULL
            OR TO_CHAR(END_DATE_SHOW) >= TO_CHAR(SYSDATE))
        	AND USE_YN = :useYn
            AND DEL_YN = :delYn
        ORDER BY
        	REG_DATE DESC
            FETCH FIRST 3 ROWS ONLY
    """, nativeQuery = true)
    List<IDashboardNoticeOutput> getDashboardNotice(@Param("useYn") String useYn, @Param("delYn") String delYn);
}
