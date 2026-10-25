package com.winwintek.web.backend.api.dto.client.student.reportWeek;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeekClientDTO {

    private String stdNm;

    private String deptNm;

    private String stdYear;

    private String stdNo;

    private String cormSeq;

    private String internshipStatus;

    private List<ListWeekClient> lstWeek;

}
