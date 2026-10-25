package com.winwintek.web.backend.api.entity.client.student.internPlanDetail;

import com.winwintek.web.backend.api.entity.common.AbstractAuditingEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;

@Entity
@Table (name="INTERN_PLAN_DETAIL")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InternPlanDetailStdEntity extends AbstractAuditingEntity<String> implements Serializable {

    @Id
    private String internDetailSeq;
    private String internPlanSeq;
    private String week;
    private String startDate;
    private String endDate;
    private String content;
    private String delYn;
}
