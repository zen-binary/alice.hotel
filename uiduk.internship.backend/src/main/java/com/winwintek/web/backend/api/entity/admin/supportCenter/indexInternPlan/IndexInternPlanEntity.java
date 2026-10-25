package com.winwintek.web.backend.api.entity.admin.supportCenter.indexInternPlan;

import com.winwintek.web.backend.api.entity.common.AbstractAuditingEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

@DynamicUpdate
@Entity
@Table (name = "INTERN_PLAN")
@Data
public class IndexInternPlanEntity extends AbstractAuditingEntity<String> implements Serializable {

    @Id
    private String internPlanSeq;

    private String internPlanNm;

    private String referInternPlanSeq;

    private String executionCount;

    private String cormSeq;

    private String commSeq;

    private String status;

    private String confirmDate;

    private String registerStr;

    private String registerEnd;

    private String internshipStr;

    private String internshipEnd;

    private String internRecruitmentQuantity;

    private String internshipField;

    private String staffNo;

    private String internYear;

    private String internTerm;

    private String duration;

    private String durationHourStr;

    private String durationMinuteStr;

    private String durationHourEnd;

    private String durationMinuteEnd;

    private String internshipWeekday;

    private String internshipDay;

    private String paymentAmount;

    private String paymentFrequency;

    private String paymentTimingType;

    private String paymentDate;

    private String employmentContractYn;

    private String insuranceParticipationYn;

    private String internJobSummary;

    private String requirements;

    private String delYn;

}
