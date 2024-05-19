package org.gyh.forestry.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.gyh.forestry.dto.PageReq;

/**
 * create by GYH on 2024/3/24
 */
@Data
public class UserPageReq extends PageReq {
    @Schema(description = "用户名")
    private String username;
    @Schema(description = "手机号")
    private String tel;
}
