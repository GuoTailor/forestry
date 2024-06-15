package org.gyh.forestry.dto.req;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * create by GYH on 2024/6/15
 */
@Data
public class AddAnimalTypeReq {
    @NotEmpty
    private String name;
}
