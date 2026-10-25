package com.winwintek.web.backend.api.entity.system.sysResourceRole;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="sys_resource_role")
@Data
public class ResourcesRolesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rrId;
    private Long rsId;
    private Long rlId;

}
