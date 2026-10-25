package com.winwintek.web.backend.api.dto.client.professor.internPlanLecturer;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class InternPlanLecturerRepDTO {

    private String internPlanSeq;

    private String internPlanNm;

    private String referInternPlanSeq;

    private String referInternPlanNm;

    private Integer executionCount;

    private String cormNameKr;

    private String mobilePhone;

    private String cormSeq;

    private String commSeq;

    private String commNm;

    private String registerStr;

    private String registerEnd;

    private String internshipStr;

    private String internshipEnd;

    private Integer internRecruitmentQuantity;

    private String internshipField;

    private String staffNo;

    private String staffNm;

    private String deptNm;

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

    private String status;

    private List<InternPlanDetailLecturerRepDTO> listInternPlanDetail;

}
