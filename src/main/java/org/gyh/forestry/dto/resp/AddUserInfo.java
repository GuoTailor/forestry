package org.gyh.forestry.dto.resp;

import org.gyh.forestry.domain.User;

import java.util.List;

/**
 * create by GYH on 2024/3/26
 */
public record AddUserInfo(
        String username,
        String password,
        List<Integer> roles,
        Boolean enable,
        String name
) {
    public User toUser() {
        User user = new User();
        user.setName(name);
        user.setEnable(enable);
        user.setUsername(username);
        return user;
    }
}
