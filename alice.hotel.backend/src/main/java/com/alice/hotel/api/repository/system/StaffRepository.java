package com.alice.hotel.api.repository.system;

import com.alice.hotel.api.entity.system.VStaffEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface StaffRepository extends JpaRepository<VStaffEntity, Long> {
//    StaffEntity findByNickName(String nickName);

    @Query (value = """
            SELECT
               VSCF.STAFF_NO AS userId
               , VSCF.PW
               , VSCF.KOR_NM AS userName
               , '' AS colgCd
               , '' AS colgCdNm
               , VSCF.DEPT_CD AS sustCd
               , VSCF.DEPT_NM AS sustCdNm
               , '' AS mjCd
               , '' AS mjCdNm
               , '' AS shyrCd
               , '' AS shyrCdNm
               , '' AS shregSt
               , '' AS shregStNm
               , '' AS genFg
               , '' AS genFgNm
               , '' AS birthDt
               , VSCF.OFFICE_CALL AS telNo
               , VSCF.EMAIL
               , VSCF.PHONE
               , '' AS fullAddress
               , 'STF' AS division
               , 'STF' AS userdiv
               , VSCF.WKGD_NM as wkgdNm
               , (SELECT MAX(reg_date) FROM SYS_LOGIN_HISTORY WHERE USER_ID = :userId) AS lastLoginDate
               , (SELECT GROUP_CONCAT(sur.RL_ID ORDER BY sur.RL_ID SEPARATOR ',') From SYS_USER_ROLE sur where sur.USER_ID=:userId) AS userRoles
               FROM
                   V_STD_CDP_STAF VSCF
               WHERE
                   trim(VSCF.STAFF_NO) = :userId
            """, nativeQuery = true)
    List<Map<String, Object>> selectByUserId(@Param ("userId") String userId);


    @Query (value = """
            SELECT
               VSCF.STAFF_NO AS userId
               , VSCF.PW
               , VSCF.KOR_NM AS userName
               , '' AS colgCd
               , '' AS colgCdNm
               , VSCF.DEPT_CD AS sustCd
               , VSCF.DEPT_NM AS sustCdNm
               , '' AS mjCd
               , '' AS mjCdNm
               , '' AS shyrCd
               , '' AS shyrCdNm
               , '' AS shregSt
               , '' AS shregStNm
               , '' AS genFg
               , '' AS genFgNm
               , '' AS birthDt
               , VSCF.OFFICE_CALL AS telNo
               , VSCF.EMAIL
               , VSCF.PHONE
               , '' AS fullAddress
               , 'ADM' AS division
               , 'ADM' AS userdiv
               , VSCF.WKGD_NM as wkgdNm
               , (SELECT MAX(reg_date) FROM SYS_LOGIN_HISTORY WHERE USER_ID = :userId) AS lastLoginDate
               , (SELECT GROUP_CONCAT(sur.RL_ID ORDER BY sur.RL_ID SEPARATOR ',') From SYS_USER_ROLE sur where sur.USER_ID=:userId) AS userRoles
               FROM
                   V_STD_CDP_STAF VSCF
               WHERE trim(VSCF.STAFF_NO) = :userId
                    or (UPPER(trim(VSCF.DEPT_NM)) LIKE UPPER(CONCAT('%', :deptNm, '%')))
            """, nativeQuery = true)
    List<Map<String, Object>> selectByUserIdFromLINC(@Param ("userId") String userId, @Param ("deptNm") String deptNm);

    VStaffEntity findByStaffNo(String staffNo);

}
