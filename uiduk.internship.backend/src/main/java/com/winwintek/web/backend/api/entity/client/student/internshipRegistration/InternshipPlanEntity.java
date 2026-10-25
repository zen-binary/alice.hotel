package com.winwintek.web.backend.api.entity.client.student.internshipRegistration;

import com.winwintek.web.backend.api.entity.common.AbstractAuditingEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table (name="INTERN_PLAN")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InternshipPlanEntity extends AbstractAuditingEntity<String> implements Serializable {

    @Id
    private String internPlanSeq;

    private String internPlanNm;

    private String referInternPlanSeq;

    private String executionCount;

    private String cormSeq;

    private String commSeq;

    private String status;

    private String confirmDate;

    private String registerDate;

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

    private String requirement;

    private String delYn;

    private String rejectionReasonDetails;

}
