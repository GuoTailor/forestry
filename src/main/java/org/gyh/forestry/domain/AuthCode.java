package org.gyh.forestry.domain;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;
import org.gyh.forestry.enums.CodeStateEnum;
import org.springframework.data.annotation.Id;

/**
 * create by GYH on 2024/5/18
 */
@Data
public class AuthCode {
    @Id
    private Integer id;

    /**
     * 授权码
     */
    private String code;

    /**
     * 操作者
     */
    private String handler;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 状态CodeStateEnum
     */
    private CodeStateEnum status;

    /**
     * 生成时间
     */
    private LocalDateTime createTime;
}
