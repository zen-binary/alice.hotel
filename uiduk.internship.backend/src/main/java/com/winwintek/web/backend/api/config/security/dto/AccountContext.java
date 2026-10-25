package com.winwintek.web.backend.api.config.security.dto;

import com.winwintek.web.backend.api.dto.system.UserDTO;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Data
public class AccountContext implements UserDetails {

    private UserDTO userDTO;
    private final List<GrantedAuthority> roles;

    public AccountContext(UserDTO userDTO, List<GrantedAuthority> roles){
        this.userDTO = userDTO;
        this.roles = roles;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
//        return String.valueOf(userDTO.getUserTsid());
        return String.valueOf(userDTO.getUserId());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
