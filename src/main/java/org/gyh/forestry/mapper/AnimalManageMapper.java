package org.gyh.forestry.mapper;

import org.gyh.forestry.domain.AnimalManage;
import org.gyh.forestry.dto.req.AnimalManagePageReq;
import org.gyh.forestry.dto.resp.AnimalManageResp;

import java.util.List;

/**
 * create by GYH on 2024/6/17
 */
public interface AnimalManageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AnimalManage record);

    int insertSelective(AnimalManage record);

    AnimalManage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AnimalManage record);

    int updateByPrimaryKey(AnimalManage record);

    List<AnimalManageResp> selectByPage(AnimalManagePageReq pageReq);
}