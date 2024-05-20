package org.gyh.forestry.domain;

import java.util.Date;
import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * create by GYH on 2024/5/20
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
    private Date createdAt;

    /**
    * 发送时间
    */
    private Date sendTime;

    /**
    * 创建人
    */
    private String creator;
}
