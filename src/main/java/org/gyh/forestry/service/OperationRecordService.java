package org.gyh.forestry.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.gyh.forestry.domain.OperationRecord;
import org.gyh.forestry.dto.PageInfo;
import org.gyh.forestry.dto.req.OperationRecordPage;
import org.gyh.forestry.mapper.OperationRecordMapper;
import org.springframework.stereotype.Service;

/**
 * create by GYH on 2024/3/27
 */
@Slf4j
@Service
public class OperationRecordService {
    @Resource
    private OperationRecordMapper operationRecordMapper;

    public void insert(OperationRecord operationRecord) {
        operationRecordMapper.insert(operationRecord);
    }

    public PageInfo<OperationRecord> selectByPage(OperationRecordPage pageReq) {
        Page<OperationRecord> page = PageHelper.startPage(pageReq.getPage(), pageReq.getPageSize());
        operationRecordMapper.selectBySelective(pageReq);
        return PageInfo.ok(page.getTotal(), pageReq, page.getResult());
    }

    public OperationRecord selectById(Integer id) {
        return operationRecordMapper.selectByPrimaryKey(id);
    }
}
