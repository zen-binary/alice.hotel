package com.winwintek.web.backend.api.entity.admin.evaluationStd;

import com.winwintek.web.backend.api.entity.common.AbstractAuditingEntity;
import jakarta.persistence.Column;
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
public class EvaluationStdEntity extends AbstractAuditingEntity<Long> implements Serializable {

    @Id
    /** PK */
//    @Column (name = "EVALUATION_STD_SEQ")
    private String evaluationStdSeq;

    /**
     * INTERN_STD 테이블과 연동 키
     */
//    @Column (name = "INTERN_STD_SEQ")
    private String internStdSeq;

    /**
     * 평가 의견
     */
//    @Column (name = "EVALUATION_FEEDBACK")
    @Lob
    private String evaluationFeedback;

    /**
     * 학생에 대한 피드백
     */
//    @Column (name = "STUDENT_FEEDBACK")
    @Lob
    private String studentFeedback;

    /**
     * 최종 저장일
     */
//    @Column (name = "EVALUATION_SUBMIT_DATE")
    private Date evaluationSubmitDate;

    /**
     * 삭제여부
     */
//    @Column (name = "DEL_YN")
    private String delYn;

}
