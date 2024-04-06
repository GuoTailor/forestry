package org.gyh.forestry.domain.vo;


import lombok.Data;
import org.gyh.forestry.domain.Menu;

import java.io.Serializable;
import java.util.List;

/**
 */
@Data
public class MenuVO extends Menu implements Serializable {
    private List<MenuVO> children;
}
