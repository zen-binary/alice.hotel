package com.winwintek.web.backend.api.dto.admin.corporate.internCorporateSurvey;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InternCorporateSurveyDTO {

    private String internPlanSeq;

    private String cormNm;

    private Integer internQuantity;

    private String internshipTerm;

    private String internCorporateSurveySeq;

    private String internRecommend;

    private List<CorporateSurveyQusDTO> listQus;

}
