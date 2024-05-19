package org.gyh.forestry.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Date;

/**
 * create by GYH on 2024/5/18
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

    /**
     * 角色名字
     */
    private String authority;

    /**
     * 角色中文名
     */
    private String roleName;

    /**
     * 排序
     */
    private Integer sortNum;

    /**
     * 是否启用
     */
    private Boolean enable;

    /**
     * 描述
     */
    private String describe;

    /**
     * 创建时间
     */
    private Date createTime;

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
