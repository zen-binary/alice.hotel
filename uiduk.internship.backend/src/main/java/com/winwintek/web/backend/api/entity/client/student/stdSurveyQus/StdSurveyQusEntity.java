package com.winwintek.web.backend.api.entity.client.student.stdSurveyQus;

import com.winwintek.web.backend.api.entity.common.AbstractAuditingEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

@DynamicUpdate
@Entity
@Table (name = "STD_SURVEY_QUS")
@Getter
@Setter
public class StdSurveyQusEntity extends AbstractAuditingEntity<String> implements Serializable {

    @Id
    private String stdSurveyQusSeq;

    private String qusContent;

    private String displayOrder;

}

