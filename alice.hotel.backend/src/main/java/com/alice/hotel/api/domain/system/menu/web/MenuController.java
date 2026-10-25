package com.alice.hotel.api.domain.system.menu.web;

import com.alice.hotel.api.common.ApiResponse;
import com.alice.hotel.api.common.ApiStatus;
import com.alice.hotel.api.domain.system.menu.service.MenuService;
import com.alice.hotel.api.dto.system.menu.MenuRequestDTO;
import com.alice.hotel.api.dto.system.menu.MenuResponseDTO;
import com.alice.hotel.api.dto.system.menu.MenuSearchRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping ("/a/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @PostMapping ("/list")
    public ApiResponse<Page<MenuResponseDTO>> findMenu(@RequestBody MenuSearchRequestDTO searchDTO) {
        Page<MenuResponseDTO> searchResults = menuService.findMenu(searchDTO);

        ApiResponse<Page<MenuResponseDTO>> lstResult = new ApiResponse<>(ApiStatus.SUCCESS, searchResults);

        return lstResult;
    }

    @PostMapping ("/create")
    public ApiResponse<Boolean> createMenu(@RequestBody MenuRequestDTO dto) {
        boolean check = menuService.createMenu(dto);
        return new ApiResponse<>(ApiStatus.CREATED, check);
    }

    @PutMapping ("/update")
    public ApiResponse<Boolean> updateMenu(@RequestBody MenuRequestDTO dto) {
        boolean check = menuService.createMenu(dto);
        return new ApiResponse<>(ApiStatus.SUCCESS, check);
    }

    @PostMapping ("/list-parent")
    public ApiResponse<List<MenuResponseDTO>> getListParent(@RequestBody String siteType) {
        List<MenuResponseDTO> searchResults = menuService.getListParent(siteType);

        ApiResponse<List<MenuResponseDTO>> lstResult = new ApiResponse<>(ApiStatus.SUCCESS, searchResults);

        return lstResult;
    }

    @PostMapping ("/")
    public ApiResponse<MenuResponseDTO> getMenuById(@RequestBody String id) {
        MenuResponseDTO detailMenu = menuService.detailMenu(Long.parseLong(id));

        ApiResponse<MenuResponseDTO> result = new ApiResponse<>(ApiStatus.SUCCESS, detailMenu);

        return result;
    }

    @PutMapping ("/")
    public ApiResponse<MenuResponseDTO> updateDeleteFlag(@RequestBody String id) {
        MenuResponseDTO deleteResult = menuService.updateDeleteFlagMenu(Long.parseLong(id));

        ApiResponse<MenuResponseDTO> result = new ApiResponse<>(ApiStatus.SUCCESS, deleteResult);

        return result;
    }

    @PostMapping ("/countOrder")
    public ApiResponse<Long> countOrder(@RequestBody String id) {
        long deleteResult = menuService.countOrder(id);

        ApiResponse<Long> result = new ApiResponse<>(ApiStatus.SUCCESS, deleteResult);

        return result;
    }

}
