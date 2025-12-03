package org.gyh.forestry.controlle;

import com.alibaba.excel.EasyExcel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.gyh.forestry.dto.PageInfo;
import org.gyh.forestry.dto.PageReq;
import org.gyh.forestry.dto.ResponseInfo;
import org.gyh.forestry.domain.WarningMessage;
import org.gyh.forestry.service.WarningMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 预警消息控制器
 * create by GYH on 2025/12/3
 */
@Tag(name = "预警消息")
@RestController
@RequestMapping("/warning")
public class WarningMessageController {

    @Autowired
    private WarningMessageService warningMessageService;

    /**
     * 分页查询预警消息
     */
    @PostMapping("/page")
    @Operation(summary = "分页查询预警消息", security = {@SecurityRequirement(name = "Authorization")})
    public PageInfo<WarningMessage> selectByPage(@RequestBody PageReq pageReq) {
        return warningMessageService.selectByPage(pageReq);
    }

    /**
     * 导出所有预警消息到Excel
     */
    @GetMapping("/export")
    @Operation(summary = "导出所有预警消息到Excel", security = {@SecurityRequirement(name = "Authorization")})
    public void exportToExcel(HttpServletResponse response) throws IOException {
        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 设置文件名
        String fileName = URLEncoder.encode("预警消息数据", StandardCharsets.UTF_8).replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        // 写入Excel
        EasyExcel.write(response.getOutputStream(), WarningMessageService.WarningMessageExcel.class)
                .sheet("预警消息")
                .doWrite(warningMessageService.exportAllToExcel());
    }
}