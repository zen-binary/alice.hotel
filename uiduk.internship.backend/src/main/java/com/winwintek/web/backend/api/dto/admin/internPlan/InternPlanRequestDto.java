package com.winwintek.web.backend.api.dto.admin.internPlan;

import com.winwintek.web.backend.api.dto.admin.internPlanDetail.InternPlanDetailListDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class InternPlanRequestDto {

    List<InternPlanDetailListDto> listInternPlanDetail;

    private String internPlanSeq;

    private String internPlanNm;

    private String cormNameKr;

    private String mobilePhone;

    private String referInternPlanSeq;

    private Integer executionCount;

    private String cormSeq;

    private String commSeq;

    private String registerStr;

    private String registerEnd;

    private String internshipStr;

    private String internshipEnd;

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

    private String status;

}
