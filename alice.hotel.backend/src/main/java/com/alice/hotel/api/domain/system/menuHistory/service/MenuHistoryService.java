package com.alice.hotel.api.domain.system.menuHistory.service;


import com.alice.hotel.api.dto.system.menuHistory.MenuHisRequestDTO;
import com.alice.hotel.api.dto.system.menuHistory.MenuHisResDTO;
import com.alice.hotel.api.dto.system.menuHistory.MenuHisSearchDTO;
import org.springframework.data.domain.Page;

public interface MenuHistoryService {

    public String createLogMenuHistory(MenuHisRequestDTO menuHis);

    Page<MenuHisResDTO> logMenuHistoryList(MenuHisSearchDTO menuHis);

}
