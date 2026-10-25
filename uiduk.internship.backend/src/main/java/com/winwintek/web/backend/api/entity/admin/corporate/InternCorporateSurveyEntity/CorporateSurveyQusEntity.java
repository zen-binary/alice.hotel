package com.winwintek.web.backend.api.entity.admin.corporate.InternCorporateSurveyEntity;

import com.winwintek.web.backend.api.entity.common.AbstractAuditingEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Table (name = "CORPORATE_SURVEY_QUS")
public class CorporateSurveyQusEntity extends AbstractAuditingEntity<String> implements Serializable {

    @Id
    private String corporateSurveyQusSeq;

    private String qusContent;

    private Integer displayOrder;

    private String delYn;

}
