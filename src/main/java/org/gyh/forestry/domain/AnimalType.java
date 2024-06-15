package org.gyh.forestry.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

/**
 * create by GYH on 2024/6/15
 */
@Data
public class AnimalType {
    @Id
    private Integer id;

    /**
     * 动物类型名称
     */
    private String name;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 是否启用
     */
    private Boolean enable;
}