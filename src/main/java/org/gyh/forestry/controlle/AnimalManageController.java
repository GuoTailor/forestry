package org.gyh.forestry.controlle;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.gyh.forestry.aspect.LogRecord;
import org.gyh.forestry.domain.AnimalManage;
import org.gyh.forestry.dto.PageInfo;
import org.gyh.forestry.dto.ResponseInfo;
import org.gyh.forestry.dto.req.AddAnimalManageReq;
import org.gyh.forestry.dto.req.AnimalManagePageReq;
import org.gyh.forestry.dto.req.UpdateAnimalManageReq;
import org.gyh.forestry.dto.resp.AnimalManageResp;
import org.gyh.forestry.service.AnimalManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * create by GYH on 2024/6/19
 */
@Tag(name = "动物管理")
@RestController
@RequestMapping("/animal/manage")
public class AnimalManageController {
    @Autowired
    private AnimalManageService animalManageService;

    @PostMapping("/add")
    @Operation(summary = "添加动物", security = {@SecurityRequirement(name = "Authorization")})
    @LogRecord(model = "动物管理", method = "添加动物")
    public ResponseInfo<AnimalManage> addAnimalManage(@RequestBody AddAnimalManageReq req) {
        return ResponseInfo.ok(animalManageService.addAnimalManage(req));
    }

    @PostMapping("/update")
    @Operation(summary = "更新动物", security = {@SecurityRequirement(name = "Authorization")})
    @LogRecord(model = "动物管理", method = "更新动物")
    public ResponseInfo<Boolean> updateAnimalManage(@RequestBody UpdateAnimalManageReq req) {
        return ResponseInfo.ok(animalManageService.updateAnimalManage(req));
    }

    @PostMapping("/page")
    @Operation(summary = "分页查询动物", security = {@SecurityRequirement(name = "Authorization")})
    public PageInfo<AnimalManageResp> selectByPage(@RequestBody AnimalManagePageReq pageReq) {
        return animalManageService.selectByPage(pageReq);
    }

    @GetMapping("/delete")
    @Operation(summary = "删除动物", security = {@SecurityRequirement(name = "Authorization")})
    @LogRecord(model = "动物管理", method = "删除动物")
    public ResponseInfo<Boolean> deleteAnimal(@RequestParam("id") Integer id) {
        return ResponseInfo.ok(animalManageService.deleteAnimal(id));
    }
}
