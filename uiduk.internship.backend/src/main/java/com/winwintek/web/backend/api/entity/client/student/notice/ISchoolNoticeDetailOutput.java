package com.winwintek.web.backend.api.entity.client.student.notice;

public interface ISchoolNoticeDetailOutput {
    String getTitle();
    String getRegDate();
    java.sql.Clob getContent();
}
