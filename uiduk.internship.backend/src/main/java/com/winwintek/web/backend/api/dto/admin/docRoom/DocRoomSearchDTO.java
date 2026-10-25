package com.winwintek.web.backend.api.dto.admin.docRoom;

import com.winwintek.web.backend.api.dto.common.pageable.PageableDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DocRoomSearchDTO extends PageableDto {

    private String title;

    private String createdBy;

    private String useYn;

    private String regStartDate;

    private String regEndDate;

}
