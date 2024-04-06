package org.gyh.forestry.mapper;

import org.gyh.forestry.domain.PointInfo;

/**
 * create by GYH on 2024/4/6
 */
public interface PointInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PointInfo record);

    int insertSelective(PointInfo record);

    PointInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PointInfo record);

    int updateByPrimaryKey(PointInfo record);
}
