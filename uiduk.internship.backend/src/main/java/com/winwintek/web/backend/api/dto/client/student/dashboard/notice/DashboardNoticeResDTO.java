package com.winwintek.web.backend.api.dto.client.student.dashboard.notice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DashboardNoticeResDTO {
    private String noticeSeq;
    private String title;
    private String regDate;
}
