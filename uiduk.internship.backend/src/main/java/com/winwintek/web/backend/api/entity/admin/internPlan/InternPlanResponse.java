package com.winwintek.web.backend.api.entity.admin.internPlan;

import java.util.Date;

public interface InternPlanResponse {

    String getRowNumb();

    String getInternPlanSeq();

    String getStatusCd();

    String getStatus();

    Date getConfirmDate();

    String getCormNameKr();

    String getInternPlanNm();

    Integer getExecutionCount();

    Date getRegisterStr();

    Date getRegisterEnd();

    Date getInternShipStr();

    Date getInternShipEnd();

    Date getRegDate();

    String getRejectionReasonDetails();
}
