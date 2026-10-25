package com.winwintek.web.backend.api.dto.admin.corporatePreTest;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PreTestCategoryDTO {

    private String preTestCategorySeq;

    private String categoryIndex;

    private String content;

    private String displayOrder;

    private List<PreTestDetailDTO> preTestDetailDTOList;

}
