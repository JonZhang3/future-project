package com.future.common.core.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.future.common.constant.enums.UserType;
import com.future.common.core.domain.entity.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/**
 * 登录用户身份权限
 *
 * @author JonZhang
 */
@Data
public class LoginUser implements UserDetails {

    private static final long serialVersionUID = 1L;

    // 用户ID
    private Long userId;

    // 部门 ID
    private Long deptId;

    // 用户唯一标识
    private String token;

    // 登录时间
    private Long loginTime;

    // 过期时间
    private Long expireTime;

    // 登录 IP 地址
    private String ip;

    private UserType userType;
    
    private Set<String> permissions;

    private User user;

    public LoginUser() {
    }

    public LoginUser(User user, Set<String> permissions) {
        this.user = user;
        this.permissions = permissions;
    }

    public LoginUser(Long userId, Long deptId, Set<String> permissions, User user) {
        this.userId = userId;
        this.deptId = deptId;
        this.permissions = permissions;
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @JsonIgnore
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

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
