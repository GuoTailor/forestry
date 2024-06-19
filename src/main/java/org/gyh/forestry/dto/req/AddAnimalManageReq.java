package org.gyh.forestry.dto.req;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * create by GYH on 2024/6/17
 * 动物管理
 */
@Data
public class AddAnimalManageReq {

    /**
     * 动物名称
     */
    @NotEmpty
    private String name;

    /**
     * 动物类型id
     */
    @NotEmpty
    private Integer animalTypeId;

    /**
     * 动物描述
     */
    private String describe;

}