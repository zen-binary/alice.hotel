package com.winwintek.web.backend.api.dto.system.menuMng;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MenuDTO {
    private Long id;

    private String siteType;

    private String name;

    private String route;

    private Long parentId;

    private Integer displayOrder;
}
