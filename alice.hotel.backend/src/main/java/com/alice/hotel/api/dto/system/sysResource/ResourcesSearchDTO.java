package com.alice.hotel.api.dto.system.sysResource;

import com.alice.hotel.api.dto.common.pageable.PageableDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResourcesSearchDTO extends PageableDto {

    private String url;
    private String name;
    private String method;
    private String type;
    private String startDate;
    private String endDate;

}
