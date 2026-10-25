package com.winwintek.web.backend.api.dto.admin.corporate.signed;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StudentInternCorporate {

    private String internStdSeq;

    private String internStdNm;

    private String internStdDeptNm;

    private String corporateSignedYn;

    private String tripartiteAgreementSeq;

    private String corporateSignedDate;

    private String stdSignedYn;

    private String univSignedYn;

}
