package com.winwintek.web.backend.api.domain.admin.supportCenter.weekReport.service;

import com.winwintek.web.backend.api.dto.admin.supportCenter.weekReport.PlanDetailSupportCenterResDTO;
import com.winwintek.web.backend.api.dto.admin.supportCenter.weekReport.WeekReportSupportCenterReqDTO;
import com.winwintek.web.backend.api.dto.admin.supportCenter.weekReport.WeekReportSupportCenterResDTO;
import com.winwintek.web.backend.api.dto.admin.supportCenter.weekReport.WeekReportSupportCenterSearchDTO;

import java.util.List;

public interface WeekReportSupportCenterService {

    List<PlanDetailSupportCenterResDTO> getListWeekByInternPlan(String internPlanId);

    WeekReportSupportCenterResDTO getDetailWeekReport(WeekReportSupportCenterSearchDTO searchDTO);

    List<String> createWeekReport(List<WeekReportSupportCenterReqDTO> lstDTO);

}
