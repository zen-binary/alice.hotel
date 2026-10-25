package com.winwintek.web.backend.api.repository.client.student.docRoom;

import com.winwintek.web.backend.api.dto.client.student.docRoom.DocRoomReqDTO;
import com.winwintek.web.backend.api.entity.client.student.docRoom.CrudDocRoomEntity;
import com.winwintek.web.backend.api.entity.client.student.docRoom.IDocRoomDetailOutput;
import com.winwintek.web.backend.api.entity.client.student.docRoom.IDocRoomOutput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DocRoomRepository extends JpaRepository<CrudDocRoomEntity, String> {

    @Query(value = """
        WITH OrderedDocRoom AS (
            SELECT
                    ROW_NUMBER() OVER (
                        ORDER BY dr.TOP_FIX_YN DESC, dr.REG_DATE DESC
                    ) AS rowNumber,
                    dr.DOC_SEQ AS docSeq,
                    dr.TITLE AS title,
                    dr.REG_DATE AS regDate,
                    dr.TOP_FIX_YN as topFixYn
                FROM
                    DOC_ROOM dr
            WHERE
                (:#{#req.title} IS NULL OR LOWER(dr.TITLE) LIKE '%' || LOWER(:#{#req.title} || '%' ))                                                                      
                AND (dr.DEL_YN = :delYn)
                AND dr.USE_YN = :useYn
        )
        SELECT
            (SELECT COUNT(*) FROM OrderedDocRoom) - rowNumber + 1 AS rowNumber,
            docSeq,
            title,
            regDate,
            topFixYn
        FROM
            OrderedDocRoom
        ORDER BY
            rowNumber DESC
    """, countQuery = """
        SELECT dr.DOC_SEQ FROM DOC_ROOM dr
        WHERE 
        (:#{#req.title} IS NULL OR LOWER(dr.TITLE) LIKE '%' || LOWER(:#{#req.title} || '%' )) AND 
        dr.DEL_YN = :delYn
        AND dr.USE_YN = :useYn
    """, nativeQuery = true)
    Page<IDocRoomOutput> getSchoolNoticeList(DocRoomReqDTO req, Pageable pageable,
            @Param ("delYn") String delYn, @Param("useYn") String useYn);

    @Query (value = """
        SELECT
            dr.TITLE AS title,
            dr.REG_DATE AS regDate,
            dr.CONTENT AS content
        FROM
            DOC_ROOM dr
        WHERE
        	(dr.DEL_YN = :delYn) AND
        	(dr.DOC_SEQ = :id)
    """, nativeQuery = true)
    IDocRoomDetailOutput getSchoolNoticeDetail(@Param("id") String id,@Param("delYn") String delYn);


}
