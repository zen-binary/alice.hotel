package com.winwintek.web.backend.api.entity.admin.internPlan;

import java.sql.Clob;
import java.util.Date;

public interface InternPlanDetailResponse {

    String getInternPlanSeq();

    String getInternPlanNm();

    String getReferInternPlanSeq();

    Integer getExecutionCount();

    String getCormNameKr();

    String getMobilePhone();

    String getCommSeq();

    Date getRegisterStr();

    Date getRegisterEnd();

    Date getInternshipStr();

    Date getInternshipEnd();

    Integer getInternRecruitmentQuantity();

    String getInternshipField();

    String getStaffNo();

    String getInternYear();

    String getInternTerm();

    String getDuration();

    String getDurationHourStr();

    String getDurationHourEnd();

    String getDurationMinuteStr();

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

    String getStatus();

}
