package org.gyh.forestry.mapper;

import org.gyh.forestry.domain.Moxing;
import org.gyh.forestry.dto.resp.FireRankProportion;

import java.util.List;

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

    List<FireRankProportion> selectFireRankProportion(String name);

    int updateByPrimaryKeySelective(Moxing record);

    int updateByPrimaryKey(Moxing record);
}
