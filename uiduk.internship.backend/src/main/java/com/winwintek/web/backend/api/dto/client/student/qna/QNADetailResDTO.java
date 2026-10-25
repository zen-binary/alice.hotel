package com.winwintek.web.backend.api.dto.client.student.qna;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QNADetailResDTO {
    private String title;
    private String content;
    private String regDate;
    private String answer;
    private String modifyId;
    private String modifyDate;
}
