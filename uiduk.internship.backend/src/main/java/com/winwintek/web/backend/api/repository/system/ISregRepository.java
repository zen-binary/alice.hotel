package com.winwintek.web.backend.api.repository.system;

import com.winwintek.web.backend.api.entity.system.VSregEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface ISregRepository extends JpaRepository<VSregEntity, Long> {
// Server operate
//    @Query (value = """
//            SELECT
//            	VSCS.STD_NO AS userId
//                , '' as password
//                , VSCS.KOR_NM AS userName
//                , VSCS.COLG_CD AS colgCd
//                , VSCS.COLG_NM AS colgCdNM
//                , VSCS.SUST_CD AS sustCd
//                , VSCS.SUST_NM AS sustCdNm
//                , VSCS.MJ_CD AS mjCd
//                , VSCS.MJ_NM AS mjCdNm
//                , VSCS.SHREG_ST AS shregSt
//                , VSCS.SHREG_ST_NM AS shregStNm
//                , VSCS.GEN_FG_CD AS genFg
//                , VSCS.GEN_FG_NM AS GenFgNm
//                , '' AS birthDt
//            	, '' AS telNo
//                , VSCS.EMAIL AS email
//                , VSCS.HAND_NO AS phone
//                , '' AS fullAddress
//            	, 'STD' AS division
//            	, 'STD' AS userDiv
//            	, '학생' AS wkgdNm
//            	, (SELECT MAX(reg_date) FROM SYS_LOGIN_HISTORY WHERE USER_ID = :userId) AS lastLoginDate
//            	, (SELECT LISTAGG(sur.RL_ID, ',') WITHIN GROUP (ORDER BY sur.RL_ID) From SYS_USER_ROLE sur where sur.USER_ID=:userId) AS userRoles
//            FROM
//            	V_STD_CDP_SREG VSCS
//            WHERE
//            	VSCS.STD_NO = :userId
//                """, nativeQuery = true)
//    List<Map<String, Object>> selectByUserId(@Param ("userId") String userId);

    // sever develop
    @Query (value = """
            SELECT
            	VSCS.STD_NO AS userId
                , '' as password
                , VSCS.KOR_NM AS userName
                , VSCS.COLG_CD AS colgCd
                , VSCS.COLG_CD_NM AS colgCdNM
                , VSCS.SUST_CD AS sustCd
                , VSCS.SUST_CD_NM AS sustCdNm
                , VSCS.MJ_CD AS mjCd
                , VSCS.MJ_CD_NM AS mjCdNm
                , VSCS.SHREG_ST AS shregSt
                , VSCS.SHREG_ST_NM AS shregStNm
                , VSCS.GEN_FG AS genFg
                , VSCS.GEN_FG_NM AS GenFgNm
                , VSCS.SHYR_CD AS shyrCd
                , VSCS.SHYR_CD_NM AS shyrCdNm
                , '' AS birthDt
            	, '' AS telNo
                , VSCS.EMAIL AS email
                , VSCS.HAND_NO AS phone
                , '' AS fullAddress
            	, 'STD' AS division
            	, 'STD' AS userDiv
            	, '학생' AS wkgdNm
            	, (SELECT MAX(reg_date) FROM SYS_LOGIN_HISTORY WHERE USER_ID = VSCS.STD_NO) AS lastLoginDate
            	, (SELECT LISTAGG(sur.RL_ID, ',') WITHIN GROUP (ORDER BY sur.RL_ID) From SYS_USER_ROLE sur where sur.USER_ID= VSCS.STD_NO) AS userRoles
            	, (SELECT PUBLIC_INFO
                   FROM SYS_USER_ROLE sur WHERE USER_ID = VSCS.STD_NO) AS publicInfo                                                                                  
            FROM
            	V_STD_CDP_SREG VSCS
            WHERE
            	VSCS.STD_NO = :userId
            """, nativeQuery = true)
    List<Map<String, Object>> selectByUserId(@Param ("userId") String userId);

}

