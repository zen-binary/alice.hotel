package com.winwintek.web.backend.api.dto.client.student.internStudent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InternStudentRegResDTO {
    private String internStdSeq;
    private String stdNo;
    private String internPlanSeq;
    private String approveStatus;
    private String delYn;
}
