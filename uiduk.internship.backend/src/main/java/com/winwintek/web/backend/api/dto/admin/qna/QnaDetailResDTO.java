package com.winwintek.web.backend.api.dto.admin.qna;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QnaDetailResDTO {

    private String qnaSeq;

    private String regId;

    private String phone;

    private String email;

    private String title;

    private String content;

    private String answer;

}
