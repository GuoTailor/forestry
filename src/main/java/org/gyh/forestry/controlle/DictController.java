package org.gyh.forestry.controlle;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.gyh.forestry.aspect.LogRecord;
import org.gyh.forestry.domain.Dict;
import org.gyh.forestry.dto.PageInfo;
import org.gyh.forestry.dto.ResponseInfo;
import org.gyh.forestry.dto.req.AddDictReq;
import org.gyh.forestry.dto.req.DictPageReq;
import org.gyh.forestry.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * create by GYH on 2024/7/23
 */
@Tag(name = "字典")
@RestController
@RequestMapping("/dict")
public class DictController {
    @Autowired
    private DictService dictService;

    @PostMapping("/add")
    @LogRecord(model = "字典", method = "添加字典")
    @Operation(summary = "添加字典", security = {@SecurityRequirement(name = "Authorization")})
    public ResponseInfo<Dict> addDict(@RequestBody AddDictReq addDictReq) {
        return ResponseInfo.ok(dictService.addDict(addDictReq));
    }

    @GetMapping("/delete")
    @LogRecord(model = "字典", method = "删除字典")
    @Operation(summary = "删除字典", security = {@SecurityRequirement(name = "Authorization")})
    public ResponseInfo<Boolean> deleteDict(@RequestParam("key") String key) {
        return ResponseInfo.ok(dictService.deleteDict(key));
    }

    @PostMapping("/update")
    @LogRecord(model = "字典", method = "更新字典")
    @Operation(summary = "更新字典", security = {@SecurityRequirement(name = "Authorization")})
    public ResponseInfo<Boolean> updateDict(@RequestBody Dict dict) {
        return ResponseInfo.ok(dictService.updateDict(dict));
    }

    @GetMapping("/get")
    @Operation(summary = "获取字典", security = {@SecurityRequirement(name = "Authorization")})
    public ResponseInfo<Dict> getDict(@RequestParam("key") String key) {
        return ResponseInfo.ok(dictService.getDict(key));
    }

    @PostMapping("/page")
    @Operation(summary = "分页获取字典", security = {@SecurityRequirement(name = "Authorization")})
    public PageInfo<Dict> findByPage(@RequestBody DictPageReq pageReq) {
        return dictService.findByPage(pageReq);
    }
}
