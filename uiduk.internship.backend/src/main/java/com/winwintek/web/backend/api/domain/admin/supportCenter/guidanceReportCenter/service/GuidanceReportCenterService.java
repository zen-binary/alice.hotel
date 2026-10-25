package com.winwintek.web.backend.api.domain.admin.supportCenter.guidanceReportCenter.service;

import com.winwintek.web.backend.api.dto.admin.supportCenter.guidanceReportCenter.GuidanceReportCenterDetailResDTO;
import com.winwintek.web.backend.api.dto.admin.supportCenter.guidanceReportCenter.GuidanceReportCenterDetailSearchDTO;
import com.winwintek.web.backend.api.dto.admin.supportCenter.guidanceReportCenter.GuidanceReportCenterReqDTO;

public interface GuidanceReportCenterService {

    GuidanceReportCenterDetailResDTO getDetailGuidanceReport(GuidanceReportCenterDetailSearchDTO searchDTO);

    String createGuidanceReport(GuidanceReportCenterReqDTO dto);

    String updateGuidanceReport(GuidanceReportCenterReqDTO dto);

}
