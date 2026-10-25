package com.alice.hotel.api.dto.system.roles;

import com.alice.hotel.api.dto.system.menuMng.MenuDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoleResponseDetailDTO {
    private Long rlId;
    private String rlNm;
    private String category;
    private String etc;
    private String rlCd;
    private String rlDesc;
    private String regId;
    private Date regDate;
    private List<MenuDTO> menus;
}
