package com.winwintek.web.backend.api.entity.admin.supportCenter.guidanceReportCenter;

import com.winwintek.web.backend.api.entity.common.AbstractAuditingEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.util.Date;
@Entity
@DynamicUpdate
@Table (name = "GUIDANCE_REPORT")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GuidanceReportCenterEntity extends AbstractAuditingEntity<String> implements Serializable {
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
