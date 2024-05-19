package org.gyh.forestry.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * create by GYH on 2024/5/18
 */
@Data
public class AddRole {
    /**
     * 角色名字
     */
    @Schema(description = "角色名字")
    private String authority;

    /**
     * 角色中文名
     */
    @Schema(description = "角色中文名")
    private String roleName;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Integer sortNum;

    /**
     * 是否启用
     */
    @Schema(description = "是否启用")
    private Boolean enable;

    /**
     * 描述
     */
    @Schema(description = "描述")
    private String describe;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "菜单id")
    private List<Integer> menuIds;
}
