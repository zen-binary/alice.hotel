package com.winwintek.web.backend.api.entity.client.student.reportWeek;

public interface IStudentReportWeekResponse {

    String getStdWeekReportId();

    String getContent();

    String getInHour();

    String getInMinute();

    String getOutHour();

    String getOutMinute();

    String getDateWeek();

    String getAcquiredKnowledge();
}
