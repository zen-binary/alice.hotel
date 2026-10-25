package com.winwintek.web.backend.api.domain.system.loginHistory.web;

import com.winwintek.web.backend.api.common.ApiResponse;
import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.domain.system.loginHistory.service.LoginHisService;
import com.winwintek.web.backend.api.dto.system.loginHistory.LoginHistoryPageDTO;
import com.winwintek.web.backend.api.dto.system.loginHistory.LoginHistoryResDTO;
import com.winwintek.web.backend.api.dto.system.loginHistory.LoginRoleDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping ("/a/loginHis")
@RequiredArgsConstructor
public class LoginHisController {

    @Autowired
    private LoginHisService loginHisService;

    /**
     * get list search login history
     *
     * @param loginHistoryPageDTO
     * @return
     */
    @PostMapping ("/")
    public ApiResponse<Page<LoginHistoryResDTO>> getAllLoginHis(@RequestBody LoginHistoryPageDTO loginHistoryPageDTO) {
        Page<LoginHistoryResDTO> categories = loginHisService.getAllLoginHis(loginHistoryPageDTO);
        return new ApiResponse<>(ApiStatus.SUCCESS, categories);
    }

    /**
     * get list role id login
     *
     * @return
     */
    @PostMapping ("/role")
    public ApiResponse<List<LoginRoleDTO>> getRole() throws Exception {
        List<LoginRoleDTO> getListRole = loginHisService.selectRole();
        return new ApiResponse<>(ApiStatus.SUCCESS, getListRole);
    }

}
