package org.gyh.forestry.mapper;

import org.gyh.forestry.domain.Pachong;

import java.util.List;

/**
 * create by GYH on 2024/10/7
 */
public interface PachongMapper {
    int insert(Pachong record);

    int insertSelective(Pachong record);

    List<Pachong> selectAll();
}
