package org.gyh.forestry.dto.resp;

import lombok.Data;
import org.gyh.forestry.domain.Role;

import java.time.LocalDateTime;
import java.util.List;

/**
 * create by GYH on 2024/3/24
 */
@Data
public class UserInfo {
    private Integer id;
    private String username;
    private List<Role> roles;
    private Boolean enable;
    private String name;
    private LocalDateTime createTime;
}
