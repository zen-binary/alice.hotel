package com.winwintek.web.backend.api.dto.client.internMng;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InternMngListDTO {

    private String internPlanSeq;

    private String internPlanNm;

    private String internshipField;

    private String internshipStr;

    private String internshipEnd;

    private String cormNameKr;

    private String cormNameEn;

    private String cormType;

    private String address;

    private String guidanceReportSeq;

    private String internProcessEvaluationSeq;

    private String internshipStatus;

    private String isShowReport;

}
