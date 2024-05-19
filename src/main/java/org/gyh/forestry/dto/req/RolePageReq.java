package org.gyh.forestry.dto.req;

import lombok.Data;
import org.gyh.forestry.dto.PageReq;

import java.time.LocalDateTime;

/**
 * create by GYH on 2024/5/14
 */
@Data
public class RolePageReq extends PageReq {

    private String roleName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
