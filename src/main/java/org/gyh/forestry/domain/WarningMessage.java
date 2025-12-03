package org.gyh.forestry.domain;

import java.util.Date;
import lombok.Data;

/**
 * create by GYH on 2025/12/3
 */
@Data
public class WarningMessage {
    private Integer id;

    /**
     * 消息
     */
    private String msg;

    /**
     * 创建时间
     */
    private Date createTime;
}