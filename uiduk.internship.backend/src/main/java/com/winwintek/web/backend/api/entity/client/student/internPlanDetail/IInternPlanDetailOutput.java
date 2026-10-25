package com.winwintek.web.backend.api.entity.client.student.internPlanDetail;

import java.sql.Clob;

public interface IInternPlanDetailOutput {
    String getWeek();
    String getStartDate();

    String getEndDate();
    Clob getContent();
}
