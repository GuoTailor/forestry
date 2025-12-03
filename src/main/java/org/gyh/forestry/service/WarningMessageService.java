package org.gyh.forestry.service;

import com.alibaba.excel.annotation.ExcelProperty;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.gyh.forestry.domain.WarningMessage;
import org.gyh.forestry.dto.PageInfo;
import org.gyh.forestry.dto.PageReq;
import org.gyh.forestry.mapper.WarningMessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * create by GYH on 2025/12/3
 */
@Slf4j
@Service
public class WarningMessageService {
    
    @Autowired
    private WarningMessageMapper warningMessageMapper;
    
    /**
     * 分页查询预警消息
     *
     * @param pageReq 分页参数
     * @return 分页结果
     */
    public PageInfo<WarningMessage> selectByPage(PageReq pageReq) {
        try (Page<WarningMessage> page = PageHelper.startPage(pageReq.getPage(), pageReq.getPageSize())) {
            List<WarningMessage> warningMessages = warningMessageMapper.selectAll();
            return PageInfo.ok(page.getTotal(), pageReq, warningMessages);
        }
    }
    
    /**
     * 导出所有预警消息到Excel
     *
     * @return 预警消息列表
     */
    public List<WarningMessageExcel> exportAllToExcel() {
        List<WarningMessage> warningMessages = warningMessageMapper.selectAll();
        return warningMessages.stream().map(warningMessage -> {
            WarningMessageExcel excel = new WarningMessageExcel();
            excel.setId(warningMessage.getId());
            excel.setMsg(warningMessage.getMsg());
            excel.setCreateTime(warningMessage.getCreateTime());
            return excel;
        }).toList();
    }
    
    /**
     * 预警消息Excel导出数据类
     */
    @Data
    public static class WarningMessageExcel {
        @ExcelProperty("ID")
        private Integer id;
        
        @ExcelProperty("消息内容")
        private String msg;
        
        @ExcelProperty("创建时间")
        private Date createTime;
    }
}