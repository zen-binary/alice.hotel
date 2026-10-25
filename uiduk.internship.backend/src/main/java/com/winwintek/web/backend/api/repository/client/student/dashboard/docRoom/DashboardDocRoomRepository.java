package com.winwintek.web.backend.api.repository.client.student.dashboard.docRoom;

import com.winwintek.web.backend.api.entity.client.student.dashboard.docRoom.DashboardDocRoomEntity;
import com.winwintek.web.backend.api.entity.client.student.dashboard.docRoom.IDashboardDocRoomOutput;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DashboardDocRoomRepository extends JpaRepository<DashboardDocRoomEntity, String> {

    @Query(value = """
        SELECT
            dr.DOC_SEQ AS docSeq,
            dr.TITLE AS title,
            dr.REG_DATE AS regDate
        FROM
            DOC_ROOM dr
        WHERE
            DR.DEL_YN = :delYn 
            AND DR.USE_YN = :useYn
        ORDER BY
            REG_DATE DESC
            FETCH FIRST 3 ROWS ONLY
    """, nativeQuery = true)
    List<IDashboardDocRoomOutput> getDashboardDocRoom(@Param("delYn") String delYn, @Param("useYn") String useYn);
}
