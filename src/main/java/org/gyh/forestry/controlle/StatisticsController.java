package org.gyh.forestry.controlle;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.gyh.forestry.dto.ResponseInfo;
import org.gyh.forestry.dto.req.StatisticAnimalTypeReq;
import org.gyh.forestry.dto.resp.*;
import org.gyh.forestry.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
