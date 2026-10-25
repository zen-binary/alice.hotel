package com.winwintek.web.backend.api.entity.client.student.internStudent;

public interface IInternStudentRegOutput {
    public String getInternStdSeq();

    public String getInternshipName();

    public String getInternPlanSeq();

    public String getApproveStatusCd();
    public String getApproveStatusNm();

    public String getInternshipStatusCd();
    public String getInternshipStatusNm() ;

    public String getInternshipField();
    public String getCormName();

    public String getAddress();

    String getInternshipStr();
    String getInternshipEnd();
    String getCorporateSignYn();

    String getTripartiteAgreementSeq();
    String getUrlFile();
}
