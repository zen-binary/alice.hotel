package com.winwintek.web.backend.api.domain.system.menuHistory.impl;

import com.winwintek.web.backend.api.common.GlobalEnum;
import com.winwintek.web.backend.api.config.security.util.SequenceGenerator;
import com.winwintek.web.backend.api.config.security.util.WebUtil;
import com.winwintek.web.backend.api.domain.common.service.CommonService;
import com.winwintek.web.backend.api.domain.system.menuHistory.service.MenuHistoryService;
import com.winwintek.web.backend.api.dto.system.UserDTO;
import com.winwintek.web.backend.api.dto.system.menuHistory.MenuHisRequestDTO;
import com.winwintek.web.backend.api.dto.system.menuHistory.MenuHisResDTO;
import com.winwintek.web.backend.api.dto.system.menuHistory.MenuHisSearchDTO;
import com.winwintek.web.backend.api.entity.system.menuHistory.ISysMenuHistoryResponse;
import com.winwintek.web.backend.api.entity.system.menuHistory.SysMenuHistoryEntity;
import com.winwintek.web.backend.api.repository.system.menuHistory.IMenuHistoryRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static com.winwintek.web.backend.api.common.Constants.NO_LOGIN_USER;
import static com.winwintek.web.backend.api.common.GlobalEnum.PREFIX_CPS_MENU_CONN_HIS;
import static com.winwintek.web.backend.api.common.GlobalEnum.TABLE_CPS_MENU_CONN_HIS;

@Service
@Transactional
public class MenuHistoryServiceImpl implements MenuHistoryService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SequenceGenerator sequenceGenerator;

    @Autowired
    private CommonService commonService;

    @Autowired
    private IMenuHistoryRepository iMenuHistoryRepository;

    @Override
    public String createLogMenuHistory(MenuHisRequestDTO menuHis) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        SysMenuHistoryEntity menuHisEntity = new SysMenuHistoryEntity();
        Authentication authentication = SecurityContextHolder.getContextHolderStrategy().getContext()
                .getAuthentication();

        menuHisEntity.setMenuConnHisId(sequenceGenerator.getNextSequence(PREFIX_CPS_MENU_CONN_HIS.toString(),
                TABLE_CPS_MENU_CONN_HIS.toString()));
        menuHisEntity.setConnUrl(menuHis.getRoute());
        if (authentication.getName().equals(GlobalEnum.AnomyousUser_String.toString())) {
            menuHisEntity.setConnId(NO_LOGIN_USER);
        } else {
            UserDTO userDTO = commonService.getFullUserInfo();
            menuHisEntity.setConnId(userDTO.getUserId());
        }
        menuHisEntity.setConnIp(WebUtil.getClientIpAddress(request));
        iMenuHistoryRepository.save(menuHisEntity);

        return menuHisEntity.getMenuConnHisId();
    }

    public Page<MenuHisResDTO> logMenuHistoryList(MenuHisSearchDTO menuHis) {
        Pageable pageable = commonService.buildPageable(menuHis.getPage(), menuHis.getSize(), menuHis.getSort());

        Page<ISysMenuHistoryResponse> lstData = iMenuHistoryRepository.selectMenuHistory(menuHis, pageable);
        Page<MenuHisResDTO> lstResults = lstData.map(item -> modelMapper.map(item, MenuHisResDTO.class));

        return lstResults;
    }

}
