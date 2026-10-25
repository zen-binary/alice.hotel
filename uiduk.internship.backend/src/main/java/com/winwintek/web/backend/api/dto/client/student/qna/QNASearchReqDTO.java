package com.winwintek.web.backend.api.dto.client.student.qna;

import com.winwintek.web.backend.api.dto.common.pageable.PageableDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QNASearchReqDTO extends PageableDto {
    private String title;
}
