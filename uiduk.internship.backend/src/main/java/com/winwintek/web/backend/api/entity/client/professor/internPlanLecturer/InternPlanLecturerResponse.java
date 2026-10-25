package com.winwintek.web.backend.api.entity.client.professor.internPlanLecturer;

import java.sql.Clob;
import java.util.Date;

public interface InternPlanLecturerResponse {
    String getInternPlanSeq();

    String getInternPlanNm();

    String getReferInternPlanSeq();

    String getReferInternPlanNm();

    Integer getExecutionCount();

    String getCormNameKr();

    String getMobilePhone();

    String getCommSeq();

    String getCommNm();

    Date getRegisterStr();

    Date getRegisterEnd();

    Date getInternshipStr();

    Date getInternshipEnd();

    Integer getInternRecruitmentQuantity();

    String getInternshipField();

    String getStaffNo();

    String getStaffNm();

    String getDeptNm();

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
