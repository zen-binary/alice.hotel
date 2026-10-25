package com.winwintek.web.backend.api.domain.client.internMng.impl;

import com.winwintek.web.backend.api.common.Constants;
import com.winwintek.web.backend.api.domain.client.internMng.service.IInternMngService;
import com.winwintek.web.backend.api.domain.common.service.CommonService;
import com.winwintek.web.backend.api.dto.client.internMng.InternMngListDTO;
import com.winwintek.web.backend.api.dto.client.internMng.InternMngResDTO;
import com.winwintek.web.backend.api.dto.client.internMng.InternMngSearchDTO;
import com.winwintek.web.backend.api.dto.system.UserDTO;
import com.winwintek.web.backend.api.entity.client.internMng.IInternMngResponeListDTO;
import com.winwintek.web.backend.api.repository.client.internMng.IInternMngRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InternMngServiceImpl implements IInternMngService {

    @Autowired
    private IInternMngRepository internMngRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CommonService commonService;

    @Override
    public InternMngResDTO getInternList(InternMngSearchDTO internMngSearchDTO) {

        UserDTO userDTO = commonService.getFullUserInfo();

        List<IInternMngResponeListDTO> responeListDTO = internMngRepository.selectInternlist(internMngSearchDTO, Constants.STATE_N, userDTO.getUserId());
        List<InternMngListDTO> internResListDTO = responeListDTO.stream()
                .map(item -> modelMapper.map(item, InternMngListDTO.class))
                .toList();

        List<String> yearList =internMngRepository.selectYearPlan(Constants.STATE_N);
        InternMngResDTO internMngResDTO = new InternMngResDTO();

        internMngResDTO.setInternMngList(internResListDTO);
        internMngResDTO.setYearList(yearList);

        return internMngResDTO;
    }
}
