package com.alice.hotel.api.dto.system.roles;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RolesResponseDTO {

    private Long rlId;

    private String rlNm;

    private String category;

    private String etc;

    private String rlCd;

    private String rlDesc;

    private String regId;

    private Date regDate;

}
