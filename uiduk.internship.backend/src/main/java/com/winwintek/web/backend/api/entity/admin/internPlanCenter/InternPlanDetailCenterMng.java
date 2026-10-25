package com.winwintek.web.backend.api.entity.admin.internPlanCenter;

import com.winwintek.web.backend.api.entity.common.AbstractAuditingEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table (name = "INTERN_PLAN_DETAIL")
public class InternPlanDetailCenterMng extends AbstractAuditingEntity<Long> implements Serializable {

    @Id
    private String internDetailSeq;

    private String internPlanSeq;

    private String week;

    private Date startDate;

    private Date endDate;

    @Lob
    private String content;

    private String delYn;
}
