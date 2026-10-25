package com.winwintek.web.backend.api.dto.admin.corporate.internCorporateSurvey;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CorporateSurveyQusDTO {

    private String corporateSurveyQusSeq;

    private String qusContent;

    private Integer displayOrder;

    private String corporateSurveyAnsSeq;

    private String score;

}
