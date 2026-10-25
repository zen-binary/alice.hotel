package com.winwintek.web.backend.api.entity.admin.sudentEvaluation;

import com.winwintek.web.backend.api.entity.common.AbstractAuditingEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@Table (name = "STUDENT_EVALUATION")
public class StudentEvaluationEntity extends AbstractAuditingEntity<Long> implements Serializable {

    @Id
    /** PK */
//    @Column (name = "STUDENT_EVALUATION_SEQ")
    private String studentEvaluationSeq;

    /**
     * EVALUATION_STD 테이블과 연동 키
     */
//    @Column (name = "EVALUATION_STD_SEQ")
    private String evaluationStdSeq;

    /**
     * EVALUATION_CRITERIA 테이블과 연동 키
     */
//    @Column (name = "EVALUATION_CRITERIA_SEQ")
    private String evaluationCriteriaSeq;

    /**
     * 평가 점수
     */
//    @Column (name = "SCORE")
    private Integer score;

    /**
     * 삭제여부
     */
//    @Column (name = "DEL_YN")
    private String delYn;

}
