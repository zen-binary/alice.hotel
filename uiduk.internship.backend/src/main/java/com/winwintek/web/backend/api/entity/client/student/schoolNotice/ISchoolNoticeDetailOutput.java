package com.winwintek.web.backend.api.entity.client.student.schoolNotice;

public interface ISchoolNoticeDetailOutput {
    String getTitle();
    String getRegDate();
    java.sql.Clob getContent();
}
