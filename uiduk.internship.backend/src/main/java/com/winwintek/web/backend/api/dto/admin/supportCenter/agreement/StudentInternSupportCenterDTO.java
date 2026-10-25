package com.winwintek.web.backend.api.dto.admin.supportCenter.agreement;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StudentInternSupportCenterDTO {

    private String id;

    private String name;

    private String corpSignedYn;

    private String stdSignedYn;

    private String univSignedYn;

    private String tripartiteAgreementSeq;

    private String uniSignedDate;

    private String urlFile;

    private String deptStdNm;

}
