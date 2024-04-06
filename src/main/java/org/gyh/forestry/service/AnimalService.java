package org.gyh.forestry.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.gyh.forestry.domain.Animal;
import org.gyh.forestry.dto.PageInfo;
import org.gyh.forestry.dto.PageReq;
import org.gyh.forestry.mapper.AnimalMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * create by GYH on 2024/3/24
 */
@Slf4j
@Service
public class AnimalService {
    @Resource
    private AnimalMapper animalMapper;

    /**
     * 添加
     * @param animal
     */
    public void addAnimal(Animal animal) {
        animalMapper.insertSelective(animal);
        log.info("插入一个{}", animal.getId());
    }

    public Animal selectById(Integer id) {
        return animalMapper.selectByPrimaryKey(id);
    }

    public PageInfo<Animal> selectByPage(PageReq pageReq) {
        Page<Animal> page = PageHelper.startPage(pageReq.getPage(), pageReq.getPageSize());
        List<Animal> animals = animalMapper.selectAll();
        return PageInfo.ok(page.getTotal(), pageReq, animals);
    }
}
