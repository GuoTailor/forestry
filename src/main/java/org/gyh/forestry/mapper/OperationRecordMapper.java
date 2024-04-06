package org.gyh.forestry.mapper;

import org.gyh.forestry.domain.OperationRecord;
import org.gyh.forestry.dto.req.OperationRecordPage;

import java.util.List;

/**
 * create by GYH on 2024/3/27
 */
public interface OperationRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OperationRecord record);

    int insertSelective(OperationRecord record);

    OperationRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OperationRecord record);

    int updateByPrimaryKey(OperationRecord record);

    List<OperationRecord> selectBySelective(OperationRecordPage page);
}
