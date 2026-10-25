package com.alice.hotel.api.domain.common.impl;

import com.alice.hotel.api.common.ApiStatus;
import com.alice.hotel.api.config.security.dto.AccountContext;
import com.alice.hotel.api.config.security.handler.CustomException;
import com.alice.hotel.api.domain.common.service.CommonService;
import com.alice.hotel.api.dto.common.nav.NavTreeDto;
import com.alice.hotel.api.dto.common.nav.RoleDto;
import com.alice.hotel.api.dto.system.UserDTO;
import com.alice.hotel.api.entity.common.navItems.INavTree;
import com.alice.hotel.api.entity.common.navItems.RoleAuthenticateEntity;
import com.alice.hotel.api.repository.common.nav.NavMngRepository;
import com.alice.hotel.api.repository.common.nav.NavTreeRepository;
import com.alice.hotel.api.util.Utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import static com.alice.hotel.api.common.Constants.SORT_ASC;

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
    private UserDetailsService userDetailsService;

    @Override
    public Authentication getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContextHolderStrategy().getContext()
                .getAuthentication();

        return authentication;
    }

    @Override
    public UserDTO getFullUserInfo() {
        Authentication authentication = SecurityContextHolder.getContextHolderStrategy().getContext()
                .getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new CustomException(ApiStatus.USER_NOT_FOUND);
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof AccountContext accountContext) {
            return accountContext.getUserDTO();
        }
        // Session may deserialize principal as username String (e.g. after DevTools restart)
        String username = principal instanceof UserDetails details
                ? details.getUsername()
                : String.valueOf(principal);
        if ("anonymousUser".equals(username)) {
            throw new CustomException(ApiStatus.USER_NOT_FOUND);
        }
        UserDetails reloaded = userDetailsService.loadUserByUsername(username);
        if (reloaded instanceof AccountContext accountContext) {
            return accountContext.getUserDTO();
        }
        throw new CustomException(ApiStatus.USER_NOT_FOUND);
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
        if (roleAuthenticateEntity == null) {
            RoleDto fallback = new RoleDto();
            fallback.setRlCd("ADMIN");
            fallback.setRlName("Alice Admin");
            return fallback;
        }
        return modelMapper.map(roleAuthenticateEntity, RoleDto.class);
    }

    @Override
    public List<NavTreeDto> getNavTreeItems() {
        Authentication authentication = SecurityContextHolder.getContextHolderStrategy().getContext()
                .getAuthentication();

        List<NavTreeDto> rootTrees = new ArrayList<>();
        UserDTO userDTO = getFullUserInfo();
        String roles = userDTO.getUserRoles();
        if (roles == null || roles.isBlank() || "null".equalsIgnoreCase(roles)) {
            return rootTrees;
        }
        long roleId = Long.parseLong(roles.split(",")[0].trim());
        List<INavTree> navTrees = navTreeRepository.getMenuTree(roleId);

        Map<Long, NavTreeDto> treeMap = new HashMap<>();

        // Step 1: Map all INavTree to NavTreeDTO and build treeMap
        for (INavTree navTree : navTrees) {
            NavTreeDto dto = new NavTreeDto();
            dto.setId(navTree.getId());
            String label = navTree.getVnName();
            if (label == null || label.isBlank()) {
                label = navTree.getName();
            }
            dto.setName(label);
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

}
