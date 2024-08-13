package org.gyh.forestry.mapper;

import org.gyh.forestry.domain.AreaInfo;
import org.gyh.forestry.dto.req.AreaInfoPageReq;
import org.gyh.forestry.dto.resp.AreaInfoResp;

import java.util.List;

/**
 * create by GYH on 2024/8/13
 */
public interface AreaInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AreaInfo record);

    int insertSelective(AreaInfo record);

    AreaInfo findByName(String name);

    AreaInfo selectByPrimaryKey(Integer id);

    List<AreaInfoResp> selectByPage(AreaInfoPageReq pageReq);

    int updateByPrimaryKeySelective(AreaInfo record);

    int updateByPrimaryKey(AreaInfo record);
}
