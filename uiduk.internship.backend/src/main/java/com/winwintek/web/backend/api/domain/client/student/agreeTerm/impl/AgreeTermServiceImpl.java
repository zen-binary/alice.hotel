package com.winwintek.web.backend.api.domain.client.student.agreeTerm.impl;

import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.config.security.handler.CustomException;
import com.winwintek.web.backend.api.domain.client.student.agreeTerm.service.IAgreeTermService;
import com.winwintek.web.backend.api.domain.common.service.CommonService;
import com.winwintek.web.backend.api.dto.client.student.agreeTerm.AgreeTermUserResDTO;
import com.winwintek.web.backend.api.repository.client.student.agreeTerm.AgreeTermRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.winwintek.web.backend.api.common.Constants.STATE_Y;

@Service
public class AgreeTermServiceImpl implements IAgreeTermService {

    @Autowired
    private AgreeTermRepository agreeTermRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CommonService commonService;

    @Override
    @Transactional
    public AgreeTermUserResDTO updateAgreeTermUser() {
        String id = commonService.getFullUserInfo().getUserId();
        var currentUser = agreeTermRepository.findByUserId(id).orElseThrow(() -> new CustomException(ApiStatus.NOT_FOUND));
        currentUser.setPublicInfo(STATE_Y);
        agreeTermRepository.save(currentUser);
        return modelMapper.map(currentUser, AgreeTermUserResDTO.class);
    }

}
