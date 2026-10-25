package com.winwintek.web.backend.api.domain.common.service;

import com.winwintek.web.backend.api.dto.admin.cmmCorporateMember.CmmCorporateMembersDTO;
import com.winwintek.web.backend.api.dto.common.nav.NavTreeDto;
import com.winwintek.web.backend.api.dto.common.nav.RoleDto;
import com.winwintek.web.backend.api.dto.system.UserDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface CommonService {

    Authentication getUserInfo();

    Pageable buildPageable(int page, int size, String sort);

    RoleDto getNavItems();

    UserDTO getFullUserInfo();

    List<NavTreeDto> getNavTreeItems();

    CmmCorporateMembersDTO getCorporateMembersInfo();

}