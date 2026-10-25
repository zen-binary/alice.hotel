package com.alice.hotel.api.dto.common.pageable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageableDto {

    private int page;

    private int size;

    private String sort;

}
