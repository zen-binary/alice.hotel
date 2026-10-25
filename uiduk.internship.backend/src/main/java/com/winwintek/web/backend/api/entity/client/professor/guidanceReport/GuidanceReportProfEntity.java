package com.winwintek.web.backend.api.entity.client.professor.guidanceReport;

import com.winwintek.web.backend.api.entity.common.AbstractAuditingEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@DynamicUpdate
@Table (name = "GUIDANCE_REPORT")
public class GuidanceReportProfEntity extends AbstractAuditingEntity<String> implements Serializable {

    @Id
    private String guidanceReportSeq;

    private String internPlanSeq;

    private String internStdSeq;

    private String workingDepartment;

    private String personInCharge;

    @Lob
    private String actualSituation;

    @Lob
    private String guidanceContent;

    @Lob
    private String corporateFeedback;

    @Lob
    private String resultsAndFeedback;

    private Date internDate;

    private String delYn;

}
