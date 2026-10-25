package com.winwintek.web.backend.api.entity.common.navItems;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * A menu.
 */
@Entity
@Table (name = "MENU")
@Getter
@Setter
public class MenuAuthenticateEntity {

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

    private String vnName;

}
