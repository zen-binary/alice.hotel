package com.winwintek.web.backend.api.domain.client.student.dashboard.notice.impl;

import com.winwintek.web.backend.api.common.Constants;
import com.winwintek.web.backend.api.domain.client.student.dashboard.notice.service.IDashboardNoticeService;
import com.winwintek.web.backend.api.dto.client.student.dashboard.notice.DashboardNoticeResDTO;
import com.winwintek.web.backend.api.repository.client.student.dashboard.notice.DashboardNoticeRepository;
import com.winwintek.web.backend.api.entity.client.student.dashboard.notice.IDashboardNoticeOutput;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.winwintek.web.backend.api.common.Constants.STATE_Y;

@Service
public class DashboardNoticeServiceImpl implements IDashboardNoticeService {

    @Autowired
    private DashboardNoticeRepository noticeRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public List<DashboardNoticeResDTO> getDashboardNotice() {
        List<IDashboardNoticeOutput> list = noticeRepository.getDashboardNotice(STATE_Y, Constants.STATE_N);
        List<DashboardNoticeResDTO> listResponse = list.stream()
                .map(item -> modelMapper.map(item, DashboardNoticeResDTO.class))
                .collect(Collectors.toList());
        return listResponse;
    }

}
