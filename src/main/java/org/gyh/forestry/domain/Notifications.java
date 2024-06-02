package org.gyh.forestry.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

/**
 * create by GYH on 2024/5/31
 */
@Data
public class Notifications {
    @Id
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 发送时间
     */
    private LocalDateTime sendTime;

    /**
     * 创建人
     */
    private String creator;
}
