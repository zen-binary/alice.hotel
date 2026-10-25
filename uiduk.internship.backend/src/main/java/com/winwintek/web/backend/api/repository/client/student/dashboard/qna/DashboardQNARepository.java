package com.winwintek.web.backend.api.repository.client.student.dashboard.qna;

import com.winwintek.web.backend.api.entity.client.student.dashboard.qna.DashboardQNAEntity;
import com.winwintek.web.backend.api.entity.client.student.dashboard.qna.IDashboardQNAOutput;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DashboardQNARepository extends JpaRepository<DashboardQNAEntity, String> {

    @Query(value = """
        SELECT
        	q.QNA_SEQ AS qnaSeq,
        	q.TITLE AS title,
        	q.REG_DATE AS regDate
        FROM
        	QNA q
        WHERE
        	q.DEL_YN = :delYn
        	AND q.REG_ID = :stdId
        ORDER BY
        	REG_DATE DESC
            FETCH FIRST 3 ROWS ONLY
    """, nativeQuery = true)
    List<IDashboardQNAOutput> getDashboardQNA(@Param("stdId") String stdId, @Param("delYn") String delYn);
}
