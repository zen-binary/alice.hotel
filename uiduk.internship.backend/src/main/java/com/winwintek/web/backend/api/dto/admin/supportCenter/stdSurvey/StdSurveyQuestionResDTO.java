package com.winwintek.web.backend.api.dto.admin.supportCenter.stdSurvey;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StdSurveyQuestionResDTO {

    private String surveyQuestId;

    private String questContent;

    private String displayOrder;

    private Integer score;

}
