package com.winwintek.web.backend.api.entity.admin.supportCenter.summaryReport;

import com.winwintek.web.backend.api.entity.common.AbstractAuditingEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

@Data
@Entity
@DynamicUpdate
@Table(name = "STD_SUMMARY_REPORT")
public class SummaryReportSupportCenterEntity extends AbstractAuditingEntity<String> implements Serializable {

    @Id
    private String stdSummaryReportSeq;

    private String internPlanSeq;

    private String internStdSeq;

    @Lob
    private String content;

    private String delYn;

}
