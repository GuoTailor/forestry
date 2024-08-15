package org.gyh.forestry.mapper;

import org.gyh.forestry.domain.Moxing;

/**
 * create by GYH on 2024/8/14
 */
public interface MoxingMapper {
    int deleteByPrimaryKey(Integer gid);

    int insert(Moxing record);

    int insertSelective(Moxing record);

    Moxing selectByPrimaryKey(Integer gid);

    Moxing selectByName(String name);

    Short selectLaveByName(String name);

    int updateByPrimaryKeySelective(Moxing record);

    int updateByPrimaryKey(Moxing record);
}
