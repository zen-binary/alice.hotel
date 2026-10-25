package com.winwintek.web.backend.api.dto.client.student.reportWeek;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeekReportCreateDTO {
    private String internDetailId;
    private String internStdSeq;
    private Date dateWeek;
    private String inHour;
    private String inMinute;
    private String outHour;
    private String outMinute;
    private String jobContent;
    private String acquiredKnowledge;

}
