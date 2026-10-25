package com.winwintek.web.backend.api.dto.admin.qna;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QnaResDTO {

    private Integer rowNumber;

    private String qnaSeq;

    private String answerYn;

    private String title;

    private String regId;

    private String phone;

    private String email;

    private String regDate;

}
