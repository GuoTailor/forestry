package org.gyh.forestry.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

/**
 * 模型
 * create by GYH on 2024/5/23
 */
@Data
public class Model {
    @Id
    private Integer id;

    /**
     * 模型名字
     */
    private String name;

    /**
     * 是否启用
     */
    private Boolean enable;

    /**
     * 模型路径
     */
    private String path;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 上传时间
     */
    private LocalDateTime createdTime;
}
