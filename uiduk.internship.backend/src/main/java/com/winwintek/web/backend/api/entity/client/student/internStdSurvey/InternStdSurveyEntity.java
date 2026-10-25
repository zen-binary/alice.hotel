package com.winwintek.web.backend.api.entity.client.student.internStdSurvey;

import com.winwintek.web.backend.api.entity.common.AbstractAuditingEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

@DynamicUpdate
@Entity
@Table (name = "intern_std_survey")
@Getter
@Setter
public class InternStdSurveyEntity extends AbstractAuditingEntity<String> implements Serializable {

    @Id
    private String internStdSurveySeq;

    private String internPlanSeq;

    private String internStdSeq;

    @Lob
    private String internRecommend;

    private String delYn;

}

