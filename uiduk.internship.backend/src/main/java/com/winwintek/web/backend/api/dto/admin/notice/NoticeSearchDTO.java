package com.winwintek.web.backend.api.dto.admin.notice;

import com.winwintek.web.backend.api.dto.common.pageable.PageableDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeSearchDTO extends PageableDto {

    private String title;

    private String startDateShow;

    private String endDateShow;

    private String useYn;

    private String startDate;

    private String endDate;

    private String regName;

}
