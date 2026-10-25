package com.winwintek.web.backend.api.domain.admin.preTest.service;

import com.winwintek.web.backend.api.dto.admin.preTest.CorporateMemberReqDTO;
import com.winwintek.web.backend.api.dto.admin.preTest.CorporatePreTestSeqDTO;

public interface PreTestService {

    CorporateMemberReqDTO getCorPreTestList(CorporatePreTestSeqDTO corporatePreTestSeqDTO);

}
