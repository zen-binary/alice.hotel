package com.winwintek.web.backend.api.entity.admin.supportCenter.evaluationTableCenter;

import com.winwintek.web.backend.api.entity.common.AbstractAuditingEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@Table (name = "EVALUATION_STD")
@DynamicUpdate
public class EvaluationStdCenterEntity extends AbstractAuditingEntity<Long> implements Serializable {

    @Id
    private String evaluationStdSeq;

    private String internStdSeq;

    @Lob
    private String evaluationFeedback;

    @Lob
    private String studentFeedback;

    private Date evaluationSubmitDate;

    private String delYn;

}
