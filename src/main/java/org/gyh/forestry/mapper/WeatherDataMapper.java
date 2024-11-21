package org.gyh.forestry.mapper;

import org.gyh.forestry.domain.WeatherData;

/**
 * create by GYH on 2024/11/21
 */
public interface WeatherDataMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WeatherData record);

    int insertSelective(WeatherData record);

    WeatherData selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WeatherData record);

    int updateByPrimaryKey(WeatherData record);

    WeatherData selectByName(String name);
}
