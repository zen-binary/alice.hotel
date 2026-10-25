package com.winwintek.web.backend.api.repository.system.menuHistory;

import com.winwintek.web.backend.api.dto.system.menuHistory.MenuHisRequestDTO;
import com.winwintek.web.backend.api.dto.system.menuHistory.MenuHisSearchDTO;
import com.winwintek.web.backend.api.entity.system.menuHistory.ISysMenuHistoryResponse;
import com.winwintek.web.backend.api.entity.system.menuHistory.SysMenuHistoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMenuHistoryRepository extends JpaRepository<SysMenuHistoryEntity, String> {

    @Query (value = """
                SELECT
                	rowNumber,
                	url,
                	userId,
                	name,
                	connDate
                FROM (
                SELECT
                	ROW_NUMBER() OVER (ORDER BY his.CONN_DATE ASC) AS rowNumber,
                	his.CONN_URL AS url,
                	his.CONN_ID AS userId,
                	CASE
                		WHEN cu.USER_ID IS NOT NULL THEN cu.USER_NAME
                		WHEN vscs.STD_NO IS NOT NULL THEN vscs.KOR_NM
                		WHEN stf.STAFF_NO IS NOT NULL THEN stf.KOR_NM
                		ELSE his.CONN_IP
                	END AS name,
                	his.CONN_DATE AS connDate
                FROM CPS_MENU_CONN_HIS his
                LEFT JOIN CPS_USER cu
                ON his.CONN_ID = cu.USER_ID
                LEFT JOIN V_STD_CDP_SREG vscs
                ON his.CONN_ID = vscs.STD_NO
                LEFT JOIN V_STD_CDP_STAF stf
                ON his.CONN_ID = stf.STAFF_NO
                WHERE 
                    (:#{#search.strDate} IS NULL OR TO_CHAR(his.CONN_DATE, 'YYYY-MM-DD') >= :#{#search.strDate})
                    AND (:#{#search.endDate} IS NULL OR TO_CHAR(his.CONN_DATE, 'YYYY-MM-DD') <= :#{#search.endDate})
                    AND (:#{#search.user} IS NULL OR LOWER(his.CONN_ID) LIKE ('%' || LOWER(:#{#search.user}) || '%'))
                )INFO
                WHERE (:#{#search.name} IS NULL OR LOWER(name) LIKE ('%' || LOWER(:#{#search.name}) || '%'))
                ORDER BY rowNumber DESC
            """, countQuery = """
                    SELECT
                    	url
                    FROM (
                    SELECT
                    	ROW_NUMBER() OVER (ORDER BY his.CONN_DATE ASC) AS rowNumber,
                    	his.CONN_URL AS url,
                    	his.CONN_ID AS userId,
                    	CASE
                    		WHEN cu.USER_ID IS NOT NULL THEN cu.USER_NAME
                    		WHEN vscs.STD_NO IS NOT NULL THEN vscs.KOR_NM
                    		WHEN stf.STAFF_NO IS NOT NULL THEN stf.KOR_NM
                    		ELSE his.CONN_IP
                    	END AS name,
                    	his.CONN_DATE AS connDate
                    FROM CPS_MENU_CONN_HIS his
                    LEFT JOIN CPS_USER cu
                    ON his.CONN_ID = cu.USER_ID
                    LEFT JOIN V_STD_CDP_SREG vscs
                    ON his.CONN_ID = vscs.STD_NO
                    LEFT JOIN V_STD_CDP_STAF stf
                    ON his.CONN_ID = stf.STAFF_NO
                    WHERE 
                        (:#{#search.strDate} IS NULL OR TO_CHAR(his.CONN_DATE, 'YYYY-MM-DD') >= :#{#search.strDate})
                        AND (:#{#search.endDate} IS NULL OR TO_CHAR(his.CONN_DATE, 'YYYY-MM-DD') <= :#{#search.endDate})
                        AND (:#{#search.user} IS NULL OR LOWER(his.CONN_ID) LIKE ('%' || LOWER(:#{#search.user}) || '%'))
                    )INFO
                    WHERE (:#{#search.name} IS NULL OR LOWER(name) LIKE ('%' || LOWER(:#{#search.name}) || '%'))
                    ORDER BY rowNumber DESC
            """, nativeQuery = true)
    Page<ISysMenuHistoryResponse> selectMenuHistory(MenuHisSearchDTO search, Pageable pageable);

}
