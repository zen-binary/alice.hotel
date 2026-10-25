package com.winwintek.web.backend.api.entity.admin.corporate.InternCorporateSurveyEntity;

import com.winwintek.web.backend.api.entity.common.AbstractAuditingEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicUpdate
@Table (name = "INTERN_CORPORATE_SURVEY")
public class InternCorporateSurveyEntity extends AbstractAuditingEntity<String> implements Serializable {

    @Id
    private String internCorporateSurveySeq;

    private String internPlanSeq;

    @Lob
    private String internRecommend;

    private String delYn;

}
