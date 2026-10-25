package com.winwintek.web.backend.api.entity.client.student.summaryReportStudent;

import com.winwintek.web.backend.api.entity.common.AbstractAuditingEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

@DynamicUpdate
@Entity
@Table (name = "STD_SUMMARY_REPORT")
@Data
public class SummaryReportStudentEntity extends AbstractAuditingEntity<String> implements Serializable {

    @Id
    private String stdSummaryReportSeq;

    private String internPlanSeq;

    private String internStdSeq;

    private String content;

    private String delYn;

}
