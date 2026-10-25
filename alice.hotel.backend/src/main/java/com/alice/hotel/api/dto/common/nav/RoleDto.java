package com.alice.hotel.api.dto.common.nav;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoleDto {
    private String rlCd;

    private String rlName;

    private Set<MenuDto> menus = new HashSet<>();
}
