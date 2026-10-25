package com.winwintek.web.backend.api.dto.client.student.internshipRegistration;

import com.winwintek.web.backend.api.dto.client.student.internPlanDetail.InternPlanDetailResDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Clob;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetailInternPlanRegFormResDTO {
    private String internPlanSeq;
    private String internPlanName;
    private String referInternPlanName;
    private String executionCount;
    private String cormName;
    private String commName;
    private String telNo;
    private String registerStr;
    private String registerEnd;
    private String internshipStr;
    private String internshipEnd;
    private String quantity;
    private String internshipField;
    private String staffName;
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
    private String isDisabled;
    private List<InternPlanDetailResDTO> internPlanDetailResDTO;

}
