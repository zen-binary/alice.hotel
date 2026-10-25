package com.winwintek.web.backend.api.repository.system;

import com.winwintek.web.backend.api.entity.system.VuserOutEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface IUserRepository extends JpaRepository<VuserOutEntity, Long> {

    @Query (value = """
            SELECT
             	CPS_USER.USER_ID AS userId
             	, CPS_USER.USER_PW AS password
             	, CPS_USER.USER_NAME AS userName
             	, '' AS colgCd
             	, '' AS colgCdNm
             	, '' AS mjCd
             	, '' AS mjCdNm
             	, '' AS shyrCd
             	, '' AS shyrCdNm
             	, '' AS shregSt
             	, '' AS shregStNm
             	, '' AS genFg
             	, '' AS genFgNm
             	, '' AS birthDt
             	, '' AS fullAddress
             	, CPS_USER.DIVISION AS division
             	, CPS_USER.USER_DIV AS userDiv
             	, (SELECT MAX(reg_date) FROM SYS_LOGIN_HISTORY WHERE USER_ID = CPS_USER.USER_ID) AS lastLoginDate
             	, (SELECT LISTAGG(sur.RL_ID, ',') WITHIN GROUP (ORDER BY sur.RL_ID) From SYS_USER_ROLE sur where sur.USER_ID= CPS_USER.USER_ID) AS userRoles
             FROM
             	CPS_USER CPS_USER
             WHERE
             	CPS_USER.USER_ID = :userId
             AND CPS_USER.USER_DIV = :userDiv
                """, nativeQuery = true)
    List<Map<String, Object>> selectByUserId(@Param ("userId") String userId, @Param ("userDiv") String userDiv);

    @Query (value = """
            SELECT
             	cu.USER_ID AS userId
             	, cu.USER_PW AS password
             	, cu.USER_NAME AS userName
             	, '' AS colgCd
             	, '' AS colgCdNm
             	, '' AS mjCd
             	, '' AS mjCdNm
             	, '' AS shyrCd
             	, '' AS shyrCdNm
             	, '' AS shregSt
             	, '' AS shregStNm
             	, '' AS genFg
             	, '' AS genFgNm
             	, '' AS birthDt
             	, '' AS fullAddress
             	, cu.DIVISION AS division
             	,'CORM' AS userDiv
                , CASE
                    WHEN cm.CORM_STATUS = 'CORM_02' THEN 'Y'
                    ELSE 'N'
                    END
                  AS policy
             	, (SELECT MAX(reg_date) FROM SYS_LOGIN_HISTORY WHERE USER_ID = cu.USER_ID) AS lastLoginDate
             	, (SELECT LISTAGG(sur.RL_ID, ',') WITHIN GROUP (ORDER BY sur.RL_ID) From SYS_USER_ROLE sur where sur.USER_ID= cu.USER_ID) AS userRoles
             FROM
             	CPS_USER cu
             LEFT JOIN CORPORATE_MEMBERS cm
             ON cu.USER_ID = cm.CORM_USER_ID
             WHERE
             	cu.USER_ID = :userId
             AND cu.USER_DIV = :userDiv
                """, nativeQuery = true)
    List<Map<String, Object>> selectByUserIdCorm(@Param ("userId") String userId, @Param ("userDiv") String userDiv);

    @Query (value = """
            SELECT
            CORM_STATUS AS cormStatus
            FROM CORPORATE_MEMBERS cm
            WHERE cm.CORM_USER_ID = :userId
                """, nativeQuery = true)
    List<Map<String, Object>> selectCormApprove(@Param ("userId") String userId);

}
