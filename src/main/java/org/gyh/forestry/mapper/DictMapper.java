package org.gyh.forestry.mapper;

import org.gyh.forestry.domain.Dict;
import org.gyh.forestry.dto.req.DictPageReq;

import java.util.List;

/**
 * create by GYH on 2024/7/22
 */
public interface DictMapper {
    int deleteByPrimaryKey(Integer id);

    int deleteByKey(String key);

    int insert(Dict record);

    int insertSelective(Dict record);

    Dict selectByPrimaryKey(Integer id);

    Dict selectByKey(String key);

    List<Dict> findByPage(DictPageReq pageReq);

    int updateByPrimaryKeySelective(Dict record);

    int updateByPrimaryKey(Dict record);
}
