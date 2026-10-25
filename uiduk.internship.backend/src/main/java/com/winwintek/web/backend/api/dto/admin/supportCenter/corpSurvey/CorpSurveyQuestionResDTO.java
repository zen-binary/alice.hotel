package com.winwintek.web.backend.api.dto.admin.supportCenter.corpSurvey;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CorpSurveyQuestionResDTO {

    private String surveyQuestId;

    private String questContent;

    private String displayOrder;

    private Integer score;

}
