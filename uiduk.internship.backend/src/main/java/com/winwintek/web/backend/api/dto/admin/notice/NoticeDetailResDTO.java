package com.winwintek.web.backend.api.dto.admin.notice;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeDetailResDTO {

    private String noticeSeq;

    private String title;

    private String content;

    private String startDateShow;

    private String endDateShow;

    private String useYn;

    private String topFixYn;

}
