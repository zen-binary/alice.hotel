package com.winwintek.web.backend.api.dto.client.student.schoolNotice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SchoolNoticeListResDTO {
    private int rowNumber;
    private String noticeSeq;
    private String title;
    private String regDate;
    private String topFixYn;
}

