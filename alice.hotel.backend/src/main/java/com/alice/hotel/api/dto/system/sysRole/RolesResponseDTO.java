package com.alice.hotel.api.dto.system.sysRole;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RolesResponseDTO {

    private Long rlId;
    private String rlNm;
    private String category;
    private String etc;
    private String rlCd;
    private String rlDesc;

}
