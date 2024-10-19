package org.gyh.forestry.domain;

import lombok.Data;
import net.postgis.jdbc.geometry.Point;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

/**
 * create by GYH on 2024/6/15
 */
@Data
public class AnimalRecognition {
    @Id
    private Integer id;

    /**
     * 位置
     */
    private String locationZh;

    /**
     * 经纬度
     */
    private Point location;

    /**
     * 类型
     */
    private String type;

    /**
     * 时间
     */
    private LocalDateTime createTime;

    /**
     * 上传人
     */
    private String creator;

    /**
     * 动物名字
     */
    private String name;

    /**
     * 上传图片
     */
    private String pic;

    /**
     * 动物详情
     */
    private String details;
    /**
     * 地区id
     */
    private Integer areaId;
}
