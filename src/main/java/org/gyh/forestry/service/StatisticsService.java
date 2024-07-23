package org.gyh.forestry.service;

import jakarta.annotation.Resource;
import org.gyh.forestry.dto.req.StatisticAnimalTypeReq;
import org.gyh.forestry.dto.resp.GeneralInforResp;
import org.gyh.forestry.dto.resp.StatisticAnimalTypeResp;
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
}
