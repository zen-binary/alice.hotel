package com.winwintek.web.backend.api.dto.client.student.internStudent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InternStudentListResDTO {

    private String internStdSeq;
    private String internshipName;
    private String internPlanSeq;
    private String approveStatusCd;
    private String approveStatusNm;
    private String internshipStatusCd;
    private String internshipStatusNm;
    private String internshipField;
    private String cormName;
    private String address;
    private String internshipStr;
    private String internshipEnd;
    private String corporateSignYn;
    private String tripartiteAgreementSeq;
    private String urlFile;
}
