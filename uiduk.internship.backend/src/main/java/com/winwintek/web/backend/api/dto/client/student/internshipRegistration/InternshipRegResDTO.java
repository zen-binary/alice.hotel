package com.winwintek.web.backend.api.dto.client.student.internshipRegistration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InternshipRegResDTO {
    private String internPlanSeq;
    private String internshipName;
    private String corpName;
    private String quantity;
    private String internshipField;
    private String address;
    private String startDate;
    private String endDate;
    private String receptionStatus;
    private String cormSeq;
    private String urlFile;
}
