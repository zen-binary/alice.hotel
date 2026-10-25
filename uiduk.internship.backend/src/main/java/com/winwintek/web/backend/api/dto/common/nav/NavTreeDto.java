package com.winwintek.web.backend.api.dto.common.nav;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NavTreeDto {

    private Long id;

    private String name;

    private Long parentId;

    private String screenName;

    private String route;

    private int displayOrder;

    private int treeLevel;

    private String blankScreen;

    private List<NavTreeDto> subMenus;

}
