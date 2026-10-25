package com.winwintek.web.backend.api.repository.admin.corporateChange;

import com.winwintek.web.backend.api.entity.admin.corporateChange.CorporateChange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CorporateChangeRepository extends JpaRepository<CorporateChange, String> {
    @Query("""
    select c2 from CorporateChange c2 where c2.cormSeq = :cormSeq and c2.delYn = :delYn
    """)
    List<CorporateChange> getLstCorporate(@Param("cormSeq") String cormSeq, @Param("delYn") String delYn);
}
