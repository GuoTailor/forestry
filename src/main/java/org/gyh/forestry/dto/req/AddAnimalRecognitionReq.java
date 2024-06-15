package org.gyh.forestry.dto.req;

import lombok.Data;
import org.gyh.forestry.dto.JsonPoint;

/**
 * create by GYH on 2024/6/4
 */
@Data
public class AddAnimalRecognitionReq {

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
     * 动物名字
     */
    private String name;

    /**
     * 图片地址
     */
    private String pic;

    /**
     * 动物详情
     */
    private String details;
}