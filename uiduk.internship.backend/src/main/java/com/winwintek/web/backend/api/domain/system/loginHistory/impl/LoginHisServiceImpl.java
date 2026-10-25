package com.winwintek.web.backend.api.domain.system.loginHistory.impl;

import com.winwintek.web.backend.api.common.Constants;
import com.winwintek.web.backend.api.domain.system.loginHistory.service.LoginHisService;
import com.winwintek.web.backend.api.domain.common.service.CommonService;

import com.winwintek.web.backend.api.dto.system.loginHistory.LoginHistoryPageDTO;
import com.winwintek.web.backend.api.dto.system.loginHistory.LoginHistoryResDTO;
import com.winwintek.web.backend.api.dto.system.loginHistory.LoginRoleDTO;
import com.winwintek.web.backend.api.entity.system.loginHistory.ISysRoleDTO.ILoginHisDTO;

import com.winwintek.web.backend.api.entity.system.loginHistory.ISysRoleDTO.ISysRoleDTO;
import com.winwintek.web.backend.api.repository.system.loginHistory.LoginHisRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LoginHisServiceImpl implements LoginHisService {

    @Autowired
    private CommonService commonService;

    @Autowired
    private LoginHisRepository loginHisRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Page<LoginHistoryResDTO> getAllLoginHis(LoginHistoryPageDTO loginHistoryPageDTO) {
        int page = loginHistoryPageDTO.getPage();
        int size = loginHistoryPageDTO.getSize();
        String sort = loginHistoryPageDTO.getSort();

        Pageable pageable = commonService.buildPageable(page, size, sort);

        Page<ILoginHisDTO> loginHisInfos = loginHisRepository
                .selectLoginHis(pageable, loginHistoryPageDTO, Constants.STATE_N, Constants.STATE_Y);

        Page<LoginHistoryResDTO> resp = loginHisInfos
                .map(loginHis -> modelMapper.map(loginHis, LoginHistoryResDTO.class));

        return resp;
    }

    @Override
    public List<LoginRoleDTO> selectRole() throws Exception {
        List<ISysRoleDTO> listRole = loginHisRepository.selectRole();
        List<LoginRoleDTO> listRoles = listRole.stream().map(role -> modelMapper.map(role, LoginRoleDTO.class))
                .collect(Collectors.toList());
        return listRoles;
    }

}
