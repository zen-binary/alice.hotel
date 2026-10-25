package com.winwintek.web.backend.api.entity.client.internMng;

public interface IInternMngResponeListDTO {

    String getInternPlanSeq();

    String getInternPlanNm();

    String getInternshipField();

    String getInternshipStr();

    String getInternshipEnd();

    String getCormNameKr();

    String getCormNameEn();

    String getCormType();

    String getAddress();

    String getGuidanceReportSeq(); // id Instructions report

    String getInternProcessEvaluationSeq(); // id  Instructor's evaluation

    String getInternshipStatus();

    String getIsShowReport();

}
