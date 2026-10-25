package com.winwintek.web.backend.api.entity.client.student.internshipRegistration;

import com.winwintek.web.backend.api.dto.client.student.internPlanDetail.InternPlanDetailResDTO;

import java.sql.Clob;
import java.util.List;

public interface DetailInternPlanRegFormOutput {

    String getInternPlanSeq();

    String getInternPlanName();

    String getReferInternPlanName();

    String getExecutionCount();

    String getCormName();

    String getCommName();

    String getTelNo();

    String getRegisterStr();

    String getRegisterEnd();

    String getInternshipStr();

    String getInternshipEnd();

    String getQuantity();

    String getInternshipField();

    String getStaffName();

    String getInternYear();

    String getInternTerm();

    String getDuration();

    String getDurationHourStr();

    String getDurationMinuteStr();

    String getDurationHourEnd();

    String getDurationMinuteEnd();

    String getInternshipWeekday();

    String getInternshipDay();

    String getPaymentAmount();

    String getPaymentFrequency();

    String getPaymentTimingType();

    String getPaymentDate();

    String getEmploymentContractYn();

    String getInsuranceParticipationYn();

    Clob getInternJobSummary();

    Clob getRequirements();

    String getIsDisabled();

    List<InternPlanDetailResDTO> getInternPlanDetailResDTO();

}
