package com.winwintek.web.backend.api.domain.common.impl;

import com.winwintek.web.backend.api.common.ApiStatus;
import com.winwintek.web.backend.api.common.Constants;
import com.winwintek.web.backend.api.config.security.dto.AccountContext;
import com.winwintek.web.backend.api.config.security.handler.CustomException;
import com.winwintek.web.backend.api.domain.common.service.CommonService;
import com.winwintek.web.backend.api.dto.admin.cmmCorporateMember.CmmCorporateMembersDTO;
import com.winwintek.web.backend.api.dto.common.nav.NavTreeDto;
import com.winwintek.web.backend.api.dto.common.nav.RoleDto;
import com.winwintek.web.backend.api.dto.system.UserDTO;
import com.winwintek.web.backend.api.entity.admin.cmnCorporateMembers.CmmCorporateMembersEntity;
import com.winwintek.web.backend.api.entity.common.navItems.INavTree;
import com.winwintek.web.backend.api.entity.common.navItems.RoleAuthenticateEntity;
import com.winwintek.web.backend.api.repository.admin.cmmCorporateMember.CmmCorporateMembersRepository;
import com.winwintek.web.backend.api.repository.common.nav.NavMngRepository;
import com.winwintek.web.backend.api.repository.common.nav.NavTreeRepository;
import com.winwintek.web.backend.api.util.Utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import static com.winwintek.web.backend.api.common.Constants.SORT_ASC;

@Service
@RequiredArgsConstructor
public class CommonServiceImpl implements CommonService {

    @Autowired
    private NavMngRepository navMngRepository;

    @Autowired
    private NavTreeRepository navTreeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CmmCorporateMembersRepository cmmCorporateMembersRepository;

    @Override
    public Authentication getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContextHolderStrategy().getContext()
                .getAuthentication();

        return authentication;
    }

    @Override
    public UserDTO getFullUserInfo() {
        AccountContext principal = (AccountContext) SecurityContextHolder.getContextHolderStrategy().getContext()
                .getAuthentication().getPrincipal();
        UserDTO userDTO = principal.getUserDTO();

        return userDTO;
    }

    @Override
    public Pageable buildPageable(int page, int size, String sortStr) {

        Pageable pageable;

        if (page <= 0 || size <= 0) {
            throw new CustomException(ApiStatus.BAD_REQUEST_VALID);
        }

        int pageNumber = (page == 1) ? 0 : page - 1;

        if (sortStr != null && ! sortStr.isEmpty()) {
            String[] sortParams = sortStr.split(",");
            String sortBy = sortParams[ 0 ].trim();
            String sortOrder = sortParams[ 1 ].trim();
            Sort.Direction direction = null;
            if (sortOrder.equalsIgnoreCase(SORT_ASC)) {
                direction = Sort.Direction.ASC;
            } else {
                direction = Sort.Direction.DESC;
            }

            String sortBySnakeCase = Utils.camelToSnake(sortBy);
            pageable = PageRequest.of(pageNumber, size, Sort.by(direction, sortBySnakeCase));
        } else {
            pageable = PageRequest.of(pageNumber, size);
        }
        return pageable;
    }

    @Override
    public RoleDto getNavItems() {

        UserDTO userDTO = getFullUserInfo();
        RoleAuthenticateEntity roleAuthenticateEntity = navMngRepository.findByRlId(
                Long.valueOf(userDTO.getUserRoles()));
        RoleDto roleDTO = modelMapper.map(roleAuthenticateEntity, RoleDto.class);

        return roleDTO;
    }

    @Override
    public List<NavTreeDto> getNavTreeItems() {
        Authentication authentication = SecurityContextHolder.getContextHolderStrategy().getContext()
                .getAuthentication();

        List<INavTree> navTrees = new ArrayList<INavTree>();
        UserDTO userDTO = getFullUserInfo();
        navTrees = navTreeRepository.getMenuTree(Long.valueOf(userDTO.getUserRoles()));

        Map<Long, NavTreeDto> treeMap = new HashMap<>();
        List<NavTreeDto> rootTrees = new ArrayList<>();

        // Step 1: Map all INavTree to NavTreeDTO and build treeMap
        for (INavTree navTree : navTrees) {
            NavTreeDto dto = new NavTreeDto();
            dto.setId(navTree.getId());
            dto.setName(navTree.getName());
            dto.setScreenName(navTree.getScreenName());
            dto.setRoute(navTree.getRouter());
            dto.setDisplayOrder(navTree.getDisplayOrder());
            dto.setTreeLevel(navTree.getTreeLevel());
            dto.setParentId(navTree.getParentId());
            dto.setBlankScreen(navTree.getBlankScreen());
            treeMap.put(navTree.getId(), dto);
        }

        // Step 2: Build the hierarchy based on parentId and treeLevel
        for (INavTree navTree : navTrees) {
            NavTreeDto dto = treeMap.get(navTree.getId());
            Long parentId = navTree.getParentId();

            if (parentId == null) {
                // It's a root node
                rootTrees.add(dto);
            } else {
                // It's a child node
                NavTreeDto parentDto = treeMap.get(parentId);
                if (parentDto != null) {
                    if (parentDto.getSubMenus() == null) {
                        parentDto.setSubMenus(new ArrayList<>());
                    }
                    parentDto.getSubMenus().add(dto);
                }
            }
        }

        return rootTrees;
    }

    @Override
    public CmmCorporateMembersDTO getCorporateMembersInfo() {
        UserDTO userDTO = getFullUserInfo();
        CmmCorporateMembersEntity cmmCorporateMembersEntity = cmmCorporateMembersRepository.findByCormUserIdAndUseYnAndDelYn(
                userDTO.getUserId(), Constants.STATE_Y, Constants.STATE_N);

        if (Objects.nonNull(cmmCorporateMembersEntity)) {
            CmmCorporateMembersDTO cmmCorporateMembersDTO = modelMapper.map(cmmCorporateMembersEntity,
                    CmmCorporateMembersDTO.class);
            return cmmCorporateMembersDTO;
        }
        return null;
    }

}
