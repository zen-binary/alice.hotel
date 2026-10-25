package com.winwintek.web.backend.api.dto.client.student.summaryReportStudent;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class SummaryReportStudentResDTO {

    private String internStdId;

    private String stdSummaryReportId;

    private String stdNm;

    private String deptNm;

    private String stdYear;

    private String stdNo;

    private String content;

    private String internPlanSeq;

    private String internshipStatus;

}
