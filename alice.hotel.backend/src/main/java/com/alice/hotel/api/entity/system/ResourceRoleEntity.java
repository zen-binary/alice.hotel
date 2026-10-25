package com.alice.hotel.api.entity.system;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

@DynamicUpdate
@Entity
@Table(name="sys_resource_role")
@Data
public class ResourceRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rrId;

    @ManyToOne
    @JoinColumn(name = "rsId")
    private ResourceEntity resource;

    @ManyToOne
    @JoinColumn(name = "rlId")
    private RoleEntity role;
}
