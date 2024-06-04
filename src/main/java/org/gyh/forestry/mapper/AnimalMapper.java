package org.gyh.forestry.mapper;

import org.gyh.forestry.domain.Animal;
import org.gyh.forestry.dto.req.AnimalPageReq;

import java.util.List;

/**
 * create by GYH on 2024/6/4
 */
public interface AnimalMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Animal record);

    int insertSelective(Animal record);

    Animal selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Animal record);

    int updateByPrimaryKey(Animal record);

    List<Animal> selectAll();

    List<Animal> selectByQuery(AnimalPageReq pageReq);
}