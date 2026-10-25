package com.alice.hotel.api.domain.system.loginHistory.service;


import com.alice.hotel.api.dto.system.loginHistory.LoginHistoryPageDTO;
import com.alice.hotel.api.dto.system.loginHistory.LoginHistoryResDTO;
import com.alice.hotel.api.dto.system.loginHistory.LoginRoleDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LoginHisService {

    Page<LoginHistoryResDTO> getAllLoginHis(LoginHistoryPageDTO loginHistoryPageDTO);

    List<LoginRoleDTO> selectRole() throws Exception;

}
