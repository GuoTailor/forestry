package org.gyh.forestry.mapper;

import org.apache.ibatis.annotations.Param;
import org.gyh.forestry.domain.PointInfo;
import org.gyh.forestry.dto.req.PointInfoPageReq;
import org.gyh.forestry.dto.resp.SelectDistance;
import org.gyh.forestry.enums.PointTypeEnum;

import java.util.List;

/**
 * create by GYH on 2024/10/20
 */
public interface PointInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PointInfo record);

    int insertSelective(PointInfo record);

    PointInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PointInfo record);

    int updateByPrimaryKey(PointInfo record);

    List<PointInfo> selectByPage(PointInfoPageReq pageReq);

    long countByType(@Param("type") PointTypeEnum type);

    SelectDistance selectDistance(@Param("x") double x, @Param("y") double y);
}
