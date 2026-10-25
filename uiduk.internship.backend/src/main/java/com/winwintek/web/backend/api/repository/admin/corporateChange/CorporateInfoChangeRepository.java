package com.winwintek.web.backend.api.repository.admin.corporateChange;

import com.winwintek.web.backend.api.entity.admin.corporateChange.CorporateInfoChange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CorporateInfoChangeRepository extends JpaRepository<CorporateInfoChange, String> {

    @Query("""
    select c from CorporateInfoChange c where c.cormUserId = :userId and c.delYn = :delYn
    """)
    Optional<CorporateInfoChange> corporateInfoDetail(@Param("userId") String userId,@Param("delYn") String delYn);

}
