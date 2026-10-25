package com.alice.hotel.api.repository.system.loginHistory;

import com.alice.hotel.api.dto.system.loginHistory.LoginHistoryPageDTO;
import com.alice.hotel.api.entity.system.loginHistory.ISysRoleDTO.ILoginHisDTO;
import com.alice.hotel.api.entity.system.loginHistory.ISysRoleDTO.ISysRoleDTO;
import com.alice.hotel.api.entity.system.loginHistory.LoginHistoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginHisRepository extends JpaRepository<LoginHistoryEntity, String> {
    @Query(value = """
            SELECT
                rowNum, loginDate, roleInfo, slId, userNm, loginIp, accessType, connOs,
                browserInfo, userId, userDiv, loginYn, loginMsg, loginType
            FROM (
                SELECT
                   ROW_NUMBER() OVER (ORDER BY h.REG_DATE ASC) AS rowNum
                  , DATE_FORMAT(h.REG_DATE, '%Y-%m-%d %H:%i:%s') AS loginDate
                  , NULL AS roleInfo
                  , CAST(h.LH_ID AS CHAR) AS slId
                  , u.USER_NAME AS userNm
                  , NULL AS loginIp
                  , NULL AS accessType
                  , NULL AS connOs
                  , NULL AS browserInfo
                  , h.USER_ID AS userId
                  , u.USER_DIV AS userDiv
                  , 'Thành công' AS loginYn
                  , NULL AS loginMsg
                  , NULL AS loginType
                FROM SYS_LOGIN_HISTORY h
                LEFT JOIN CPS_USER u ON u.USER_ID = h.USER_ID
                WHERE (NULLIF(:#{#loginHistoryPageDTO.strDate}, '') IS NULL OR DATE_FORMAT(h.REG_DATE, '%Y-%m-%d') >= :#{#loginHistoryPageDTO.strDate})
                  AND (NULLIF(:#{#loginHistoryPageDTO.endDate}, '') IS NULL OR DATE_FORMAT(h.REG_DATE, '%Y-%m-%d') <= :#{#loginHistoryPageDTO.endDate})
                  AND (NULLIF(:#{#loginHistoryPageDTO.id}, '') IS NULL OR h.USER_ID LIKE CONCAT('%', :#{#loginHistoryPageDTO.id}, '%'))
                  AND (NULLIF(:#{#loginHistoryPageDTO.name}, '') IS NULL OR u.USER_NAME LIKE CONCAT('%', :#{#loginHistoryPageDTO.name}, '%'))
            ) t
            ORDER BY rowNum DESC
            """,
            countQuery = """
            SELECT COUNT(*)
            FROM SYS_LOGIN_HISTORY h
            LEFT JOIN CPS_USER u ON u.USER_ID = h.USER_ID
            WHERE (NULLIF(:#{#loginHistoryPageDTO.strDate}, '') IS NULL OR DATE_FORMAT(h.REG_DATE, '%Y-%m-%d') >= :#{#loginHistoryPageDTO.strDate})
              AND (NULLIF(:#{#loginHistoryPageDTO.endDate}, '') IS NULL OR DATE_FORMAT(h.REG_DATE, '%Y-%m-%d') <= :#{#loginHistoryPageDTO.endDate})
              AND (NULLIF(:#{#loginHistoryPageDTO.id}, '') IS NULL OR h.USER_ID LIKE CONCAT('%', :#{#loginHistoryPageDTO.id}, '%'))
              AND (NULLIF(:#{#loginHistoryPageDTO.name}, '') IS NULL OR u.USER_NAME LIKE CONCAT('%', :#{#loginHistoryPageDTO.name}, '%'))
            """,
            nativeQuery = true)
    Page<ILoginHisDTO> selectLoginHis(Pageable pageable, LoginHistoryPageDTO loginHistoryPageDTO);

    @Query(value = """
        SELECT DISTINCT(RL_NM) FROM SYS_ROLE
    """, nativeQuery = true)
    List<ISysRoleDTO> selectRole();

}
