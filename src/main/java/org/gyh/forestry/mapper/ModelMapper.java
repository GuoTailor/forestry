package org.gyh.forestry.mapper;

import org.gyh.forestry.domain.Model;

/**
 * create by GYH on 2024/5/23
 */
public interface ModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Model record);

    int insertSelective(Model record);

    Model selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Model record);

    int updateByPrimaryKey(Model record);
}
