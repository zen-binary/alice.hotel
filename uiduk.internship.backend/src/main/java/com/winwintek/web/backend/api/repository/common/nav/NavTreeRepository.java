package com.winwintek.web.backend.api.repository.common.nav;

import com.winwintek.web.backend.api.entity.common.navItems.INavTree;
import com.winwintek.web.backend.api.entity.common.navItems.MenuAuthenticateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NavTreeRepository extends JpaRepository<MenuAuthenticateEntity, Long> {

    @Query (value = """
                    SELECT
                    	ID
                     	, NAME
                     	, PARENT_ID
                     	, SCREEN_NAME
                     	, ROUTE
                     	, DISPLAY_ORDER
                    	, LEVEL AS TREE_LEVEL
                    	, CONNECT_BY_ROOT ID AS TREE_ROOT
                    FROM
                    	MENU mn
                    INNER JOIN MENU_ROLE mr
                    ON mn.ID = mr.MENU_ID
                    AND mr.ROLE_ID = :rlId
                    START WITH PARENT_ID IS NULL
                    	CONNECT BY PRIOR ID = PARENT_ID
                    ORDER SIBLINGS BY DISPLAY_ORDER
            """, nativeQuery = true)
    List<INavTree> getMenuTree(@Param ("rlId") Long rlId);

}
