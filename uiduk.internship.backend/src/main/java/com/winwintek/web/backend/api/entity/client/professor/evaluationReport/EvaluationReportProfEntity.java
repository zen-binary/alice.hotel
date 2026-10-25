package com.winwintek.web.backend.api.entity.client.professor.evaluationReport;

import com.winwintek.web.backend.api.entity.common.AbstractAuditingEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

@Entity
@Data
@DynamicUpdate
@Table(name = "INTERN_PROCESS_EVALUATION")
public class EvaluationReportProfEntity extends AbstractAuditingEntity<String> implements Serializable {

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
