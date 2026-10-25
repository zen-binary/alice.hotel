package com.alice.hotel.api.repository.common.nav;

import com.alice.hotel.api.entity.common.navItems.INavTree;
import com.alice.hotel.api.entity.common.navItems.MenuAuthenticateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NavTreeRepository extends JpaRepository<MenuAuthenticateEntity, Long> {

    @Query(value = """
            WITH RECURSIVE menu_tree AS (
                SELECT
                    mn.ID AS id,
                    mn.NAME AS name,
                    mn.PARENT_ID AS parent_id,
                    mn.SCREEN_NAME AS screen_name,
                    mn.ROUTE AS router,
                    mn.VN_NAME AS vn_name,
                    CAST(mn.DISPLAY_ORDER AS SIGNED) AS display_order,
                    1 AS tree_level,
                    mn.ID AS tree_root,
                    mn.BLANK_SCREEN AS blank_screen
                FROM MENU mn
                INNER JOIN MENU_ROLE mr
                    ON mn.ID = mr.MENU_ID
                   AND mr.ROLE_ID = :rlId
                WHERE mn.PARENT_ID IS NULL
                  AND IFNULL(mn.DEL_YN, IFNULL(mn.DELETE_FLAG, 'N')) = 'N'
                UNION ALL
                SELECT
                    child.ID,
                    child.NAME,
                    child.PARENT_ID,
                    child.SCREEN_NAME,
                    child.ROUTE,
                    child.VN_NAME,
                    CAST(child.DISPLAY_ORDER AS SIGNED),
                    parent.tree_level + 1,
                    parent.tree_root,
                    child.BLANK_SCREEN
                FROM MENU child
                INNER JOIN MENU_ROLE mr
                    ON child.ID = mr.MENU_ID
                   AND mr.ROLE_ID = :rlId
                INNER JOIN menu_tree parent
                    ON child.PARENT_ID = parent.id
                WHERE IFNULL(child.DEL_YN, IFNULL(child.DELETE_FLAG, 'N')) = 'N'
            )
            SELECT
                id,
                name,
                parent_id,
                screen_name,
                router,
                vn_name,
                display_order,
                tree_level,
                tree_root,
                blank_screen
            FROM menu_tree
            ORDER BY tree_root, display_order, id
            """, nativeQuery = true)
    List<INavTree> getMenuTree(@Param("rlId") Long rlId);

}
