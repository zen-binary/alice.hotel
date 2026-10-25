package com.winwintek.web.backend.api.entity.admin.category;

import com.winwintek.web.backend.api.entity.common.AbstractAuditingEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

@DynamicUpdate
@Entity
@Table(name="CATEGORY")
@Data
public class CategoryEntity extends AbstractAuditingEntity<String> implements Serializable {
    @Id
    private String id;
    private String name;
    private String content;
    private Integer deleteFlag;
}
