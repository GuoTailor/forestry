package org.gyh.forestry.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

/**
 * create by GYH on 2024/3/27
 */
@Data
public class OperationRecord {
    @Id
    private Integer id;

    /**
     * 模块名字
     */
    private String modelName;

    /**
     * 操作类型
     */
    private String operation;

    /**
     * 请求方式
     */
    private String method;

    /**
     * 操作url
     */
    private String url;

    /**
     * 操作ip
     */
    private String ip;

    /**
     * 操作地址
     */
    private String location;

    /**
     * 操作状态
     */
    private Boolean state;

    /**
     * 调用方法
     */
    private String function;

    /**
     * 请求参数
     */
    private String request;

    /**
     * 响应
     */
    private String response;

    /**
     * 操作用户
     */
    private String operationUser;

    /**
     * 操作时间
     */
    private LocalDateTime createTime;
}
