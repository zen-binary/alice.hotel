package com.winwintek.web.backend.api.dto.client.professor.internPlanLecturer;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InternPlanDetailLecturerRepDTO {
    private String internDetailSeq;

    private String week;

    private String startDate;

    private String endDate;

    private String content;

    private String delYn;
}
