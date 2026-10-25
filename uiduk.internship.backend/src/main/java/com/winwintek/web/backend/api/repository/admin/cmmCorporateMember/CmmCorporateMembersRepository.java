package com.winwintek.web.backend.api.repository.admin.cmmCorporateMember;

import com.winwintek.web.backend.api.entity.admin.cmnCorporateMembers.CmmCorporateMembersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CmmCorporateMembersRepository extends JpaRepository<CmmCorporateMembersEntity, String> {

    CmmCorporateMembersEntity findByCormUserIdAndUseYnAndDelYn(String cormUserId, String useYn, String delYn);

}
