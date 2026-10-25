package com.alice.hotel.api.entity.system.roles;

import com.alice.hotel.api.entity.common.AbstractAuditingEntity;
import com.alice.hotel.api.entity.system.menuMng.MenuEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@DynamicUpdate
@Entity
@Table(name="SYS_ROLE")
@Data
public class RolesEntity extends AbstractAuditingEntity<String> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rlId;
    private String rlNm;
    private String category;
    private String etc;
    private String rlCd;
    private String rlDesc;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "menu_role", joinColumns = @JoinColumn(name = "roleId", referencedColumnName = "rlId"),
            inverseJoinColumns = @JoinColumn(name = "menuId", referencedColumnName = "id"))
    private Set<MenuEntity> menus = new HashSet<>();
}
