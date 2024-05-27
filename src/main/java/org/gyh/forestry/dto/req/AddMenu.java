package org.gyh.forestry.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author javaboy
 * @since 2024-01-03
 */
@Data
public class AddMenu implements Serializable {

    @Schema(description = "url")
    @NotNull
    private String url;

    @Schema(description = "路径")
    private String path;

    @Schema(description = "组件")
    private String component;

    @Schema(description = "名字")
    private String name;

    @Schema(description = "icon")
    private String iconCls;

    @Schema(description = "keepAlive")
    private Boolean keepAlive;

    @Schema(description = "requireAuth")
    private Boolean requireAuth;

    @Schema(description = "父id")
    private Integer parentId;

    @Schema(description = "是否启用")
    private Boolean enabled;

}
