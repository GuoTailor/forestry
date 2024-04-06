package org.gyh.forestry.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author javaboy
 * @since 2024-01-03
 */
@Data
public class Menu implements Serializable {


    @Id
    private Integer id;

    private String url;

    private String path;

    private String component;

    private String name;

    private String iconCls;

    private Boolean keepAlive;

    private Boolean requireAuth;

    private Integer parentId;

    private Boolean enabled;

}
