package org.gyh.forestry.mapper;

import net.postgis.jdbc.geometry.Point;
import org.apache.ibatis.annotations.Param;
import org.gyh.forestry.domain.AnimalRecognition;
import org.gyh.forestry.dto.req.AnimalRecognitionPageReq;
import org.gyh.forestry.dto.req.StatisticAnimalTypeReq;
import org.gyh.forestry.dto.resp.StatisticAnimalArea;
import org.gyh.forestry.dto.resp.StatisticAnimalByCount;
import org.gyh.forestry.dto.resp.StatisticAnimalByMonthResp;
import org.gyh.forestry.dto.resp.StatisticAnimalTypeResp;

import java.util.List;

/**
 * create by GYH on 2024/10/21
 */
public interface AnimalRecognitionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AnimalRecognition record);

    int insertSelective(AnimalRecognition record);

    AnimalRecognition selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AnimalRecognition record);

    int updateByPrimaryKey(AnimalRecognition record);

    List<AnimalRecognition> selectAll();

    List<AnimalRecognition> selectByQuery(AnimalRecognitionPageReq pageReq);

    List<StatisticAnimalTypeResp> statisticAnimalType(StatisticAnimalTypeReq req);

    List<StatisticAnimalByMonthResp> statisticAnimalByMonth(StatisticAnimalTypeReq req);

    List<StatisticAnimalByCount> statisticAnimalByCount(StatisticAnimalTypeReq req);

    List<StatisticAnimalArea> animalByArea();

    List<String> randomImage(String pointName);

    int selectRepeat(@Param("location") Point location, @Param("type") String type, @Param("name") String name, @Param("pic") String pic);
}
