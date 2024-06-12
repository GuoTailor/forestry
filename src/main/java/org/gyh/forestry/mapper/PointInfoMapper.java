package org.gyh.forestry.mapper;

import org.gyh.forestry.domain.PointInfo;
import org.gyh.forestry.dto.req.PointInfoPageReq;

import java.util.List;

/**
 * create by GYH on 2024/5/29
 */
public interface PointInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PointInfo record);

    int insertSelective(PointInfo record);

    PointInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PointInfo record);

    int updateByPrimaryKey(PointInfo record);

    List<PointInfo> selectByPage(PointInfoPageReq pageReq);
}