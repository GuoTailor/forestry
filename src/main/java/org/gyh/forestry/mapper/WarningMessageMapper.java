package org.gyh.forestry.mapper;

import org.gyh.forestry.domain.WarningMessage;
import java.util.List;

/**
 * create by GYH on 2025/12/3
 */
public interface WarningMessageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WarningMessage record);

    int insertOrUpdate(WarningMessage record);

    int insertOrUpdateSelective(WarningMessage record);

    int insertSelective(WarningMessage record);

    WarningMessage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WarningMessage record);

    int updateByPrimaryKey(WarningMessage record);
    
    /**
     * 查询所有预警消息
     *
     * @return 预警消息列表
     */
    List<WarningMessage> selectAll();
}