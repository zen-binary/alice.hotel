package com.winwintek.web.backend.api.repository.admin.notice;

import com.winwintek.web.backend.api.dto.admin.notice.NoticeSearchDTO;
import com.winwintek.web.backend.api.entity.admin.notice.INoticeCusDTO;
import com.winwintek.web.backend.api.entity.admin.notice.INoticeDetailCusDTO;
import com.winwintek.web.backend.api.entity.admin.notice.NoticeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoticeRepository extends JpaRepository<NoticeEntity, String> {

    @Query (value = """
            SELECT * FROM (
                SELECT
                      ROW_NUMBER() OVER (ORDER BY n.USE_YN, TO_CHAR(n.REG_DATE, 'yyyy-MM-dd'), n.TITLE DESC) AS NO_ID,
                      n.NOTICE_SEQ AS noticeSeq,
                      n.USE_YN AS useYN,
                      n.TITLE AS title,
                      n.START_DATE_SHOW AS startDateShow,
                      n.END_DATE_SHOW AS endDateShow,
                      FN_GET_NAME_KR(n.REG_ID) AS regId,
                      TO_CHAR(n.REG_DATE, 'yyyy-MM-dd') AS regDate
                  FROM NOTICE n
                  LEFT JOIN CPS_USER cu
                       ON cu.USER_ID = n.REG_ID
                   WHERE n.DEL_YN = :delYn
                       AND (:#{#request.regName} IS NULL OR LOWER(cu.USER_NAME) LIKE '%' || LOWER(:#{#request.regName}) || '%')
                       AND (:#{#request.title} IS NULL OR LOWER(n.TITLE) LIKE '%' || LOWER(:#{#request.title}) || '%')
                       AND (:#{#request.useYn} IS NULL OR n.USE_YN = :#{#request.useYn})
                       AND (:#{#request.startDateShow} IS NULL OR TO_CHAR(n.START_DATE_SHOW, 'yyyy-MM-dd') >= :#{#request.startDateShow})
                       AND (:#{#request.endDateShow} IS NULL OR TO_CHAR(n.END_DATE_SHOW, 'yyyy-MM-dd') <= :#{#request.endDateShow})
                       AND (:#{#request.startDate} IS NULL OR TO_CHAR(n.REG_DATE, 'yyyy-MM-dd') >= :#{#request.startDate})
                       AND (:#{#request.endDate} IS NULL OR TO_CHAR(n.REG_DATE, 'yyyy-MM-dd') <= :#{#request.endDate})
                   ) NOTI
                ORDER BY NOTI.NO_ID DESC
            """
            ,countQuery =
            """
                    SELECT
                COUNT(*)
                 FROM (
                SELECT
                  ROW_NUMBER() OVER (ORDER BY n.USE_YN, TO_CHAR(n.REG_DATE, 'yyyy-MM-dd'), n.TITLE DESC) AS NO_ID,
                  n.NOTICE_SEQ AS noticeSeq,
                  n.USE_YN AS useYN,
                  n.TITLE AS title,
                  n.START_DATE_SHOW AS startDateShow,
                  n.END_DATE_SHOW AS endDateShow,
                  FN_GET_NAME_KR(n.REG_ID) AS regId,
                  TO_CHAR(n.REG_DATE, 'yyyy-MM-dd') AS regDate
                  FROM NOTICE n
                  LEFT JOIN CPS_USER cu
                                      ON cu.USER_ID = n.REG_ID
                   WHERE n.DEL_YN = :delYn
                       AND (:#{#request.regName} IS NULL OR LOWER(cu.USER_NAME) LIKE '%' || LOWER(:#{#request.regName}) || '%')
                       AND (:#{#request.title} IS NULL OR LOWER(n.TITLE) LIKE '%' || LOWER(:#{#request.title}) || '%')
                       AND (:#{#request.useYn} IS NULL OR LOWER(n.USE_YN) LIKE '%' || LOWER(:#{#request.useYn}) || '%')
                       AND (:#{#request.startDateShow} IS NULL OR TO_CHAR(n.END_DATE_SHOW, 'yyyy-MM-dd') >= :#{#request.startDateShow})
                       AND (:#{#request.endDateShow} IS NULL OR TO_CHAR(n.START_DATE_SHOW, 'yyyy-MM-dd') <= :#{#request.endDateShow})
                       AND (:#{#request.startDate} IS NULL OR TO_CHAR(n.REG_DATE, 'yyyy-MM-dd') >= :#{#request.startDate})
                       AND (:#{#request.endDate} IS NULL OR TO_CHAR(n.REG_DATE, 'yyyy-MM-dd') <= :#{#request.endDate})
                   ) NOTI
                ORDER BY NOTI.NO_ID DESC
                """
            ,nativeQuery = true)
    Page<INoticeCusDTO> getNotice(@Param("request") NoticeSearchDTO request, Pageable pageable, @Param("delYn") String delYn);

    @Modifying
    @Query (value = """
            UPDATE NOTICE n
            SET  n.DEL_YN = :delYn
            WHERE n.NOTICE_SEQ in (:idList)
            """, nativeQuery = true)
    void allRemove(@Param ("idList") List<String> idList, @Param ("delYn") String delYn);

    @Query (value = """
            SELECT
              n.NOTICE_SEQ AS noticeSeq,
              n.TITLE AS title,
              n.CONTENT AS content,
              n.USE_YN AS useYN,
              n.TITLE AS title,
              n.START_DATE_SHOW AS startDateShow,
              n.END_DATE_SHOW AS endDateShow,
              n.TOP_FIX_YN AS topFixYN
            FROM
                NOTICE n
            WHERE n.NOTICE_SEQ = :seq
                AND n.DEL_YN = :delYn
            """, nativeQuery = true)
    INoticeDetailCusDTO findNoticeSeqAndDelYn(@Param ("seq") String seq, @Param ("delYn") String delYn);

    NoticeEntity findByNoticeSeqAndDelYn(String id, String delYn);

}
