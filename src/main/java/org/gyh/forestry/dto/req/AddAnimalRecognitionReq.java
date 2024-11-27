package org.gyh.forestry.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.gyh.forestry.dto.JsonPoint;

import java.time.LocalDateTime;
import java.util.List;

/**
 * create by GYH on 2024/6/4
 */
@Data
public class AddAnimalRecognitionReq {

    /**
     * 位置
     */
    @Schema(description = "位置")
    private String locationZh;

    /**
     * 经纬度
     */
    @Schema(description = "经纬度")
    private JsonPoint location;

    @Schema(description = "动物信息")
    private List<AnimalInfo> animalInfo;

    /**
     * 图片地址
     */
    @Schema(description = "图片地址")
    private String pic;

    private LocalDateTime createTime;

    @Data
    public static class AnimalInfo {
        /**
         * 类型
         */
        @Schema(description = "类型")
        private String type;

        /**
         * 动物名字
         */
        @Schema(description = "动物名字")
        private String name;

        /**
         * 动物详情
         */
        @Schema(description = "动物详情")
        private String details;
    }
}
