package org.gyh.forestry.domain;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * create by GYH on 2024/5/20
 */
@Data
public class Notification {
    private Integer id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
}
