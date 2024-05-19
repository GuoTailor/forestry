package org.gyh.forestry.dto.req;

import lombok.Data;

/**
 * create by GYH on 2024/4/26
 */
@Data
public class CameraInfoReq {
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
     * 相机时间 2018-10-23 07:50:00
     */
    private String Time;
    /**
     * 相机软件版本
     */
    private String FWVersion;
    /**
     * 拍照间隔
     * 30Sec 相机监控到活动物体后每间隔设定时间上传一次图片，直到活动离开监控范围
     */
    private String PirInterval;
    /**
     * 照片大小
     * 14 照片大小为14兆
     */
    private String PSize;
    /**
     * 视频分辨率
     * 1920x1080
     */
    private String VSize;
    /**
     * 视频长度
     * 20Sec 每次拍摄长度20秒
     */
    private String VLength;
}
