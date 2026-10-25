package com.alice.hotel.api.repository.system.menuHistory;

import com.alice.hotel.api.dto.system.menuHistory.MenuHisSearchDTO;
import com.alice.hotel.api.entity.system.menuHistory.ISysMenuHistoryResponse;
import com.alice.hotel.api.entity.system.menuHistory.SysMenuHistoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IMenuHistoryRepository extends JpaRepository<SysMenuHistoryEntity, String> {

    @Query(value = """
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
                		ELSE his.CONN_IP
                	END AS name,
                	his.CONN_DATE AS connDate
                FROM CPS_MENU_CONN_HIS his
                LEFT JOIN CPS_USER cu
                ON his.CONN_ID = cu.USER_ID
                WHERE
                    (:#{#search.strDate} IS NULL OR DATE_FORMAT(his.CONN_DATE, '%Y-%m-%d') >= :#{#search.strDate})
                    AND (:#{#search.endDate} IS NULL OR DATE_FORMAT(his.CONN_DATE, '%Y-%m-%d') <= :#{#search.endDate})
                    AND (:#{#search.user} IS NULL OR LOWER(his.CONN_ID) LIKE CONCAT('%', LOWER(:#{#search.user}), '%'))
                ) INFO
                WHERE (:#{#search.name} IS NULL OR LOWER(name) LIKE CONCAT('%', LOWER(:#{#search.name}), '%'))
                ORDER BY rowNumber DESC
            """, countQuery = """
                    SELECT COUNT(*)
                    FROM (
                    SELECT
                    	CASE
                    		WHEN cu.USER_ID IS NOT NULL THEN cu.USER_NAME
                    		ELSE his.CONN_IP
                    	END AS name
                    FROM CPS_MENU_CONN_HIS his
                    LEFT JOIN CPS_USER cu
                    ON his.CONN_ID = cu.USER_ID
                    WHERE
                        (:#{#search.strDate} IS NULL OR DATE_FORMAT(his.CONN_DATE, '%Y-%m-%d') >= :#{#search.strDate})
                        AND (:#{#search.endDate} IS NULL OR DATE_FORMAT(his.CONN_DATE, '%Y-%m-%d') <= :#{#search.endDate})
                        AND (:#{#search.user} IS NULL OR LOWER(his.CONN_ID) LIKE CONCAT('%', LOWER(:#{#search.user}), '%'))
                    ) INFO
                    WHERE (:#{#search.name} IS NULL OR LOWER(name) LIKE CONCAT('%', LOWER(:#{#search.name}), '%'))
            """, nativeQuery = true)
    Page<ISysMenuHistoryResponse> selectMenuHistory(MenuHisSearchDTO search, Pageable pageable);

}
