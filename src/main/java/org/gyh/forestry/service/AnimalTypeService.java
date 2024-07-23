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
import org.gyh.forestry.mapper.AnimalManageMapper;
import org.gyh.forestry.mapper.AnimalTypeMapper;
import org.springframework.stereotype.Service;

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
    @Resource
    private AnimalManageMapper animalManageMapper;

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
        animalType.setId(updateAnimalTypeReq.getId());
        animalType.setEnable(updateAnimalTypeReq.getEnable());
        animalType.setName(updateAnimalTypeReq.getName());
        animalTypeMapper.updateByPrimaryKeySelective(animalType);
        if (Boolean.FALSE.equals(updateAnimalTypeReq.getEnable())) {
            log.info("动物类型{}禁用", animalType.getName());
            animalManageMapper.disableByTypeId(animalType.getId());
        }
        return animalType;
    }

    public PageInfo<AnimalType> selectByPage(AnimalTypePageReq pageReq) {
        try (Page<OperationRecord> page = PageHelper.startPage(pageReq.getPage(), pageReq.getPageSize())) {
            List<AnimalType> all = animalTypeMapper.findByPage(pageReq);
            return PageInfo.ok(page.getTotal(), pageReq, all);
        }
    }

    public List<AnimalType> selectAll() {
        return animalTypeMapper.findByPage(new AnimalTypePageReq());
    }

    public boolean deleteAnimalType(Integer id) {
        return animalTypeMapper.deleteByPrimaryKey(id) == 1;
    }
}
