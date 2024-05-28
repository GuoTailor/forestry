package org.gyh.forestry.mapper;

import org.gyh.forestry.domain.Model;
import org.gyh.forestry.dto.req.ModelPageReq;
import org.gyh.forestry.dto.resp.ModelResp;

import java.util.List;

/**
 * create by GYH on 2024/5/23
 */
public interface ModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Model record);

    int insertSelective(Model record);

    Model selectByPrimaryKey(Integer id);

    Model selectByEnable();

    List<ModelResp> findByPage(ModelPageReq req);

    int updateByPrimaryKeySelective(Model record);

    int updateByPrimaryKey(Model record);
}
