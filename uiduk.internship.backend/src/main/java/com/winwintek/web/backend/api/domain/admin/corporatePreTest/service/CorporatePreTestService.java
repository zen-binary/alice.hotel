package com.winwintek.web.backend.api.domain.admin.corporatePreTest.service;

import com.winwintek.web.backend.api.dto.admin.corporatePreTest.CorporateMemberInfoDTO;
import com.winwintek.web.backend.api.dto.admin.corporatePreTest.CorporatePreTestIdDTO;

public interface CorporatePreTestService {

    CorporateMemberInfoDTO getCorPreTestList(CorporatePreTestIdDTO corporatePreTestIdDTO );

    CorporateMemberInfoDTO getDetailCorporatePreTest(String internPlanSeq );

    Boolean insertCorporatePreTest(CorporateMemberInfoDTO memberInfoDTO);
}
