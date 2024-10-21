package org.gyh.forestry.mapper;

import org.gyh.forestry.domain.ScenicSpot;

import java.util.List;

/**
 * create by GYH on 2024/10/20
 */
public interface ScenicSpotMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ScenicSpot record);

    int insertSelective(ScenicSpot record);

    ScenicSpot selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ScenicSpot record);

    int updateByPrimaryKey(ScenicSpot record);

    List<ScenicSpot> selectAll();
}
