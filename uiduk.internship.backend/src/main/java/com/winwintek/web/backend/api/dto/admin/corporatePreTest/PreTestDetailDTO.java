package com.winwintek.web.backend.api.dto.admin.corporatePreTest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PreTestDetailDTO {

    private String preTestDetailSeq;

    private String preTestCategorySeq;

    private String content;

    private String displayOrder;

    private String addContentYn;

    private String checkYn;

    private String contentDetail;

}
