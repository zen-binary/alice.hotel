package com.winwintek.web.backend.api.dto.client.student.qna;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class QNAResDTO {
    private String qnaSeq;
    private String title;
    private String regDate;
    private String answer;
}
