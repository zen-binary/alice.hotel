package com.alice.hotel.api.domain.system.menu.service;


import com.alice.hotel.api.dto.system.menu.MenuRequestDTO;
import com.alice.hotel.api.dto.system.menu.MenuResponseDTO;
import com.alice.hotel.api.dto.system.menu.MenuSearchRequestDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MenuService {

    Page<MenuResponseDTO> findMenu(MenuSearchRequestDTO dto);

    List<MenuResponseDTO> getListParent(String siteType);

    boolean createMenu(MenuRequestDTO dto);

    boolean updateMenu(MenuRequestDTO dto);

    MenuResponseDTO updateDeleteFlagMenu(Long id);

    MenuResponseDTO detailMenu(Long id);

    Long countOrder(String id);

}
