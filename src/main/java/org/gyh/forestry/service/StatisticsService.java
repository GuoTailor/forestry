package org.gyh.forestry.service;

import jakarta.annotation.Resource;
import org.gyh.forestry.dto.req.StatisticAnimalTypeReq;
import org.gyh.forestry.dto.resp.*;
import org.gyh.forestry.mapper.AnimalManageMapper;
import org.gyh.forestry.mapper.AnimalRecognitionMapper;
import org.gyh.forestry.mapper.AnimalTypeMapper;
import org.gyh.forestry.mapper.PointInfoMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * create by GYH on 2024/7/23
 */
@Service
public class StatisticsService {
    @Resource
    private PointInfoMapper pointInfoMapper;
    @Resource
    private AnimalManageMapper animalManageMapper;
    @Resource
    private AnimalTypeMapper animalTypeMapper;
    @Resource
    private AnimalRecognitionMapper animalRecognitionMapper;

    /**
     * 总览
     */
    public GeneralInforResp generalInfor() {
        return new GeneralInforResp(
                pointInfoMapper.countByType(null),
                animalManageMapper.count(),
                animalTypeMapper.count()
        );
    }

    /**
     * 统计种类数量
     */
    public List<StatisticAnimalTypeResp> statisticAnimalType(StatisticAnimalTypeReq req) {
        return animalRecognitionMapper.statisticAnimalType(req);
    }

    /**
     * 统计每月动物数量
     */
    public List<StatisticAnimalByMonthResp> statisticAnimalByMonth(StatisticAnimalTypeReq req) {
        return animalRecognitionMapper.statisticAnimalByMonth(req);
    }

    /**
     * 统计动物出现频次
     */
    public List<StatisticAnimalByCount> statisticAnimalByCount(StatisticAnimalTypeReq req) {
        return animalRecognitionMapper.statisticAnimalByCount(req);
    }

    /**
     * 统计动物出现比例
     */
    public List<StatisticAnimalProportion> statisticAnimalProportion() {
        List<StatisticAnimalTypeResp> statisticAnimalTypeResp = animalRecognitionMapper.statisticAnimalType(null);
        double count = statisticAnimalTypeResp.stream().mapToLong(StatisticAnimalTypeResp::getCount).sum();
        return statisticAnimalTypeResp.stream().map(it -> {
            StatisticAnimalProportion statisticAnimalProportion = new StatisticAnimalProportion();
            statisticAnimalProportion.setName(it.getName());
            statisticAnimalProportion.setProportion(it.getCount() / count);
            return statisticAnimalProportion;
        }).toList();
    }

}
