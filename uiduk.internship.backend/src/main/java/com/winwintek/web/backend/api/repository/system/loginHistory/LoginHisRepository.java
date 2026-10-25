package com.winwintek.web.backend.api.repository.system.loginHistory;

import com.winwintek.web.backend.api.dto.system.loginHistory.LoginHistoryPageDTO;
import com.winwintek.web.backend.api.entity.system.loginHistory.ISysRoleDTO.ILoginHisDTO;
import com.winwintek.web.backend.api.entity.system.loginHistory.ISysRoleDTO.ISysRoleDTO;
import com.winwintek.web.backend.api.entity.system.loginHistory.LoginHistoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginHisRepository extends JpaRepository<LoginHistoryEntity, String> {
    @Query (value = """
            SELECT
                ROW_NUM
                  , LOGIN_DATE
                  , ROLE_INFO
                  , SL_ID
                  , USER_NM
                  , LOGIN_IP
                  , ACCESS_TYPE
                  , CONN_OS
                  , BROWSER_INFO
                  , USER_ID
                  , USER_DIV
                  , LOGIN_YN
                  , LOGIN_MSG
                  , LOGIN_TYPE
                 FROM(
                SELECT
                   ROW_NUMBER() OVER (ORDER BY REG_DATE ASC) AS ROW_NUM
                  , REG_DATE AS LOGIN_DATE
                  , ROLE_INFO
                  , USER_ID AS SL_ID
                  , USER_NM
                  , LOGIN_IP
                  , ACCESS_TYPE
                  , CONN_OS
                  , BROWSER_INFO
                  , USER_ID
                  , USER_DIV
                  , CASE
                        WHEN LOGIN_YN = :stsN THEN '성공'
                        WHEN LOGIN_YN = :stsY THEN '미성공'
                        ELSE '-'
                    END AS LOGIN_YN
                  , LOGIN_MSG
                  , LOGIN_TYPE
                  FROM SYS_LOGIN_HISTORY
                   WHERE (:#{#loginHistoryPageDTO.strDate} IS NULL OR TO_CHAR(REG_DATE, 'yyyy-MM-dd') >= :#{#loginHistoryPageDTO.strDate} )
                   AND (:#{#loginHistoryPageDTO.endDate} IS NULL OR TO_CHAR(REG_DATE, 'yyyy-MM-dd') <= :#{#loginHistoryPageDTO.endDate} ) 
                   AND (:#{#loginHistoryPageDTO.authority} IS NULL OR ROLE_INFO = :#{#loginHistoryPageDTO.authority} )
                   AND (:#{#loginHistoryPageDTO.id} IS NULL OR USER_ID LIKE %:#{#loginHistoryPageDTO.id}% )
                   AND (:#{#loginHistoryPageDTO.name} IS NULL OR USER_NM LIKE %:#{#loginHistoryPageDTO.name}% )
                   AND (:#{#loginHistoryPageDTO.ipAccess} IS NULL OR LOGIN_IP LIKE %:#{#loginHistoryPageDTO.ipAccess}% )
                   )
                ORDER BY ROW_NUM DESC
            """,countQuery =
            """
                    SELECT
                COUNT(*)
                 FROM(
                SELECT
                   ROW_NUMBER() OVER (ORDER BY REG_DATE ASC) AS ROW_NUM
                  , REG_DATE AS LOGIN_DATE
                  , ROLE_INFO
                  , USER_ID AS SL_ID
                  , USER_NM
                  , LOGIN_IP
                  , ACCESS_TYPE
                  , CONN_OS
                  , BROWSER_INFO
                  , USER_ID
                  , USER_DIV
                  ,CASE
                        WHEN LOGIN_YN = :stsN THEN '성공'
                        WHEN LOGIN_YN = :stsY THEN '미성공'
                        ELSE '-'
                    END AS LOGIN_YN
                  , LOGIN_MSG
                  , LOGIN_TYPE
                  FROM SYS_LOGIN_HISTORY
                  WHERE (:#{#loginHistoryPageDTO.strDate} IS NULL OR TO_CHAR(REG_DATE, 'yyyy-MM-dd') >= :#{#loginHistoryPageDTO.strDate} )
                   AND (:#{#loginHistoryPageDTO.endDate} IS NULL OR TO_CHAR(REG_DATE, 'yyyy-MM-dd') <= :#{#loginHistoryPageDTO.endDate} ) 
                   AND (:#{#loginHistoryPageDTO.authority} IS NULL OR ROLE_INFO = :#{#loginHistoryPageDTO.authority} )
                   AND (:#{#loginHistoryPageDTO.id} IS NULL OR USER_ID LIKE %:#{#loginHistoryPageDTO.id}% )
                   AND (:#{#loginHistoryPageDTO.name} IS NULL OR USER_NM LIKE %:#{#loginHistoryPageDTO.name}% )
                   AND (:#{#loginHistoryPageDTO.ipAccess} IS NULL OR LOGIN_IP LIKE %:#{#loginHistoryPageDTO.ipAccess}% )
                   
                  )
                ORDER BY ROW_NUM DESC
                    """
            ,nativeQuery = true)
    Page<ILoginHisDTO> selectLoginHis(Pageable pageable,
            LoginHistoryPageDTO loginHistoryPageDTO,
            @Param ("stsN") String stsN,
            @Param("stsY") String stsY);

    @Query(value = """
        SELECT DISTINCT(RL_NM) FROM SYS_ROLE
    """, nativeQuery = true)
    List<ISysRoleDTO> selectRole();

}
