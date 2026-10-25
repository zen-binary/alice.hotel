package com.winwintek.web.backend.api.dto.admin.preTest;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PreTestCategoryResDTO {

    private String preTestCategorySeq;

    private String categoryIndex;

    private String content;

    private String displayOrder;

    private List<PreTestDetailResDTO> preTestDetailResDTOList;

}
