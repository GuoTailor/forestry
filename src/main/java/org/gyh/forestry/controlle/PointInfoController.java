package org.gyh.forestry.controlle;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.gyh.forestry.aspect.LogRecord;
import org.gyh.forestry.dto.PageInfo;
import org.gyh.forestry.dto.ResponseInfo;
import org.gyh.forestry.dto.req.AddPointInfo;
import org.gyh.forestry.dto.req.PointInfoPageReq;
import org.gyh.forestry.dto.req.UpdatePointInfo;
import org.gyh.forestry.dto.resp.PointInfoResp;
import org.gyh.forestry.service.PointInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * create by GYH on 2024/5/23
 */
@Tag(name = "点位管理")
@RestController
@RequestMapping("/point")
public class PointInfoController {
    @Autowired
    private PointInfoService pointInfoService;

    @PostMapping("/add")
    @Operation(summary = "添加点", security = {@SecurityRequirement(name = "Authorization")})
    @LogRecord(model = "点位管理", method = "添加点")
    public ResponseInfo<PointInfoResp> save(@RequestBody @Valid AddPointInfo pointInfo) {
        return ResponseInfo.ok(pointInfoService.save(pointInfo));
    }

    @GetMapping("/delete")
    @Operation(summary = "删除点", security = {@SecurityRequirement(name = "Authorization")})
    @LogRecord(model = "点位管理", method = "删除点")
    public ResponseInfo<Boolean> deleteById(@RequestParam("id") Integer id) {
        return ResponseInfo.ok(pointInfoService.deleteById(id));
    }

    @PostMapping("/update")
    @Operation(summary = "更新点", security = {@SecurityRequirement(name = "Authorization")})
    @LogRecord(model = "点位管理", method = "更新点")
    public ResponseInfo<Void> updateById(@RequestBody @Valid UpdatePointInfo pointInfo) {
        pointInfoService.updateById(pointInfo);
        return ResponseInfo.ok();
    }

    @PostMapping("/select")
    @Operation(summary = "查询点信息", security = {@SecurityRequirement(name = "Authorization")})
    public ResponseInfo<PointInfoResp> selectById(@RequestParam("id") Integer id) {
        return ResponseInfo.ok(pointInfoService.selectById(id));
    }

    @PostMapping("/page")
    @Operation(summary = "查询点列表信息", security = {@SecurityRequirement(name = "Authorization")})
    public PageInfo<PointInfoResp> selectByPage(@RequestBody PointInfoPageReq pageReq) {
        return pointInfoService.selectByPage(pageReq);
    }

    @PostMapping("/all")
    @Operation(summary = "获取全部点列表信息", security = {@SecurityRequirement(name = "Authorization")})
    public ResponseInfo<List<PointInfoResp>> getAll() {
        return ResponseInfo.ok(pointInfoService.getAll());
    }
}
