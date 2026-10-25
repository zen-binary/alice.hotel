package com.winwintek.web.backend.api.entity.common.navItems;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * A role.
 */
@Entity
@Table (name = "SYS_ROLE")
@Getter
@Setter
public class RoleAuthenticateEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rlId;
    private String rlNm;
    private String category;
    private String etc;
    private String rlCd;
    private String rlDesc;
    @ManyToMany (fetch = FetchType.LAZY)
    @JoinTable (name = "MENU_ROLE", joinColumns = {@JoinColumn (name = "roleId", referencedColumnName = "rlId")},
            inverseJoinColumns = {@JoinColumn (name = "menuId", referencedColumnName = "id")})
    @OrderBy ("id ASC")
    private Set<MenuAuthenticateEntity> menus = new HashSet<>();
}
