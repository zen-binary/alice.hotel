package com.winwintek.web.backend.api.repository.admin.corporateChange;

import com.winwintek.web.backend.api.entity.admin.corporateChange.CorporateUserChange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CorporateUserChangeRepository extends JpaRepository<CorporateUserChange, String> {

}
