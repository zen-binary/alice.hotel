package com.winwintek.web.backend.api.dto.client.student.internStdSurvey;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InternStdSurveyDetailReqDTO {

    private String studentName;

    private String shyrCode;

    private String internTerm;

    private String internPlanSeq;

    private String internStdSeq;

    private String internRecommend;

    private String internStdSurveySeq;

    private List<StdSurveyAnsDTO> stdSurveyAnsDTOList;

}
