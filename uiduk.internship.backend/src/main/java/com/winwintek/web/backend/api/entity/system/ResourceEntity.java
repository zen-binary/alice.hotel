package com.winwintek.web.backend.api.entity.system;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;
@DynamicUpdate
@Entity
@Table(name="sys_resource")
@Data
public class ResourceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rsId;
    private String url;
    private String httpMethod;
    private String rsNm;
    private String rsType;
    private Date regDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "sys_resource_role", joinColumns = { @JoinColumn(name = "rsId") },
            inverseJoinColumns = { @JoinColumn(name = "rlId") })
    @ToString.Exclude
    private Set<RoleEntity> roleSet = new HashSet<>();
}
