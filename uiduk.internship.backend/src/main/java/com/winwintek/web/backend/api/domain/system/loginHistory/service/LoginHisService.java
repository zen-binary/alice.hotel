package com.winwintek.web.backend.api.domain.system.loginHistory.service;


import com.winwintek.web.backend.api.dto.system.loginHistory.LoginHistoryPageDTO;
import com.winwintek.web.backend.api.dto.system.loginHistory.LoginHistoryResDTO;
import com.winwintek.web.backend.api.dto.system.loginHistory.LoginRoleDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LoginHisService {

    Page<LoginHistoryResDTO> getAllLoginHis(LoginHistoryPageDTO loginHistoryPageDTO);

    List<LoginRoleDTO> selectRole() throws Exception;

}
