package com.alice.hotel.api.dto.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRoleDTO {
    private Long urId;
    private Long userTsid;
    private Long rlId;
}
