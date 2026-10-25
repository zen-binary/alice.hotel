package com.winwintek.web.backend.api.domain.client.student.dashboard.qna.service;

import com.winwintek.web.backend.api.dto.client.student.dashboard.qna.DashboardQNAResDTO;

import java.util.List;

public interface IDashboardQNAService {
    List<DashboardQNAResDTO> getDashboardQNA();
}
