package com.winwintek.web.backend.api.dto.admin.qna;

import com.winwintek.web.backend.api.dto.common.pageable.PageableDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QnaSearchDTO extends PageableDto {

    private String title;

    private String answerYn;

    private String regName;

    private String phone;

    private String email;

    private String startDate;

    private String endDate;

}
