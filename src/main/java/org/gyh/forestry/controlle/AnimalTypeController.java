package org.gyh.forestry.controlle;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.gyh.forestry.aspect.LogRecord;
import org.gyh.forestry.domain.AnimalType;
import org.gyh.forestry.dto.PageInfo;
import org.gyh.forestry.dto.ResponseInfo;
import org.gyh.forestry.dto.req.AddAnimalTypeReq;
import org.gyh.forestry.dto.req.AnimalTypePageReq;
import org.gyh.forestry.dto.req.UpdateAnimalTypeReq;
import org.gyh.forestry.service.AnimalTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * create by GYH on 2024/6/15
 */
@Tag(name = "动物类型")
@RestController
@RequestMapping("/animal/type")
public class AnimalTypeController {
    @Autowired
    private AnimalTypeService animalTypeService;

    @PostMapping(value = "/add", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "添加动物类型", security = {@SecurityRequirement(name = "Authorization")})
    @LogRecord(model = "动物类型", method = "添加动物类型")
    public ResponseInfo<AnimalType> addAnimalType(@RequestBody @Valid AddAnimalTypeReq req) {
        return ResponseInfo.ok(animalTypeService.addAnimalType(req));
    }

    @PostMapping(value = "/update", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "更新动物类型", security = {@SecurityRequirement(name = "Authorization")})
    @LogRecord(model = "动物类型", method = "更新动物类型")
    public ResponseInfo<AnimalType> updateAnimalType(@RequestBody @Valid UpdateAnimalTypeReq req) {
        return ResponseInfo.ok(animalTypeService.updateAnimalType(req));
    }

    @Operation(summary = "分页查询动物类型", security = {@SecurityRequirement(name = "Authorization")})
    @PostMapping("/page")
    public PageInfo<AnimalType> selectByPage(@RequestBody AnimalTypePageReq pageReq) {
        return animalTypeService.selectByPage(pageReq);
    }

    @Operation(summary = "删除动物类型", security = {@SecurityRequirement(name = "Authorization")})
    @GetMapping("/delete")
    @LogRecord(model = "动物类型", method = "删除动物类型")
    public ResponseInfo<Boolean> deleteAnimalType(@RequestParam("id") Integer id) {
        return ResponseInfo.ok(animalTypeService.deleteAnimalType(id));
    }
}
