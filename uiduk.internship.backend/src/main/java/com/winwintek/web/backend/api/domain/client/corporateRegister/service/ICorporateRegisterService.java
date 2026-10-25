package com.winwintek.web.backend.api.domain.client.corporateRegister.service;

import com.winwintek.web.backend.api.dto.client.corporateRegister.CorporateInfoRegisterRequest;

public interface ICorporateRegisterService {
    String createCorporate(CorporateInfoRegisterRequest request);

    boolean isExistRegister(String userId);
}
