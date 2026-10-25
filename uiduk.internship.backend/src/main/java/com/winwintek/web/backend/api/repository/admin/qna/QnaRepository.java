package com.winwintek.web.backend.api.repository.admin.qna;

import com.winwintek.web.backend.api.dto.admin.qna.QnaSearchDTO;
import com.winwintek.web.backend.api.entity.admin.qna.IQnaCusDTO;
import com.winwintek.web.backend.api.entity.admin.qna.QnaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QnaRepository extends JpaRepository<QnaEntity, String> {
    @Query (value = """
            SELECT * FROM (
                SELECT
                    ROW_NUMBER() OVER (ORDER BY (CASE WHEN q.ANSWER IS NULL THEN 'N' ELSE 'Y' END) DESC, TO_CHAR(q.REG_DATE, 'yyyy-MM-dd'), q.TITLE DESC) AS rowNumber,
                    q.QNA_SEQ AS qnaSeq,
                    q.TITLE AS title,
                    FN_GET_NAME_KR(q.REG_ID) AS regId,
                    vscs.HAND_NO AS phone,
                    vscs.EMAIL AS email,
                    q.REG_DATE AS regDate,
                    CASE WHEN q.ANSWER IS NULL THEN 'N' ELSE 'Y' END AS answerYn
                FROM QNA q
                LEFT JOIN V_STD_CDP_SREG vscs
                        ON vscs.STD_NO = q.REG_ID
                WHERE q.DEL_YN = :delYn
                    AND (:#{#request.title} IS NULL OR LOWER(q.TITLE) LIKE '%' || LOWER(:#{#request.title}) || '%')
                    AND (:#{#request.regName} IS NULL OR LOWER(vscs.KOR_NM) LIKE '%' || LOWER(:#{#request.regName}) || '%')
                    AND (:#{#request.phone} IS NULL OR LOWER(vscs.HAND_NO) LIKE '%' || LOWER(:#{#request.phone}) || '%')
                    AND (:#{#request.email} IS NULL OR LOWER(vscs.EMAIL) LIKE '%' || LOWER(:#{#request.email}) || '%')
                    AND (:#{#request.startDate} IS NULL OR TO_CHAR(q.REG_DATE, 'yyyy-MM-dd') >= :#{#request.startDate})
                    AND (:#{#request.endDate} IS NULL OR TO_CHAR(q.REG_DATE, 'yyyy-MM-dd') <= :#{#request.endDate})
                    AND (:#{#request.answerYn} IS NULL
                                         OR (:#{#request.answerYn} = 'N' AND q.ANSWER IS NULL)
                                         OR (:#{#request.answerYn} = 'Y' AND q.ANSWER IS NOT NULL))
                ) QA
            ORDER BY QA.rowNumber DESC
            """
            ,countQuery =
            """
                    SELECT
                COUNT(*)
                 FROM (
                SELECT
                    ROW_NUMBER() OVER (ORDER BY (CASE WHEN q.ANSWER IS NULL THEN 'N' ELSE 'Y' END), TO_CHAR(q.REG_DATE, 'yyyy-MM-dd'), q.TITLE DESC) AS rowNumber,
                    q.QNA_SEQ AS qnaSeq,
                    q.TITLE AS title,
                    FN_GET_NAME_KR(q.REG_ID) AS regId,
                    vscs.HAND_NO AS phone,
                    vscs.EMAIL AS email,
                    q.REG_DATE AS regDate,
                    CASE WHEN q.ANSWER IS NULL THEN 'N' ELSE 'Y' END AS answerYn
                FROM QNA q
                LEFT JOIN V_STD_CDP_SREG vscs
                        ON vscs.STD_NO = q.REG_ID
                WHERE q.DEL_YN = :delYn
                    AND (:#{#request.title} IS NULL OR LOWER(q.TITLE) LIKE '%' || LOWER(:#{#request.title}) || '%')
                    AND (:#{#request.regName} IS NULL OR LOWER(vscs.KOR_NM) LIKE '%' || LOWER(:#{#request.regName}) || '%')
                    AND (:#{#request.phone} IS NULL OR LOWER(vscs.HAND_NO) LIKE '%' || LOWER(:#{#request.phone}) || '%')
                    AND (:#{#request.email} IS NULL OR LOWER(vscs.EMAIL) LIKE '%' || LOWER(:#{#request.email}) || '%')
                    AND (:#{#request.startDate} IS NULL OR TO_CHAR(q.REG_DATE, 'yyyy-MM-dd') >= :#{#request.startDate})
                    AND (:#{#request.endDate} IS NULL OR TO_CHAR(q.REG_DATE, 'yyyy-MM-dd') <= :#{#request.endDate})
                    AND (:#{#request.answerYn} IS NULL
                                         OR (:#{#request.answerYn} = 'N' AND q.ANSWER IS NULL)
                                         OR (:#{#request.answerYn} = 'Y' AND q.ANSWER IS NOT NULL))
                ) QA
            ORDER BY QA.rowNumber DESC
            """
            ,nativeQuery = true)
    Page<IQnaCusDTO> getQna(@Param ("request") QnaSearchDTO request, Pageable pageable,
            @Param("delYn") String delYn);

    @Modifying
    @Query (value = """
            UPDATE QNA q
            SET  q.DEL_YN = :delYn
            WHERE q.QNA_SEQ in (:idList)
            """, nativeQuery = true)
    void allRemove(@Param ("idList") List<String> idList, @Param ("delYn") String delYn);
    @Query (value = """
            SELECT
                FN_GET_NAME_KR(q.REG_ID) AS regId,
                q.QNA_SEQ AS qnaSeq,
                vscs.HAND_NO AS phone,
                vscs.EMAIL AS email,
                q.TITLE AS title,
                q.CONTENT AS content,
                q.ANSWER AS answer
            FROM QNA q
            LEFT JOIN V_STD_CDP_SREG vscs
                    ON vscs.STD_NO = q.REG_ID
            WHERE q.DEL_YN = :delYn
            AND q.QNA_SEQ = :qnaSeq
            """, nativeQuery = true)
    IQnaCusDTO getQnaDetail(@Param("qnaSeq") String qnaSeq, @Param("delYn") String delYn);

    QnaEntity findByQnaSeqAndDelYn(String id, String delYn);
}
