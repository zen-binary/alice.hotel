package com.winwintek.web.backend.api.dto.client.student.schoolNotice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SchoolNoticeDetailResDTO {
    private String title;
    private String regDate;
    private String content;

}
