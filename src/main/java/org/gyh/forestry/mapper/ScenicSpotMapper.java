package org.gyh.forestry.mapper;

import org.gyh.forestry.domain.ScenicSpot;
import org.gyh.forestry.dto.req.ScenicSpotPointPageReq;

import java.util.List;

/**
 * create by GYH on 2024/10/31
 */
public interface ScenicSpotMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ScenicSpot record);

    int insertSelective(ScenicSpot record);

    ScenicSpot selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ScenicSpot record);

    int updateByPrimaryKey(ScenicSpot record);

    List<ScenicSpot> selectAll();

    List<ScenicSpot> selectByName(ScenicSpotPointPageReq req);
}
