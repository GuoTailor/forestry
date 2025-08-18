package org.gyh.forestry.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.gyh.forestry.domain.Dict;
import org.gyh.forestry.dto.PageInfo;
import org.gyh.forestry.dto.req.AddDictReq;
import org.gyh.forestry.dto.req.DictPageReq;
import org.gyh.forestry.mapper.DictMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * create by GYH on 2024/7/22
 */
@Slf4j
@Service
public class DictService {
    @Resource
    private DictMapper dictMapper;

    /**
     * 添加字典
     */
    public Dict addDict(AddDictReq addDictReq) {
        if (!addDictReq.getType().parse(addDictReq.getKey())) {
            throw new RuntimeException("字典类型错误");
        }
        Dict dict = new Dict();
        BeanUtils.copyProperties(addDictReq, dict);
        dictMapper.insertSelective(dict);
        return dict;
    }

    /**
     * 删除字典
     */
    public boolean deleteDict(String key) {
        return dictMapper.deleteByKey(key) > 0;
    }

    /**
     * 更新字典
     */
    public boolean updateDict(Dict dict) {
        return dictMapper.updateByPrimaryKeySelective(dict) > 0;
    }

    /**
     * 获取字典
     */
    public Dict getDict(String key) {
        return dictMapper.selectByKey(key);
    }

    /**
     * 分页获取字典
     */
    public PageInfo<Dict> findByPage(DictPageReq pageReq) {
        try (Page<Dict> page = PageHelper.startPage(pageReq.getPage(), pageReq.getPageSize())) {
            List<Dict> all = dictMapper.findByPage(pageReq);
            return PageInfo.ok(page.getTotal(), pageReq, all);
        }
    }
}
