package org.gyh.forestry.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

/**
 * create by GYH on 2022/10/27
 */
@Data
public class Role implements GrantedAuthority, Serializable {
    // 超级管理员
    public static final String SUPER_ADMIN = "ROLE_SUPER_ADMIN";
    // 管理员
    public static final String ADMIN = "ROLE_ADMIN";
    // 用户
    public static final String USER = "ROLE_USER";

    @Id
    private Integer id;
    private String authority;
    private String roleName;

    public Role() {
    }

    public Role(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
