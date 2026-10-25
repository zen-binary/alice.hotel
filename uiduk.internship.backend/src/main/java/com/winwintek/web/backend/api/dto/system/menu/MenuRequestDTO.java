package com.winwintek.web.backend.api.dto.system.menu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenuRequestDTO {

    private Long id;
    private String siteType;
    private String name;
    private String route;
    private Long parentId;
    private String vnName;
    private String screenName;
    private Integer displayOrder;
}
