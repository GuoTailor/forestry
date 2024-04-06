package org.gyh.forestry.domain.vo;


import org.gyh.forestry.domain.Menu;
import org.gyh.forestry.domain.Role;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 */
@Data
public class MenuWithRole extends Menu implements Serializable {
    private List<Role> roles;
}
