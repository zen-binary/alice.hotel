package com.winwintek.web.backend.api.entity.admin.internPlanCenter;

import java.util.Date;

public interface InternPlanCenterResponse {

    String getRowNumb();

    String getInternPlanSeq();

    String getStatus();

    String getStatusCd();

    Date getConfirmDate();

    String getCormNameKr();

    String getInternPlanNm();

    Integer getExecutionCount();

    Date getRegisterStr();

    Date getRegisterEnd();

    Date getInternShipStr();

    Date getInternShipEnd();

    Date getRegDate();

    Integer getInternRecruit();

    Integer getInternApplyRecruit();

    String getRejectionReasonDetails();

}
