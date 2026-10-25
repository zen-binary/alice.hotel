package com.alice.hotel.api.dto.common.nav;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MenuDto {
    private Long id;

    private String siteType;

    private String name;

    private String route;

    private Long parentId;

    private String subUrl;

    private String displayOrder;

    private String deleteFlag;

    private String vnName;
}
