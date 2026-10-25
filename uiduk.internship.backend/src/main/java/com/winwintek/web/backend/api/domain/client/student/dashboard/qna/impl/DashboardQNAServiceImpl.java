package com.winwintek.web.backend.api.domain.client.student.dashboard.qna.impl;

import com.winwintek.web.backend.api.domain.client.student.dashboard.qna.service.IDashboardQNAService;
import com.winwintek.web.backend.api.domain.common.service.CommonService;
import com.winwintek.web.backend.api.dto.client.student.dashboard.qna.DashboardQNAResDTO;
import com.winwintek.web.backend.api.entity.client.student.dashboard.qna.IDashboardQNAOutput;
import com.winwintek.web.backend.api.repository.client.student.dashboard.qna.DashboardQNARepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.winwintek.web.backend.api.common.Constants.STATE_N;

@Service
public class DashboardQNAServiceImpl implements IDashboardQNAService {

    @Autowired
    private DashboardQNARepository qnaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CommonService commonService;

    @Override
    public List<DashboardQNAResDTO> getDashboardQNA() {
        String stdId = commonService.getFullUserInfo().getUserId();
        List<IDashboardQNAOutput> list = qnaRepository.getDashboardQNA(stdId, STATE_N);
        List<DashboardQNAResDTO> listResponse = list.stream()
                .map(item -> modelMapper.map(item, DashboardQNAResDTO.class))
                .collect(Collectors.toList());
        return listResponse;
    }

}
