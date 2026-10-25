package com.winwintek.web.backend.api.repository.system.menu;

import com.winwintek.web.backend.api.dto.system.menu.MenuSearchRequestDTO;
import com.winwintek.web.backend.api.entity.system.menu.IMenuResponse;
import com.winwintek.web.backend.api.entity.system.menu.SysMenuEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMenusRepository extends JpaRepository<SysMenuEntity, Long> {

    @Query (value = """
                    SELECT ROW_NUMBER() OVER (ORDER BY mn.REG_DATE) AS rowNumber, 
                            mn.ID,
                            mn.SITE_TYPE,
                            mn.NAME,
                            mn.ROUTE,
                            mn.PARENT_ID,
                            mn.DISPLAY_ORDER,
                            mn.DEL_YN,
                            mn.VN_NAME
                        FROM MENU mn
                        WHERE mn.DEL_YN = 'N'
                            AND (:#{#dto.vnName} IS NULL OR LOWER(mn.VN_NAME) LIKE ('%' || LOWER(:#{#dto.vnName}) || '%'))
                            AND (:#{#dto.name} IS NULL OR LOWER(mn.NAME) LIKE ('%' || LOWER(:#{#dto.name}) || '%'))
                            AND (:#{#dto.route} IS NULL OR LOWER(mn.ROUTE) LIKE ('%' || LOWER(:#{#dto.route}) || '%'))
                    ORDER BY mn.REG_DATE DESC
                    
            """, countQuery = """
                    SELECT ROW_NUMBER() OVER (ORDER BY mn.REG_DATE) AS rowNumber, 
                                mn.ID,
                                mn.SITE_TYPE,
                                mn.NAME,
                                mn.ROUTE,
                                mn.PARENT_ID,
                                mn.DISPLAY_ORDER,
                                mn.DEL_YN,
                                mn.VN_NAME
                            FROM MENU mn
                            WHERE mn.DEL_YN = 'N'
                                AND (:#{#dto.vnName} IS NULL OR LOWER(mn.VN_NAME) LIKE ('%' || LOWER(:#{#dto.vnName}) || '%'))
                                AND (:#{#dto.name} IS NULL OR LOWER(mn.NAME) LIKE ('%' || LOWER(:#{#dto.name}) || '%'))
                                AND (:#{#dto.route} IS NULL OR LOWER(mn.ROUTE) LIKE ('%' || LOWER(:#{#dto.route}) || '%'))
                                ORDER BY mn.REG_DATE DESC
            """, nativeQuery = true)
    Page<IMenuResponse> searchMenu(@Param ("dto") MenuSearchRequestDTO dto, Pageable pageable);

    @Modifying
    @Query (value = "update MENU  set DEL_YN = :delYn where ID = :id", nativeQuery = true)
    void updateDelYn(String delYn, Long id);

    @Query (value = """
                    SELECT * FROM 
                        (SELECT ROW_NUMBER() OVER (ORDER BY mn.REG_DATE) AS rowNumber,
                            mn.ID,
                            mn.SITE_TYPE,
                            mn.NAME,
                            mn.ROUTE,
                            mn.PARENT_ID,
                            mn.DISPLAY_ORDER,
                            mn.DEL_YN,
                            mn.VN_NAME
                        FROM MENU mn
                        WHERE mn.DEL_YN = :delYn
                            AND mn.SITE_TYPE = :siteType
                        ) M
                        ORDER BY M.rowNumber
            """, nativeQuery = true)
    List<IMenuResponse> getListParent(@Param ("siteType") String siteType, String delYn);

    List<SysMenuEntity> findAllByParentId(Long parentId);

    @Query (value = """
                   SELECT COUNT(*)
                      FROM MENU
                      WHERE 
                      DEL_YN = :delYn
                      AND (:parentId IS NULL OR :parentId = '') AND (PARENT_ID IS NULL OR PARENT_ID = '')
                      OR PARENT_ID = :parentId
            """, nativeQuery = true)
    long countNumberChildrenMenu(String parentId, String delYn);

    @Query (value = """
            SELECT COUNT(*) FROM MENU
            WHERE ROUTE = :route
            AND DEL_YN = :delYn
            AND (:id IS NULL OR :id = '' OR ID <> :id)
            """, nativeQuery = true)
    int getDoubleRoute(@Param ("route") String route, String delYn, @Param ("id") Long id);

}
