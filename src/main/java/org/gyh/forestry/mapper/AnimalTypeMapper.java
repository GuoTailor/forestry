package org.gyh.forestry.mapper;

import org.gyh.forestry.domain.AnimalType;
import org.gyh.forestry.dto.req.AnimalTypePageReq;

import java.util.List;

/**
 * create by GYH on 2024/6/15
 */
public interface AnimalTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AnimalType record);

    int insertSelective(AnimalType record);

    AnimalType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AnimalType record);

    int updateByPrimaryKey(AnimalType record);

    List<AnimalType> findByPage(AnimalTypePageReq pageReq);

    long count();
}