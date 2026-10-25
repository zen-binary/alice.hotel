package com.winwintek.web.backend.api.entity.admin.supportCenter.evaluationReportCenter;

import com.winwintek.web.backend.api.entity.common.AbstractAuditingEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@DynamicUpdate
@Table (name = "INTERN_PROCESS_EVALUATION")
public class EvaluationReportCenterEntity extends AbstractAuditingEntity<String> implements Serializable {

    @Id
    private String internProcessEvaluationSeq;

    private String internPlanSeq;

    private String internStdSeq;

    private String workingDepartment;

    private Integer attendanceStatus;

    private Integer weekReport;

    private Integer summaryReport;

    private Integer guidanceReport;

    private String delYn;
}
