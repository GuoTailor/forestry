package org.gyh.forestry.dto.req;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * create by GYH on 2024/4/26
 */
@Data
public class CameraUploadReq {
    /**
     * 电信IMEI码
     * 相机唯一标示
     */
    private String ImeiVal;
    /**
     * 信号
     * 无线信号强度：0小 5最大
     */
    private String SignalVal;

    /**
     * 电量
     * 0-100 低于25则视为低电量
     */
    private String BatteryVal;

    /**
     * 温度
     * 环境温度
     */
    private String TemperatureVal;

    /**
     * sd卡总容量 单位（MB）
     */
    private String SDTotalSpaceVal;

    /**
     * sd卡使用空间 单位（MB）
     */
    private String SDUsedpaceVal;

    /**
     * 经度
     */
    private String LongitudeVal;
    /**
     * 纬度
     */
    private String LatitudeVal;

    /**
     * 图片/视频文件
     */
    private MultipartFile Image;

    /**
     * 相机时间 2018-10-23 07:50:00
     */
    private String Time;
}
