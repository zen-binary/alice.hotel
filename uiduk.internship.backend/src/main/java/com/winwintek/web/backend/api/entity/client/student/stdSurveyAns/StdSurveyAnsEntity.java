package com.winwintek.web.backend.api.entity.client.student.stdSurveyAns;

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
@Table (name = "STD_SURVEY_ANS")
@Getter
@Setter
public class StdSurveyAnsEntity extends AbstractAuditingEntity<String> implements Serializable {

    @Id
    private String stdSurveyAnsSeq;

    private String stdSurveyQusSeq;

    private String internStdSurveySeq;

    private Integer score;

    private String delYn;

}

