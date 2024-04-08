package org.gyh.forestry.controlle;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.gyh.forestry.dto.PageInfo;
import org.gyh.forestry.dto.PageReq;
import org.gyh.forestry.dto.ResponseInfo;
import org.gyh.forestry.dto.resp.AnimalResp;
import org.gyh.forestry.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * create by GYH on 2024/3/30
 */
@Tag(name = "动物监测")
@RestController
@RequestMapping("/animal")
public class AnimalController {
    @Autowired
    private AnimalService animalService;

    @Operation(summary = "分页查询动物", security = {@SecurityRequirement(name = "Authorization")})
    @PostMapping("/page")
    public PageInfo<AnimalResp> selectByPage(@RequestBody PageReq pageReq) {
        return animalService.selectByPage(pageReq);
    }

    @Operation(summary = "获取详情", security = {@SecurityRequirement(name = "Authorization")})
    @GetMapping()
    public ResponseInfo<AnimalResp> selectById(@RequestParam Integer id) {
        return ResponseInfo.ok(animalService.selectById(id));
    }
}
