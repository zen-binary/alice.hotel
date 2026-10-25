package com.alice.hotel.api.domain.system.menuHistory.impl;

import com.alice.hotel.api.common.GlobalEnum;
import com.alice.hotel.api.config.security.util.SequenceGenerator;
import com.alice.hotel.api.config.security.util.WebUtil;
import com.alice.hotel.api.domain.common.service.CommonService;
import com.alice.hotel.api.domain.system.menuHistory.service.MenuHistoryService;
import com.alice.hotel.api.dto.system.UserDTO;
import com.alice.hotel.api.dto.system.menuHistory.MenuHisRequestDTO;
import com.alice.hotel.api.dto.system.menuHistory.MenuHisResDTO;
import com.alice.hotel.api.dto.system.menuHistory.MenuHisSearchDTO;
import com.alice.hotel.api.entity.system.menuHistory.ISysMenuHistoryResponse;
import com.alice.hotel.api.entity.system.menuHistory.SysMenuHistoryEntity;
import com.alice.hotel.api.repository.system.menuHistory.IMenuHistoryRepository;
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

import static com.alice.hotel.api.common.Constants.NO_LOGIN_USER;
import static com.alice.hotel.api.common.GlobalEnum.PREFIX_CPS_MENU_CONN_HIS;
import static com.alice.hotel.api.common.GlobalEnum.TABLE_CPS_MENU_CONN_HIS;

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
