package com.winwintek.web.backend.api.entity.admin.internPlanDetail;

import java.sql.Clob;
import java.util.Date;

public interface InternPlanListResponse {

    String getInternDetailSeq();

    String getWeek();

    Date getStartDate();

    Date getEndDate();

    Clob getContent();

    String getDelYn();

}
