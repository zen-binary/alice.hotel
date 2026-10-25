package com.winwintek.web.backend.api.repository.admin.corporateApproval;

import com.winwintek.web.backend.api.entity.admin.corporateApproval.CorporateManagerApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CorporateManagerApprovalRepository extends JpaRepository<CorporateManagerApproval, String> {

    @Query ("""
    select c2 from CorporateManagerApproval c2 where c2.cormSeq = :cormSeq and c2.delYn = :delYn
    """)
    List<CorporateManagerApproval> getLstCorporateManager(@Param ("cormSeq") String cormSeq, @Param("delYn") String delYn);

}
