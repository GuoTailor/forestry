package org.gyh.forestry.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * create by GYH on 2024/4/6
 */
@Data
public class PointUser {
    @Id
    private Integer id;

    /**
    * 用户id
    */
    private Integer userId;

    /**
    * 点位id
    */
    private Integer pointId;
}
