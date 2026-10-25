package com.winwintek.web.backend.api.dto.client.student.schoolNotice;

import com.winwintek.web.backend.api.dto.common.pageable.PageableDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SchoolNoticeReqDTO extends PageableDto {
    private String title;
}

