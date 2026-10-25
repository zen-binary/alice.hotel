package com.winwintek.web.backend.api.dto.admin.corporatePreTest;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CorporateMemberInfoDTO {

    private String cormSeq;

    private String cormNameKr;

    private String cormNameEn;

    private String cormType;

    private String cormCategories;

    private String corporatePreTestSeq;

    private String standardIndustryType;

    private String internPlanSeq;

    private List<PreTestDetailSelectDTO> preTestDetailSelectDTOList;

    private List<PreTestCategoryDTO> preTestCategoryDTOList;

}
