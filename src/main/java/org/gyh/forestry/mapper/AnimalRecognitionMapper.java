package org.gyh.forestry.mapper;

import org.gyh.forestry.domain.AnimalRecognition;
import org.gyh.forestry.dto.req.AnimalRecognitionPageReq;

import java.util.List;

/**
 * create by GYH on 2024/6/15
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
}