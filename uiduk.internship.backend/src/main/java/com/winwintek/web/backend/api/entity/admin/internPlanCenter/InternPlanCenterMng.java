package com.winwintek.web.backend.api.entity.admin.internPlanCenter;

import com.winwintek.web.backend.api.entity.common.AbstractAuditingEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@Table (name = "INTERN_PLAN")
public class InternPlanCenterMng extends AbstractAuditingEntity<String> implements Serializable {

    @Id
    private String internPlanSeq;

    private String internPlanNm;

    private String referInternPlanSeq;

    private Integer executionCount;

    private String cormSeq;

    private String commSeq;

    private String status;

    private Date confirmDate;

    private Date registerStr;

    private Date registerEnd;

    private Date internshipStr;

    private Date internshipEnd;

    private Integer internRecruitmentQuantity;

    private String internshipField;

    private String staffNo;

    private String internYear;

    private String internTerm;

    private String duration;

    private String durationHourStr;

    private String durationHourEnd;

    private String durationMinuteStr;

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

    private String rejectionReasonDetails;

    private String delYn;

}
