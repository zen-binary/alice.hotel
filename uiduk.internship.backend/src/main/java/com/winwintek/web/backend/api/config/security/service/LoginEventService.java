package com.winwintek.web.backend.api.config.security.service;

import com.winwintek.web.backend.api.config.security.dto.AccountContext;
import com.winwintek.web.backend.api.config.security.util.SecurityUtils;
import com.winwintek.web.backend.api.config.security.util.SequenceGenerator;
import com.winwintek.web.backend.api.config.security.util.WebUtil;
import com.winwintek.web.backend.api.dto.system.UserDTO;
import com.winwintek.web.backend.api.entity.system.RoleEntity;
import com.winwintek.web.backend.api.entity.system.SysLoginEventEntity;
import com.winwintek.web.backend.api.repository.system.RoleRepository;
import com.winwintek.web.backend.api.repository.system.SysLoginEventRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.winwintek.web.backend.api.common.GlobalEnum.*;

@RequiredArgsConstructor
@Service
public class LoginEventService {

    @Autowired
    private SysLoginEventRepository sysLoginEventRepository;

    @Autowired
    private SequenceGenerator sequenceGenerator;

    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public void logSuccess(HttpServletRequest request, UserDTO userDTO) {

        SysLoginEventEntity loginEvent = new SysLoginEventEntity();

        loginEvent.setSlId(sequenceGenerator.getNextSequence(PREFIX_SYS_LOGIN_HISTORY.toString(), TABLE_SYS_LOGIN_HISTORY.toString()));

        loginEvent.setUserId(userDTO.getUserId());
        loginEvent.setUserNm(userDTO.getUserName());
        loginEvent.setUserDiv(userDTO.getUserDiv());
        loginEvent.setLoginYn(STATE_Y.toString());
        loginEvent.setLoginMsg(LOGIN_EVENT_SUCCESS_MSG.toString());
        loginEvent.setLoginIp(WebUtil.getClientIpAddress(request));
        loginEvent.setConnOs(WebUtil.getUserAgentOs(request));
        loginEvent.setLoginType(WebUtil.getUserAgentDevice(request));
        loginEvent.setAccessType(WebUtil.isInternalIP(WebUtil.getClientIpAddress(request)));

        loginEvent.setBrowserInfo(WebUtil.getBrowserInfo(request));

        RoleEntity role = roleRepository.findByRlId(Long.valueOf(userDTO.getUserRoles()));
        if (role != null) {
            loginEvent.setRoleInfo(role.getRlNm());
        }

        sysLoginEventRepository.save(loginEvent);
    }

    public void logFailure(HttpServletRequest request, String userId) {
        SysLoginEventEntity loginEvent = new SysLoginEventEntity();
        loginEvent.setSlId(sequenceGenerator.getNextSequence(PREFIX_SYS_LOGIN_HISTORY.toString(), TABLE_SYS_LOGIN_HISTORY.toString()));
        loginEvent.setUserId(userId);
        loginEvent.setUserNm("");
        loginEvent.setUserDiv("");
        loginEvent.setLoginYn(STATE_N.toString());
        loginEvent.setLoginMsg(LOGIN_EVENT_FAIL_MSG + userId);
        loginEvent.setLoginIp(WebUtil.getClientIpAddress(request));
        loginEvent.setConnOs(WebUtil.getUserAgentOs(request));
        loginEvent.setLoginType(WebUtil.getUserAgentDevice(request));
        loginEvent.setAccessType(WebUtil.isInternalIP(WebUtil.getClientIpAddress(request)));
        sysLoginEventRepository.save(loginEvent);
    }
}
