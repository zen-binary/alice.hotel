package com.winwintek.web.backend.api.domain.client.student.dashboard.notice.service;

import com.winwintek.web.backend.api.dto.client.student.dashboard.notice.DashboardNoticeResDTO;

import java.util.List;

public interface IDashboardNoticeService {
    List<DashboardNoticeResDTO> getDashboardNotice();
}
