package com.winwintek.web.backend.api.entity.admin.supportCenter.corpSurvey;

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
@Table(name = "INTERN_CORPORATE_SURVEY")
public class CorpSurveySupportCenterEntity extends AbstractAuditingEntity<String> implements Serializable {

    @Id
    private String internCorporateSurveySeq;

    private String internPlanSeq;

    @Lob
    private String internRecommend;

    private String delYn;

}
