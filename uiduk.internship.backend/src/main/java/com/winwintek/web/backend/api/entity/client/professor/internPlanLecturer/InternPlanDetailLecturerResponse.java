package com.winwintek.web.backend.api.entity.client.professor.internPlanLecturer;

import java.sql.Clob;
import java.util.Date;

public interface InternPlanDetailLecturerResponse {
    String getInternDetailSeq();

    String getWeek();

    Date getStartDate();

    Date getEndDate();

    Clob getContent();

    String getDelYn();
}
