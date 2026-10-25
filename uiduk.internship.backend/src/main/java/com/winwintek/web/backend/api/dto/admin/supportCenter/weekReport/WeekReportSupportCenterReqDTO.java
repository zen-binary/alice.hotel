package com.winwintek.web.backend.api.dto.admin.supportCenter.weekReport;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class WeekReportSupportCenterReqDTO {

    private String stdWeekReportSeq;

    private String internDetailSeq;

    private String internStdSeq;

    private String dateWeek;

    private String inHour;

    private String inMinute;

    private String outHour;

    private String outMinute;

    private String jobContent;

    private String acquiredKnowledge;

}
