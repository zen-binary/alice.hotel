package com.alice.hotel.api.domain.system.menu.impl;

import com.alice.hotel.api.common.Constants;
import com.alice.hotel.api.domain.common.service.CommonService;
import com.alice.hotel.api.domain.system.menu.service.MenuService;
import com.alice.hotel.api.dto.system.menu.MenuRequestDTO;
import com.alice.hotel.api.dto.system.menu.MenuResponseDTO;
import com.alice.hotel.api.dto.system.menu.MenuSearchRequestDTO;
import com.alice.hotel.api.entity.system.menu.IMenuResponse;
import com.alice.hotel.api.entity.system.menu.SysMenuEntity;
import com.alice.hotel.api.repository.system.menu.IMenusRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.alice.hotel.api.common.Constants.DELETE_FLAG_INACTIVE;
import static com.alice.hotel.api.common.Constants.STATE_N;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {

    @Autowired
    private CommonService commonService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IMenusRepository menuRepository;

    @Override
    public Page<MenuResponseDTO> findMenu(MenuSearchRequestDTO dto) {
        int page = dto.getPage();
        int size = dto.getSize();
        String sort = dto.getSort();
        Pageable pageable = commonService.buildPageable(page, size, sort);

        Page<IMenuResponse> lstData = menuRepository.searchMenu(dto, pageable);
        Page<MenuResponseDTO> lstResults = lstData.map(item -> modelMapper.map(item, MenuResponseDTO.class));

        return lstResults;
    }

    @Override
    public List<MenuResponseDTO> getListParent(String siteType) {
        List<IMenuResponse> lstData = menuRepository.getListParent(siteType, STATE_N);

        List<MenuResponseDTO> lstResults = lstData.stream().map(item -> modelMapper.map(item, MenuResponseDTO.class))
                .toList();

        return lstResults;
    }

    @Override
    public boolean createMenu(MenuRequestDTO dto) {
        boolean isDouble = true;
        int cntCheck = menuRepository.getDoubleRoute(dto.getRoute(), STATE_N, dto.getId());
        if (cntCheck > 0) {
            isDouble = false;
        } else {
            SysMenuEntity entity = modelMapper.map(dto, SysMenuEntity.class);
            entity.setDelYn(Constants.STATE_N);
            menuRepository.save(entity);
        }
        return isDouble;
    }

    @Override
    public boolean updateMenu(MenuRequestDTO dto) {
        boolean isDouble = true;
        int cntCheck = menuRepository.getDoubleRoute(dto.getRoute(), STATE_N, dto.getId());
        if (cntCheck > 0) {
            isDouble = false;
        } else {
            SysMenuEntity entity = new SysMenuEntity();

            if (dto.getId() != null) {
                entity = menuRepository.findById(dto.getId()).orElse(new SysMenuEntity());
                modelMapper.map(dto, entity);
            }
            menuRepository.save(entity);
        }
        return isDouble;
    }

    @Override
    public MenuResponseDTO updateDeleteFlagMenu(Long id) {
        SysMenuEntity deleteMenu = menuRepository.findById(id).orElse(new SysMenuEntity());

        if (deleteMenu.getId() != null) {
            List<SysMenuEntity> lstChildren = menuRepository.findAllByParentId(deleteMenu.getId());
            lstChildren.forEach(item -> {
                item.setDelYn(Constants.STATE_Y);
            });
            menuRepository.saveAll(lstChildren);
            menuRepository.updateDelYn(Constants.STATE_Y, deleteMenu.getId());
        }
        MenuResponseDTO result = modelMapper.map(deleteMenu, MenuResponseDTO.class);

        return result;
    }

    @Override
    public MenuResponseDTO detailMenu(Long id) {
        SysMenuEntity entity = menuRepository.findById(id).orElse(new SysMenuEntity());
        MenuResponseDTO result = modelMapper.map(entity, MenuResponseDTO.class);

        return result;
    }

    @Override
    public Long countOrder(String id) {
        long count = menuRepository.countNumberChildrenMenu(id, STATE_N);

        return count;
    }

}
