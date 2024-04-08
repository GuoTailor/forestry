package org.gyh.forestry.mapper;

import org.gyh.forestry.domain.PointUser;

/**
 * create by GYH on 2024/4/6
 */
public interface PointUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PointUser record);

    int insertSelective(PointUser record);

    PointUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PointUser record);

    int updateByPrimaryKey(PointUser record);
}
