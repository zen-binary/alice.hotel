package com.winwintek.web.backend.api.domain.client.student.signedAgreement.service;

import com.winwintek.web.backend.api.dto.client.student.signedAgreement.SignedAgreementResDTO;

public interface ISignedAgreementService {
    SignedAgreementResDTO getSignedAgreement(String internStdSeq);
    boolean studentSignAgreement(String id);
}
