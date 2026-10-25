package com.winwintek.web.backend.api.repository.client.student.qna;

import com.winwintek.web.backend.api.dto.client.student.qna.QNASearchReqDTO;
import com.winwintek.web.backend.api.entity.client.student.qna.CrudQNAEntity;
import com.winwintek.web.backend.api.entity.client.student.qna.IQNADetailOutput;
import com.winwintek.web.backend.api.entity.client.student.qna.IQNAOutput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QNARepository extends JpaRepository<CrudQNAEntity, String> {

    @Query(value = """
        SELECT
        	q.QNA_SEQ AS qnaSeq,
        	q.TITLE AS title,
        	q.REG_DATE AS regDate,
        	q.ANSWER AS answer
        FROM QNA q
        WHERE
        (:#{#req.title} IS NULL OR LOWER(q.TITLE) LIKE LOWER('%' || :#{#req.title} || '%'))
        AND DEL_YN = :delYn
        AND q.USER_ID = :regId
        ORDER BY
            CASE WHEN q.ANSWER IS NULL THEN 1 ELSE 2 END,
            REG_DATE DESC
    """, countQuery = """
    SELECT COUNT(*)
    FROM QNA q
    WHERE q.DEL_YN = :delYn AND q.USER_ID = :regId
    """, nativeQuery = true)
    Page<IQNAOutput> getQnaList(QNASearchReqDTO req, @Param("regId") String regId, Pageable pageable,
            @Param("delYn") String delYn);

    @Query(value = """
        SELECT
        	q.TITLE AS title,
         	q.REG_DATE AS regDate,
         	q.CONTENT as content,
         	q.ANSWER AS answer,
         	FN_GET_NAME_KR(q.MODIFY_ID) AS modifyId,
         	q.MODIFY_DATE AS modifyDate
        FROM
        	QNA q
        WHERE
        	(q.DEL_YN = :delYn) AND
        	(q.QNA_SEQ = :id)
    """, nativeQuery = true)
    IQNADetailOutput getQNADetail(@Param("id") String id, @Param("delYn") String delYn);

    Optional<CrudQNAEntity> findByQnaSeq(String id);

}
