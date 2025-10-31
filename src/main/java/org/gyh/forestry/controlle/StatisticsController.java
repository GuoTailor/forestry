package org.gyh.forestry.controlle;

import com.alibaba.excel.EasyExcel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.gyh.forestry.domain.Pachong;
import org.gyh.forestry.dto.ResponseInfo;
import org.gyh.forestry.dto.req.StatisticAnimalTypeReq;
import org.gyh.forestry.dto.resp.*;
import org.gyh.forestry.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * create by GYH on 2024/7/24
 */
@Tag(name = "统计")
@RestController
@RequestMapping("/statistics")
public class StatisticsController {
    @Autowired
    private StatisticsService statisticsService;

    /**
     * 总览
     */
    @PostMapping("/general")
    @Operation(summary = "总览", security = {@SecurityRequirement(name = "Authorization")})
    public ResponseInfo<GeneralInforResp> generalInfor() {
        return ResponseInfo.ok(statisticsService.generalInfor());
    }

    /**
     * 统计种类数量
     */
    @PostMapping("/animal/type")
    @Operation(summary = "统计种类数量", security = {@SecurityRequirement(name = "Authorization")})
    public ResponseInfo<List<StatisticAnimalTypeResp>> statisticAnimalType(@RequestBody StatisticAnimalTypeReq req) {
        return ResponseInfo.ok(statisticsService.statisticAnimalType(req));
    }

    /**
     * 统计种类数量，导出为excel
     */
    @PostMapping("/animal/type/download")
    @Operation(summary = "统计种类数量，导出为excel", security = {@SecurityRequirement(name = "Authorization")})
    public void download(@RequestBody StatisticAnimalTypeReq req, HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("已识别动物种类类名", StandardCharsets.UTF_8).replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), StatisticAnimalTypeResp.class).sheet().doWrite(statisticsService.statisticAnimalType(req));
    }

    /**
     * 统计每月动物数量
     */
    @PostMapping("/animal/month")
    @Operation(summary = "统计每月动物数量", security = {@SecurityRequirement(name = "Authorization")})
    public ResponseInfo<List<StatisticAnimalByMonthResp>> statisticAnimalByMonth(@RequestBody StatisticAnimalTypeReq req) {
        return ResponseInfo.ok(statisticsService.statisticAnimalByMonth(req));
    }

    /**
     * 统计动物出现频次
     */
    @PostMapping("/animal/count")
    @Operation(summary = "统计动物出现频次", security = {@SecurityRequirement(name = "Authorization")})
    public ResponseInfo<List<StatisticAnimalByCount>> statisticAnimalByCount(@RequestBody StatisticAnimalTypeReq req) {
        return ResponseInfo.ok(statisticsService.statisticAnimalByCount(req));
    }

    /**
     * 统计动物出现比例
     */
    @PostMapping("/animal/proportion")
    @Operation(summary = "统计动物出现比例", security = {@SecurityRequirement(name = "Authorization")})
    public ResponseInfo<List<StatisticAnimalProportion>> statisticAnimalProportion() {
        return ResponseInfo.ok(statisticsService.statisticAnimalProportion());
    }

    /**
     * 森林火险统计
     */
    @PostMapping("/forest/fire")
    @Operation(summary = "森林火险统计", security = {@SecurityRequirement(name = "Authorization")})
    public ResponseInfo<List<ForestFire>> forestFire() {
        return ResponseInfo.ok(statisticsService.forestFire());
    }

    @GetMapping("/forest/fire/proportion")
    @Operation(summary = "查询火险等级占比", security = {@SecurityRequirement(name = "Authorization")})
    public ResponseInfo<List<FireRankProportion>> fireRankProportion(@RequestParam("name") String name) {
        return ResponseInfo.ok(statisticsService.fireRankProportion(name));
    }

    @GetMapping("/forest/fire/pachong")
    @Operation(summary = "查询火险预报", security = {@SecurityRequirement(name = "Authorization")})
    public ResponseInfo<List<Pachong>> pachongList() {
        return ResponseInfo.ok(statisticsService.pachongList());
    }

    @GetMapping("/animal/area")
    @Operation(summary = "统计不同区域的动物种类占比", security = {@SecurityRequirement(name = "Authorization")})
    public ResponseInfo<List<AreaAnimalResp>> animalByArea() {
        return ResponseInfo.ok(statisticsService.animalByArea());
    }
}
