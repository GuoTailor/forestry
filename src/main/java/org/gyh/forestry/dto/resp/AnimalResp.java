package org.gyh.forestry.dto.resp;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * create by GYH on 2024/3/30
 */
@Data
public class AnimalResp {
    private Integer id;

    /**
     * 位置
     */
    private String locationZh;

    /**
     * 经纬度
     */
    private JsonPoint location;

    /**
     * 类型
     */
    private String type;

    /**
     * 时间
     */
    private LocalDateTime createTime;

    /**
     * 相机id
     */
    private String cameraId;

    /**
     * 动物名字
     */
    private String name;
}
