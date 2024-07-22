package org.gyh.forestry.service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.gyh.forestry.domain.Dict;
import org.gyh.forestry.dto.req.AddDictReq;
import org.gyh.forestry.mapper.DictMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * create by GYH on 2024/7/22
 */
@Slf4j
@Service
public class DictService {
    @Resource
    private DictMapper dictMapper;


    public Dict addDist(AddDictReq addDictReq) {
        Dict dict = new Dict();
        BeanUtils.copyProperties(addDictReq, dict);
        dictMapper.insertSelective(dict);
        return dict;
    }
}
