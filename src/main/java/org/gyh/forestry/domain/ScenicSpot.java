package org.gyh.forestry.domain;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * create by GYH on 2024/10/20
 */
@Data
public class ScenicSpot {
    private Integer id;

    /**
    * 景区名字
    */
    private String name;

    /**
    * 创建时间
    */
    private LocalDateTime createTime;
}
