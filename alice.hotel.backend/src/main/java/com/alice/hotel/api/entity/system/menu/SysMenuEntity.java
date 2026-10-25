package com.alice.hotel.api.entity.system.menu;

import com.alice.hotel.api.entity.common.AbstractAuditingEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

@DynamicUpdate
@Entity
@Table(name="MENU")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SysMenuEntity extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String siteType;
    private String name;
    private String route;
    private Long parentId;
    private Integer displayOrder;
    private String delYn;
    private String vnName;
    private String screenName;
}
