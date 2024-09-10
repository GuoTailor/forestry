package org.gyh.forestry.service;

import jakarta.annotation.Resource;
import org.gyh.forestry.domain.AreaInfo;
import org.gyh.forestry.dto.req.StatisticAnimalTypeReq;
import org.gyh.forestry.dto.resp.*;
import org.gyh.forestry.mapper.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    @Resource
    private AreaInfoMapper areaInfoMapper;
    @Resource
    private MoxingMapper moxingMapper;

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

    /**
     * 统计森林火险的数据
     */
    public List<ForestFire> forestFire() {
        List<AreaInfo> areaInfos = areaInfoMapper.selectAll();
        return areaInfos.parallelStream().map(it -> {
            ForestFire forestFire = new ForestFire();
            forestFire.setName(it.getName());
            forestFire.setArea(it.getArea());
            forestFire.setTemperature(it.getTemperature());
            forestFire.setMoistureContent(it.getMoistureContent());
            forestFire.setHumidity(it.getHumidity());
            forestFire.setWindSpeed(it.getWindSpeed());
            forestFire.setWindDirection(it.getWindDirection());
            forestFire.setLevel(moxingMapper.selectLaveByName(it.getName()));
            return forestFire;
        }).toList();
    }

    /**
     * 查询火险等级占比
     * @param name 区域名字
     */
    public List<FireRankProportion> fireRankProportion(String name) {
        List<FireRankProportion> fireRankProportions = moxingMapper.selectFireRankProportion(name);
        int sum = 0;
        for (FireRankProportion fireRankProportion : fireRankProportions) {
            sum += fireRankProportion.getCount();
        }
        BigDecimal sumDecimal = new BigDecimal(sum);
        for (FireRankProportion fireRankProportion : fireRankProportions) {
            fireRankProportion.setProportion(new BigDecimal(fireRankProportion.getCount()).divide(sumDecimal, 4, RoundingMode.HALF_UP));
        }
        return fireRankProportions;
    }

}
