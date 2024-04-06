package org.gyh.forestry.dto.req;

import lombok.Data;
import org.gyh.forestry.dto.PageReq;

import java.time.LocalDateTime;

/**
 * create by GYH on 2024/3/30
 */
@Data
public class OperationRecordPage extends PageReq {
    private String modelName;
    /**
     * 操作用户
     */
    private String operationUser;
    /**
     * 请求方式
     */
    private String method;
    /**
     * 操作状态
     */
    private Boolean state;
    /**
     * 操作时间
     */
    private LocalDateTime startCreateTime;
    private LocalDateTime endCreateTime;
}
