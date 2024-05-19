package org.gyh.forestry.dto.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.gyh.forestry.domain.Role;

import java.time.LocalDateTime;
import java.util.List;

/**
 * create by GYH on 2024/3/24
 */
@Data
public class UserInfo {
    private Integer id;
    @Schema(description = "用户账号")
    private String username;
    @Schema(description = "角色")
    private List<Role> roles;
    @Schema(description = "是否启用")
    private Boolean enable;
    @Schema(description = "用户姓名")
    private String name;
    @Schema(description = "手机号")
    private String tel;
    @Schema(description = "邮箱")
    private String email;
    @Schema(description = "创建人")
    private String creator;
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
