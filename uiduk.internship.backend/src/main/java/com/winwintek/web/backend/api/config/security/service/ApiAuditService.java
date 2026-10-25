package com.winwintek.web.backend.api.config.security.service;


import com.winwintek.web.backend.api.config.security.dto.AccountContext;
import com.winwintek.web.backend.api.config.security.util.SequenceGenerator;
import com.winwintek.web.backend.api.config.security.util.WebUtil;
import com.winwintek.web.backend.api.dto.system.UserDTO;
import com.winwintek.web.backend.api.entity.system.SysApiAuditEntity;
import com.winwintek.web.backend.api.repository.system.IResourceRepository;
import com.winwintek.web.backend.api.repository.system.SysApiAuditRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;

import static com.winwintek.web.backend.api.common.GlobalEnum.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiAuditService {

    @Autowired
    private SysApiAuditRepository sysApiAuditRepository;

    @Autowired
    private IResourceRepository iResourceRepository;

    @Autowired
    private SequenceGenerator sequenceGenerator;

    @Transactional
    public void logApiUsageUser(HttpServletRequest request, HttpServletResponse response, String httpMethod,
            String params, long executionTime, String reqPayload, String resPayload, String userId) {

        SysApiAuditEntity sysApiAuditEntity = new SysApiAuditEntity();
        sysApiAuditEntity.setSauhId(
                sequenceGenerator.getNextSequence(PREFIX_SYS_API_USE_HIS.toString(), TABLE_SYS_API_USE_HIS.toString()));

        sysApiAuditEntity.setUserId(userId);
        sysApiAuditEntity.setHttpMethod(httpMethod);
        sysApiAuditEntity.setCallDate(LocalDate.now());
        sysApiAuditEntity.setUrl(request.getRequestURI());
        sysApiAuditEntity.setStsCode(response.getStatus());
        sysApiAuditEntity.setExecTime(String.valueOf(executionTime));
        sysApiAuditEntity.setClientIp(WebUtil.getClientIpAddress(request));
        sysApiAuditEntity.setParams(params);
        sysApiAuditEntity.setReqPayload(reqPayload);
        sysApiAuditEntity.setResPayload(resPayload);

        Long rsId = iResourceRepository.findRsIdByUrlAndMethod(request.getRequestURI(), httpMethod);
        if (rsId != null) {
            sysApiAuditEntity.setRsId(rsId);
        }else{
            sysApiAuditEntity.setRsId(0L);
        }
        sysApiAuditRepository.save(sysApiAuditEntity);
    }

    @Transactional
    public void saveAnonymousLog(HttpServletRequest request, HttpServletResponse response, String httpMethod,
            String params, long executionTime, String reqPayload, String resPayload) {
        SysApiAuditEntity sysApiAuditEntity = new SysApiAuditEntity();
        sysApiAuditEntity.setSauhId(
                sequenceGenerator.getNextSequence(PREFIX_SYS_API_USE_HIS.toString(), TABLE_SYS_API_USE_HIS.toString()));

        sysApiAuditEntity.setUserId(AnomyousUser_String.toString());
        sysApiAuditEntity.setHttpMethod(httpMethod);
        sysApiAuditEntity.setCallDate(LocalDate.now());
        sysApiAuditEntity.setUrl(request.getRequestURI());
        sysApiAuditEntity.setStsCode(response.getStatus());
        sysApiAuditEntity.setExecTime(String.valueOf(executionTime));
        sysApiAuditEntity.setClientIp(WebUtil.getClientIpAddress(request));
        sysApiAuditEntity.setParams(params);
        sysApiAuditEntity.setReqPayload(reqPayload);
        sysApiAuditEntity.setResPayload(resPayload);

        Long rsId = iResourceRepository.findRsIdByUrlAndMethod(request.getRequestURI(), httpMethod);
        if (rsId != null) {
            sysApiAuditEntity.setRsId(rsId);
        }else{
            sysApiAuditEntity.setRsId(0L);
        }
        sysApiAuditRepository.save(sysApiAuditEntity);
    }
}
