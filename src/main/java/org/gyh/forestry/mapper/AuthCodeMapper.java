package org.gyh.forestry.mapper;

import org.gyh.forestry.domain.AuthCode;

import java.util.List;

/**
 * create by GYH on 2024/5/18
 */
public interface AuthCodeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AuthCode record);

    int insertSelective(AuthCode record);

    AuthCode selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AuthCode record);

    int updateByPrimaryKey(AuthCode record);

    List<AuthCode> selectAll();
}
