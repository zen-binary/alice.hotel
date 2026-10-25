package com.winwintek.web.backend.api.entity.system;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

@DynamicUpdate
@Entity
@Table(name="sys_role")
@Data
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rlId;
    private String rlNm;
    private String category;
    private String etc;
    private String rlCd;
    private String rlDesc;
}
