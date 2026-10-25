package com.winwintek.web.backend.api.dto.admin.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryCreResDTO {
    private String name;
    private String content;
    private Integer deleteFlag;
}
