package com.winwintek.web.backend.api.dto.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;

//    private Long userTsid;
    private String userId;
    private String password;
    private String userName;
    private String colgCd;
    private String colgCdNm;
    private String sustCd;
    private String sustCdNm;
    private String mjCd;
    private String mjCdNm;
    private String shyrCd;
    private String shyrCdNm;
    private String shregSt;
    private String shregStNm;
    private String genFg;
    private String genFgNm;
    private String birthDt;
    private String telNo;
    private String email;
    private String phone;
    private String fullAddress;
    private String division;
    private String userDiv;
    private String uuid;
    private String lastLoginDate;
    private String userRoles;
    private String loginDiv;
    private String loginDivCheck;
    private String publicInfo;
    private String policy;
    private boolean sso;
}
