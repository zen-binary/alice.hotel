package com.alice.hotel.api.entity.system;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import java.util.HashSet;
import java.util.Set;

@DynamicUpdate
@Entity
@Table (name = "MENU")
@Data
public class IMenuEntity {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String siteType;

    private String name;

    private String route;

    private Long parentId;

    private String subUrl;

    private String displayOrder;

    private String deleteFlag;

    @ManyToMany (fetch = FetchType.LAZY)
    @JoinTable (name = "MENU_ROLE", joinColumns = {@JoinColumn (name = "menuId")}, inverseJoinColumns = {@JoinColumn (name = "roleId")})
    @ToString.Exclude
    private Set<RoleEntity> roleSet = new HashSet<>();

}