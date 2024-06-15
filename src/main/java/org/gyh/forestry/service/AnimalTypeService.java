package org.gyh.forestry.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.gyh.forestry.domain.AnimalType;
import org.gyh.forestry.domain.OperationRecord;
import org.gyh.forestry.dto.PageInfo;
import org.gyh.forestry.dto.req.AddAnimalTypeReq;
import org.gyh.forestry.dto.req.AnimalTypePageReq;
import org.gyh.forestry.dto.req.UpdateAnimalTypeReq;
import org.gyh.forestry.dto.resp.UserInfo;
import org.gyh.forestry.mapper.AnimalTypeMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * create by GYH on 2024/6/15
 */
@Slf4j
@Service
public class AnimalTypeService {
    @Resource
    private AnimalTypeMapper animalTypeMapper;

    /**
     * 添加动物类型
     */
    public AnimalType addAnimalType(AddAnimalTypeReq req) {
        AnimalType animalType = new AnimalType();
        animalType.setName(req.getName());
        animalType.setEnable(true);
        animalType.setCreateTime(LocalDateTime.now());
        animalTypeMapper.insertSelective(animalType);
        return animalType;
    }

    /**
     * 修改动物类型
     */
    public AnimalType updateAnimalType(UpdateAnimalTypeReq updateAnimalTypeReq) {
        AnimalType animalType = new AnimalType();
        if (updateAnimalTypeReq.getEnable() != null) {
            animalType.setEnable(updateAnimalTypeReq.getEnable());
        }
        if (StringUtils.hasLength(updateAnimalTypeReq.getName())) {
            animalType.setName(updateAnimalTypeReq.getName());
        }
        animalTypeMapper.updateByPrimaryKeySelective(animalType);
        return animalType;
    }

    public PageInfo<AnimalType> selectByPage(AnimalTypePageReq pageReq) {
        try (Page<OperationRecord> page = PageHelper.startPage(pageReq.getPage(), pageReq.getPageSize())) {
            List<AnimalType> all = animalTypeMapper.findByPage(pageReq);
            return PageInfo.ok(page.getTotal(), pageReq, all);
        }
    }

    public boolean deleteAnimalType(Integer id) {
        return animalTypeMapper.deleteByPrimaryKey(id) == 1;
    }
}
