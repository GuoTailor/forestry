package org.gyh.forestry.domain;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * create by GYH on 2024/6/17
 * 动物管理
 */
@Data
public class AnimalManage {
    @Id
    private Integer id;

    /**
    * 动物名称
    */
    private String name;

    /**
    * 动物类型id
    */
    private Integer animalTypeId;

    /**
    * 动物描述
    */
    private String describe;

    /**
    * 是否启用
    */
    private Boolean enable;

    /**
    * 创建时间
    */
    private LocalDateTime createTime;
}