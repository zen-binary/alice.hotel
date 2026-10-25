package com.winwintek.web.backend.api.dto.admin.category;

import com.winwintek.web.backend.api.dto.common.pageable.PageableDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryPageDto extends PageableDto {
    private String name;
}
