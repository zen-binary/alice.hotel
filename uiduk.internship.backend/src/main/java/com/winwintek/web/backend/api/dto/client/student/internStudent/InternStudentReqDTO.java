package com.winwintek.web.backend.api.dto.client.student.internStudent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InternStudentReqDTO {
    private String selectedYear;
    private String internshipStatus;
    private String internshipName;
}
