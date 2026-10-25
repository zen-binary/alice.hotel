package com.winwintek.web.backend.api.dto.admin.preTest;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CorporateMemberReqDTO {

    private String cormSeq;

    private String cormNameKr;

    private String cormNameEn;

    private String cormType;

    private String cormCategories;

    private String corporatePreTestSeq;

    private String standardIndustryType;

    private String internPlanSeq;

    private List<PreTestDetailSelectResDTO> preTestDetailSelectResDTOList;

    private List<PreTestCategoryResDTO> preTestCategoryResDTOList;


}
