package com.winwintek.web.backend.api.dto.admin.supportCenter.stdSurvey;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class StdSurveySupportCenterResDTO {

    private String stdSurveyId;

    private String stdNm;

    private String shyrCd;

    private String shyrNm;

    private String recommend;

    private String termCd;

    private String termNm;

    private List<StdSurveyQuestionResDTO> lstSurveyQuest;

}
