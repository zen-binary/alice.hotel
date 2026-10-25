package com.winwintek.web.backend.api.entity.admin.notice;

import java.sql.Clob;

public interface INoticeDetailCusDTO {

    String getNoticeSeq();

    String getTitle();

    Clob getContent();

    String getStartDateShow();

    String getEndDateShow();

    String getUseYn();

    String getTopFixYn();

}
