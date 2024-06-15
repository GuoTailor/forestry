package org.gyh.forestry.dto.resp;

import lombok.Data;
import org.gyh.forestry.dto.JsonPoint;

import java.time.LocalDateTime;

/**
 * create by GYH on 2024/3/30
 */
@Data
public class AnimalRecognitionResp {
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

    /** * 上传人
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
}
