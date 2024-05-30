package org.gyh.forestry.websocket;

import lombok.Data;

/**
 * create by GYH on 2024/5/30
 */
@Data
public class ServiceRequestInfo {
    private String order;
    private Object body;
    private Integer req;
}
