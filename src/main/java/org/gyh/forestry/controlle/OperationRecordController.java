package org.gyh.forestry.controlle;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.gyh.forestry.aspect.LogRecord;
import org.gyh.forestry.domain.OperationRecord;
import org.gyh.forestry.dto.PageInfo;
import org.gyh.forestry.dto.req.OperationRecordPage;
import org.gyh.forestry.service.OperationRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * create by GYH on 2024/3/30
 */
@Tag(name = "日志")
@RestController
@RequestMapping("/log")
public class OperationRecordController {
    @Autowired
    private OperationRecordService operationRecordService;

    @Operation(summary = "分页查询日志", security = {@SecurityRequirement(name = "Authorization")})
    @PostMapping("/page")
    @LogRecord(model = "日志", method = "分页查询日志")
    public PageInfo<OperationRecord> selectByPage(@RequestBody OperationRecordPage pageReq) {
        return operationRecordService.selectByPage(pageReq);
    }

    @Operation(summary = "获取详情", security = {@SecurityRequirement(name = "Authorization")})
    @PostMapping("")
    public OperationRecord selectById(@RequestParam Integer id) {
        return operationRecordService.selectById(id);
    }
}
