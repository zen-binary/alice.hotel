package com.winwintek.web.backend.api.domain.admin.corporateChange.service;

import com.winwintek.web.backend.api.dto.admin.corporateChange.CorporateInfoChangeRequest;
import com.winwintek.web.backend.api.dto.admin.corporateChange.CorporateInfoChangeResponse;

public interface ICorporateChangeService {

    CorporateInfoChangeResponse detailCorporate();

    String updateCorporate(CorporateInfoChangeRequest request);

}
