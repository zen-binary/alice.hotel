package com.winwintek.web.backend.api.repository.admin.docRoom;

import com.winwintek.web.backend.api.dto.admin.docRoom.DocRoomRequestDTO;
import com.winwintek.web.backend.api.dto.admin.docRoom.DocRoomSearchDTO;
import com.winwintek.web.backend.api.entity.admin.supportCenter.docRoom.DocumentRoomSupportCenterEntity;
import com.winwintek.web.backend.api.entity.admin.supportCenter.docRoom.IDocRoomDetailCusDTO;
import com.winwintek.web.backend.api.entity.admin.supportCenter.docRoom.IDocRoomSupportCenter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRoomRepository extends JpaRepository<DocumentRoomSupportCenterEntity, String> {

    @Query (value = """
            SELECT * FROM (
                SELECT
                    ROW_NUMBER() OVER (ORDER BY TO_CHAR(dr.REG_DATE, 'yyyy-MM-dd'), dr.TITLE DESC) AS rowNumber,
                    dr.DOC_SEQ AS docSeq,
                    dr.USE_YN AS useYn,
                    dr.TITLE AS title,
                    dr.CONTENT AS content,
                    dr.TOP_FIX_YN AS topFixYn,
                    FN_GET_NAME_KR(dr.REG_ID) AS regId,
                    dr.REG_DATE AS regDate
                FROM DOC_ROOM dr
                LEFT JOIN CPS_USER cu
                       ON cu.USER_ID = dr.REG_ID
                WHERE (:#{#docRoomSearchDTO.title} IS NULL OR :#{#docRoomSearchDTO.title} = '' OR LOWER(dr.TITLE) LIKE '%' || :#{#docRoomSearchDTO.title} || '%')
                    AND (:#{#docRoomSearchDTO.useYn} IS NULL OR dr.USE_YN = :#{#docRoomSearchDTO.useYn})
                    AND (:#{#docRoomSearchDTO.createdBy} IS NULL OR :#{#docRoomSearchDTO.createdBy} = '' OR LOWER(cu.USER_NAME) LIKE '%' || :#{#docRoomSearchDTO.createdBy} || '%')
                    AND (:#{#docRoomSearchDTO.regStartDate} IS NULL OR :#{#docRoomSearchDTO.regStartDate} = '' OR TRUNC(dr.REG_DATE) >= TO_DATE(:#{#docRoomSearchDTO.regStartDate}, 'YYYY-MM-DD'))
                    AND (:#{#docRoomSearchDTO.regEndDate} IS NULL OR :#{#docRoomSearchDTO.regEndDate} = '' OR TRUNC(dr.REG_DATE) <= TO_DATE(:#{#docRoomSearchDTO.regEndDate}, 'YYYY-MM-DD'))
                    AND dr.DEL_YN = :delN
                ) DR
            ORDER BY DR.rowNumber DESC
            """, countQuery = """
                    SELECT
                COUNT(*)
                 FROM (
               SELECT
                    ROW_NUMBER() OVER (ORDER BY TO_CHAR(dr.REG_DATE, 'yyyy-MM-dd'), dr.TITLE DESC) AS rowNumber,
                    dr.DOC_SEQ AS docSeq,
                    dr.TITLE AS title,
                    dr.CONTENT AS content,
                    dr.TOP_FIX_YN AS topFixYn,
                    FN_GET_NAME_KR(dr.REG_ID) AS regId,
                    dr.REG_DATE AS regDate
                FROM DOC_ROOM dr
                LEFT JOIN CPS_USER cu
                       ON cu.USER_ID = dr.REG_ID
                WHERE (:#{#docRoomSearchDTO.title} IS NULL OR :#{#docRoomSearchDTO.title} = '' OR LOWER(dr.TITLE) LIKE '%' || :#{#docRoomSearchDTO.title} || '%')
                    AND (:#{#docRoomSearchDTO.createdBy} IS NULL OR :#{#docRoomSearchDTO.createdBy} = '' OR LOWER(cu.USER_NAME) LIKE '%' || :#{#docRoomSearchDTO.createdBy} || '%')
                    AND (:#{#docRoomSearchDTO.regStartDate} IS NULL OR :#{#docRoomSearchDTO.regStartDate} = '' OR TRUNC(dr.REG_DATE) >= TO_DATE(:#{#docRoomSearchDTO.regStartDate}, 'YYYY-MM-DD'))
                    AND (:#{#docRoomSearchDTO.regEndDate} IS NULL OR :#{#docRoomSearchDTO.regEndDate} = '' OR TRUNC(dr.REG_DATE) <= TO_DATE(:#{#docRoomSearchDTO.regEndDate}, 'YYYY-MM-DD'))
                    AND dr.DEL_YN = :delN
                ) DR
            ORDER BY DR.rowNumber DESC
            """, nativeQuery = true)
    Page<IDocRoomSupportCenter> getDocRoomList(DocRoomSearchDTO docRoomSearchDTO, @Param ("delN") String delN,
            Pageable pageable);


    @Query (value = """
            SELECT
                DOC_SEQ AS docSeq
                ,TITLE AS title
                ,USE_YN AS useYn
                ,CONTENT AS content
                ,TOP_FIX_YN AS topFixYn
            FROM
                DOC_ROOM
            WHERE DOC_SEQ = :docSeq
                AND DEL_YN = :delYn
            """, nativeQuery = true)
    IDocRoomDetailCusDTO findDocRoomById(@Param ("docSeq") String docSeq, @Param ("delYn") String delYn);


    @Modifying
    @Query (value = """
            UPDATE DOC_ROOM
            SET TITLE = :#{#docRoomRequestDTO.title},
                CONTENT = :#{#docRoomRequestDTO.content},
                TOP_FIX_YN = :#{#docRoomRequestDTO.topFixYn},
                USE_YN = :#{#docRoomRequestDTO.useYn}
            WHERE DOC_SEQ = :#{#docRoomRequestDTO.docSeq}
            """, nativeQuery = true)
    Boolean updateDocRoom(DocRoomRequestDTO docRoomRequestDTO);

    @Modifying
    @Query (value = """
            UPDATE DOC_ROOM
            SET DEL_YN = :delYn
            WHERE DOC_SEQ IN :docSeqList
            """, nativeQuery = true)
    void updateDelYnForMultipleDocs(@Param ("docSeqList") List<String> docSeqList, @Param ("delYn") String delYn);

    DocumentRoomSupportCenterEntity findByDocSeqAndDelYn(String seq, String delYn);

}
