package com.winwintek.web.backend.api.dto.admin.notice;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeResDTO {

    private Integer noId;

    private String noticeSeq;

    private String useYn;

    private String title;

    private String startDateShow;

    private String endDateShow;

    private String regDate;

    private String regId;

}
