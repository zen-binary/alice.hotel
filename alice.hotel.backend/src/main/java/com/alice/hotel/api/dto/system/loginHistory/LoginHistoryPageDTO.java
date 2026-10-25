package com.alice.hotel.api.dto.system.loginHistory;

import com.alice.hotel.api.dto.common.pageable.PageableDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginHistoryPageDTO extends PageableDto {

    private String strDate;

    private String endDate;

    private String authority;

    private String id;

    private String name;

    private String ipAccess;

}
