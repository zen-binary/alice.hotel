package com.alice.hotel.api.entity.system;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

@DynamicUpdate
@Entity
@Table(name = "SYS_USER_ROLE")
@Data
public class UserRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long urId;

    private String rlId;

    private String userId;

    private String useYn;
}
