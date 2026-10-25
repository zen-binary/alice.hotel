package com.alice.hotel.api.dto.system.menu;

import com.alice.hotel.api.dto.common.pageable.PageableDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenuSearchRequestDTO extends PageableDto {

    private String vnName;
    private String name;
    private String route;

}
