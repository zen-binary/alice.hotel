package com.alice.hotel.api.dto.system.menu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenuResponseDTO {

    private String rowNumber;
    private String id;
    private String siteType;
    private String name;
    private String route;
    private String parentId;
    private String displayOrder;
    private String deleteFlag;
    private String vnName;
    private String regId;
    private String regDate;
    private String screenName;
}
