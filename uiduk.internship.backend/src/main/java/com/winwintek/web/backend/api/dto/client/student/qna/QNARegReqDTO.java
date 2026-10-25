package com.winwintek.web.backend.api.dto.client.student.qna;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QNARegReqDTO {
    private String title;
    private String content;
}
