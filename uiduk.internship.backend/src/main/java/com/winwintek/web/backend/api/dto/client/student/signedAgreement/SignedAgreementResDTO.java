package com.winwintek.web.backend.api.dto.client.student.signedAgreement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignedAgreementResDTO {

    private String internPlanName;

    private String internYear;

    private String internTerm;

    private String cormSeq;

    private String cormName;

    private String internshipStr;

    private String internshipEnd;

    private String duration;

    private String durationHourStr;

    private String durationMinuteStr;

    private String durationHourEnd;

    private String durationMinuteEnd;

    private String internshipWeekDay;

    private String internshipDay;

    private String paymentAmount;

    private String paymentFrequency;

    private String paymentTimingType;

    private String paymentDate;

    private String employmentContract;

    private String insuranceParticipation;

    private String corporateSignedDate;

    private String urlFile;

    private String tripartiteSeq;

    private String stdSignedDate;

    private String corpSignedYn;

    private String stdSignedYn;

    private String univSignedYn;

    private String managerNm;

}
