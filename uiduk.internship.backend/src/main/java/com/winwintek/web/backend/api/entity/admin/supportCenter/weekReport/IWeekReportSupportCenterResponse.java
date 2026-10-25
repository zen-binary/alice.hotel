package com.winwintek.web.backend.api.entity.admin.supportCenter.weekReport;

public interface IWeekReportSupportCenterResponse {

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
