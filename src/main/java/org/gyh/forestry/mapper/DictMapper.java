package org.gyh.forestry.mapper;

import org.gyh.forestry.domain.Dict;

/**
 * create by GYH on 2024/7/22
 */
public interface DictMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Dict record);

    int insertSelective(Dict record);

    Dict selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Dict record);

    int updateByPrimaryKey(Dict record);
}
