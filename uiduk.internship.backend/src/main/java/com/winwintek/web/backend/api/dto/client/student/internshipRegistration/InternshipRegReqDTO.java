package com.winwintek.web.backend.api.dto.client.student.internshipRegistration;

import com.winwintek.web.backend.api.dto.common.pageable.PageableDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InternshipRegReqDTO {
    private String selectedYear;
    private String receptionStatus;
    private String internshipName;
}
