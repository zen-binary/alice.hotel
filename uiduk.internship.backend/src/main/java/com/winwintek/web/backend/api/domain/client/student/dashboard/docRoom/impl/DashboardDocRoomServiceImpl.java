package com.winwintek.web.backend.api.domain.client.student.dashboard.docRoom.impl;

import com.winwintek.web.backend.api.common.Constants;
import com.winwintek.web.backend.api.domain.client.student.dashboard.docRoom.service.IDashboardDocRoomService;
import com.winwintek.web.backend.api.dto.client.student.dashboard.docRoom.DashboardDocRoomResDTO;
import com.winwintek.web.backend.api.entity.client.student.dashboard.docRoom.IDashboardDocRoomOutput;
import com.winwintek.web.backend.api.repository.client.student.dashboard.docRoom.DashboardDocRoomRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.winwintek.web.backend.api.common.Constants.STATE_N;

@Repository
public class DashboardDocRoomServiceImpl implements IDashboardDocRoomService {

    @Autowired
    private DashboardDocRoomRepository docRoomRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<DashboardDocRoomResDTO> getDashboardDocRoom() {
        List<IDashboardDocRoomOutput> list = docRoomRepository.getDashboardDocRoom(STATE_N, Constants.STATE_Y);
        List<DashboardDocRoomResDTO> listResponse = list.stream()
                .map(item -> modelMapper.map(item, DashboardDocRoomResDTO.class))
                .collect(Collectors.toList());
        return listResponse;
    }

}
