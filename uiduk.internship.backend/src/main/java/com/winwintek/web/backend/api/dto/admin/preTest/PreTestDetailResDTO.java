package com.winwintek.web.backend.api.dto.admin.preTest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PreTestDetailResDTO {

    private String preTestDetailSeq;

    private String preTestCategorySeq;

    private String content;

    private String displayOrder;

    private String addContentYn;

    private String checkYn;

    private String contentDetail;

}
