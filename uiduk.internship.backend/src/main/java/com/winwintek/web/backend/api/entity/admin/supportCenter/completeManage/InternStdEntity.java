package com.winwintek.web.backend.api.entity.admin.supportCenter.completeManage;

import com.winwintek.web.backend.api.entity.common.AbstractAuditingEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

@DynamicUpdate
@Entity
@Table (name = "INTERN_STD")
@Data
public class InternStdEntity extends AbstractAuditingEntity<String> implements Serializable {

    @Id
    private String internStdSeq;

    private String stdNo;

    private String internPlanSeq;

    private String approveStatus;

    private String approveDate;

    private String internshipStatus;

    private String delYn;


}
