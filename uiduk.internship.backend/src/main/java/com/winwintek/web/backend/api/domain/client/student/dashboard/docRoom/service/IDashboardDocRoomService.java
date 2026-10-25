package com.winwintek.web.backend.api.domain.client.student.dashboard.docRoom.service;

import com.winwintek.web.backend.api.dto.client.student.dashboard.docRoom.DashboardDocRoomResDTO;

import java.util.List;

public interface IDashboardDocRoomService {
    List<DashboardDocRoomResDTO> getDashboardDocRoom();
}
