package com.winwintek.web.backend.api.dto.admin.supportCenter.corpSurvey;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CorpSurveySupportCenterResDTO {

    private String corpSurveyId;

    private String corporateNm;

    private Integer numberIntern;

    private String termCd;

    private String termNm;

    private String recommend;

    private List<CorpSurveyQuestionResDTO> lstSurveyQuest;

}
