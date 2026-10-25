package com.winwintek.web.backend.api.entity.admin.corporate.signed;

public interface ISignedResponse {

    String getInternTerm();

    String getInternYear();

    String getCormNameKr();

    String getInternshipStr();

    String getInternshipEnd();

    String getDuration();

    String getDurationHourStr();

    String getDurationMinuteStr();

    String getDurationHourEnd();

    String getDurationMinuteEnd();

    String getInternshipWeekDay();

    String getInternshipDay();

    String getPaymentAmount();

    String getPaymentFrequency();

    String getPaymentTimingType();

    String getPaymentDate();

    String getEmploymentContractYn();

    String getInsuranceParticipationYn();

    String getRegDate();

    String getCormSeq();

    String getInterPlanNm();

    String getUrlFile();

    String getCorporateSignedDate();

    String getManagerNm();

}
