package org.gyh.forestry.dto.req;

import lombok.Data;
import org.gyh.forestry.dto.PageReq;

/**
 * create by GYH on 2024/8/13
 */
@Data
public class AreaInfoPageReq extends PageReq {

    /**
     * 区域名字
     */
    private String name;

}
