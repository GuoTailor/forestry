package org.gyh.forestry.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.gyh.forestry.domain.User;

import java.util.List;

/**
 * create by GYH on 2024/3/26
 */
public record UpdateUserReq(
        @Schema(description = "用户id")
        @NotNull
        Integer id,
        @Schema(description = "用户名")
        String username,
        String password,
        List<Integer> roles,
        @Schema(description = "是否启用")
        Boolean enable,
        @Schema(description = "姓名")
        String name,
        @Schema(description = "手机号")
        String tel,
        @Schema(description = "邮箱")
        String email
) {
    public User toUser() {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setEnable(enable);
        user.setUsername(username);
        user.setTel(tel);
        user.setEmail(email);
        return user;
    }
}
