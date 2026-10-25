package com.winwintek.web.backend.api.dto.client.student.internPlanDetail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InternPlanDetailResDTO {
    private String week;
    private String startDate;
    private String endDate;
    private String content;
}
