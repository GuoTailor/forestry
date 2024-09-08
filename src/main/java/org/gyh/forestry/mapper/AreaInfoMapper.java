package org.gyh.forestry.mapper;

import org.gyh.forestry.domain.AreaInfo;
import org.gyh.forestry.dto.req.AreaInfoPageReq;
import org.gyh.forestry.dto.resp.AreaInfoResp;

import java.util.List;

/**
 * create by GYH on 2024/9/8
 */
public interface AreaInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AreaInfo record);

    int insertSelective(AreaInfo record);

    AreaInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AreaInfo record);

    int updateByPrimaryKey(AreaInfo record);

    AreaInfo findByName(String name);

    List<AreaInfoResp> selectByPage(AreaInfoPageReq pageReq);

    List<AreaInfo> selectAll();
}
