package com.winwintek.web.backend.api.dto.client.student.dashboard.qna;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DashboardQNAResDTO {
    private String qnaSeq;
    private String title;
    private String regDate;
}
