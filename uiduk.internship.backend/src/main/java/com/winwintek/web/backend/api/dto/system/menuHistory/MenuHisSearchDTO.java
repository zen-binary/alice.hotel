package com.winwintek.web.backend.api.dto.system.menuHistory;

import com.winwintek.web.backend.api.dto.common.pageable.PageableDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenuHisSearchDTO extends PageableDto {

    private String strDate;

    private String endDate;

    private String user;

    private String name;
}
