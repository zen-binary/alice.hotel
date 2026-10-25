package com.winwintek.web.backend.api.repository.client.student.schoolNotice;

import com.winwintek.web.backend.api.dto.client.student.schoolNotice.SchoolNoticeReqDTO;
import com.winwintek.web.backend.api.entity.client.student.schoolNotice.ISchoolNoticeDetailOutput;
import com.winwintek.web.backend.api.entity.client.student.schoolNotice.ISchoolNoticeListOutput;
import com.winwintek.web.backend.api.entity.client.student.schoolNotice.ISchoolNoticeOutput;
import com.winwintek.web.backend.api.entity.client.student.schoolNotice.SchoolNoticeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolNoticeRepository extends JpaRepository<SchoolNoticeEntity, String> {

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
    List<ISchoolNoticeOutput> getSchoolNoticeList(@Param("useYn") String useYn, @Param("delYn") String delYn);

    @Query(value = """
        WITH OrderedNotice AS (
            SELECT
                ROW_NUMBER() OVER (
                    ORDER BY n.TOP_FIX_YN DESC, n.REG_DATE DESC
                ) AS rowNumber,
                n.NOTICE_SEQ AS noticeSeq,
                n.TITLE AS title,
                n.REG_DATE AS regDate,
                n.TOP_FIX_YN as topFixYn
            FROM
                NOTICE n
            WHERE
                (:#{#req.title} IS NULL OR LOWER(n.TITLE) LIKE LOWER('%' || :#{#req.title} || '%'))
                AND n.DEL_YN = :delYn  AND n.USE_YN = :useYn
        )
        SELECT
            (SELECT COUNT(*) FROM OrderedNotice) - rowNumber + 1 AS rowNumber,
            noticeSeq,
            title,
            regDate,
            topFixYn
        FROM
            OrderedNotice
        ORDER BY
            rowNumber DESC
    """, countQuery = """
                SELECT n.NOTICE_SEQ FROM NOTICE n
                 WHERE (:#{#req.title} IS NULL LOWER(n.TITLE) LIKE LOWER('%' || :#{#req.title} || '%'))
                 AND n.DEL_YN = :delYn AND n.USE_YN = :useYn""",
            nativeQuery = true)
    Page<ISchoolNoticeListOutput> getSchoolNoticeList(SchoolNoticeReqDTO req, Pageable pageable,
            @Param("delYn") String delYn, @Param("useYn") String useYn);

    @Query(value = """
        SELECT
        	n.TITLE AS title,
        	n.REG_DATE AS regDate,
        	n.CONTENT AS content
        FROM
        	NOTICE n
        WHERE
        	(n.DEL_YN = :delYn) AND
        	(n.NOTICE_SEQ = :id)
    """, nativeQuery = true)
    ISchoolNoticeDetailOutput getSchoolNoticeDetail(@Param("id") String id,@Param("delYn") String delYn);

}
