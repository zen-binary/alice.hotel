package com.winwintek.web.backend.api.entity.admin.internPlanCenter;

import java.sql.Clob;
import java.util.Date;

public interface InternPlanDetailListCenterResponse {
    String getInternDetailSeq();

    String getWeek();

    Date getStartDate();

    Date getEndDate();

    Clob getContent();

    String getDelYn();
}
