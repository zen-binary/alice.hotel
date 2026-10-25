package com.winwintek.web.backend.api.entity.system.sysRole;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="sys_role")
@Data
public class SysRolesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rlId;
    private String rlNm;
    private String category;
    private String etc;
    private String rlCd;
    private String rlDesc;

}
