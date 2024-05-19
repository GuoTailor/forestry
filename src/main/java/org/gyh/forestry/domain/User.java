package org.gyh.forestry.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * create by GYH on 2024/5/14
 */
@Data
public class User implements UserDetails, Serializable {
    @Id
    private Integer id;

    /**
     * 用户名
     */
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @Transient
    private List<Role> roles;
    private Boolean enable;
    private String name;
    private LocalDateTime createTime;
    @Transient
    private Boolean credentialsNonExpired = true;

    /**
     * 手机号
     */
    private String tel;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 创建人
     */
    private String creator;

    @JsonIgnore
    public List<String> getStringRoles() {
        if (CollectionUtils.isEmpty(roles)) return List.of();
        return roles.stream().map(Role::getAuthority).collect(Collectors.toList());
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return enable;
    }
}
