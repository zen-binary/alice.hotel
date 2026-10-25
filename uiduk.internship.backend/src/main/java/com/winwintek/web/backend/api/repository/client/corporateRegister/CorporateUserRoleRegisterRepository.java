package com.winwintek.web.backend.api.repository.client.corporateRegister;

import com.winwintek.web.backend.api.entity.client.corporateRegister.CorporateUserRoleRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CorporateUserRoleRegisterRepository extends JpaRepository<CorporateUserRoleRegister, Long> {

}
