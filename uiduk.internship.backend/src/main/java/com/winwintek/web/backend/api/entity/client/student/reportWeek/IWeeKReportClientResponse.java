package com.winwintek.web.backend.api.entity.client.student.reportWeek;

public interface IWeeKReportClientResponse {
    String getStdWeekReportId();

    String getDateWeek();

    String getInHour();

    String getInMinute();

    String getOutHour();

    String getOutMinute();

    String getJobContent();

    String getAcquiredKnowledge();

    String getAttendanceDate();

    String getIsDisabled();
}
