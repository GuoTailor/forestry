package org.gyh.forestry.controlle;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.gyh.forestry.aspect.LogRecord;
import org.gyh.forestry.domain.AreaInfo;
import org.gyh.forestry.dto.PageInfo;
import org.gyh.forestry.dto.ResponseInfo;
import org.gyh.forestry.dto.req.AddAreaInfo;
import org.gyh.forestry.dto.req.AreaInfoPageReq;
import org.gyh.forestry.dto.req.UpdateAreaInfo;
import org.gyh.forestry.dto.resp.AreaInfoResp;
import org.gyh.forestry.service.AreaInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * create by GYH on 2024/8/13
 */
@Tag(name = "区域")
@RestController
@RequestMapping("/area")
public class AreaInfoController {
    @Autowired
    private AreaInfoService areaInfoService;

    @PostMapping("/add")
    @Operation(summary = "添加区域", security = {@SecurityRequirement(name = "Authorization")})
    @LogRecord(model = "区域", method = "添加区域")
    public ResponseInfo<AreaInfo> addAreaInfo(@RequestBody @Valid AddAreaInfo addAreaInfo) {
        return ResponseInfo.ok(areaInfoService.addAreaInfo(addAreaInfo));
    }

    @PostMapping("/update")
    @Operation(summary = "更新区域", security = {@SecurityRequirement(name = "Authorization")})
    @LogRecord(model = "区域", method = "更新区域")
    public ResponseInfo<Void> updateAreaInfo(@RequestBody @Valid UpdateAreaInfo updateAreaInfo) {
        areaInfoService.updateAreaInfo(updateAreaInfo);
        return ResponseInfo.ok();
    }

    @PostMapping("/page")
    @Operation(summary = "分页查询区域", security = {@SecurityRequirement(name = "Authorization")})
    public PageInfo<AreaInfoResp> selectPage(@RequestBody AreaInfoPageReq pageReq) {
        return areaInfoService.selectPage(pageReq);
    }

    @GetMapping("/delete")
    @Operation(summary = "删除区域", security = {@SecurityRequirement(name = "Authorization")})
    @LogRecord(model = "区域", method = "删除区域")
    public ResponseInfo<Void> delete(@RequestParam("id") Integer id) {
        areaInfoService.delete(id);
        return ResponseInfo.ok();
    }

}
