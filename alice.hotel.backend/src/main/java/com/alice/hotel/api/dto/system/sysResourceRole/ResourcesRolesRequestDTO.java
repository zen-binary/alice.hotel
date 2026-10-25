package com.alice.hotel.api.dto.system.sysResourceRole;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResourcesRolesRequestDTO {

    String roleId;
    List<String> listInsert;
    List<String> listDelete;

}
