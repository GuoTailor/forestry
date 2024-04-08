package org.gyh.forestry.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.gyh.forestry.domain.Animal;
import org.gyh.forestry.dto.PageInfo;
import org.gyh.forestry.dto.PageReq;
import org.gyh.forestry.dto.resp.AnimalResp;
import org.gyh.forestry.dto.resp.JsonPoint;
import org.gyh.forestry.mapper.AnimalMapper;
import org.springframework.beans.BeanUtils;
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
     */
    public void addAnimal(Animal animal) {
        animalMapper.insertSelective(animal);
        log.info("插入一个{}", animal.getId());
    }

    public AnimalResp selectById(Integer id) {
        Animal animal = animalMapper.selectByPrimaryKey(id);
        AnimalResp resp = new AnimalResp();
        BeanUtils.copyProperties(animal, resp);
        JsonPoint jsonPoint = new JsonPoint(animal.getLocation());
        resp.setLocation(jsonPoint);
        return resp;
    }

    public PageInfo<AnimalResp> selectByPage(PageReq pageReq) {
        Page<Animal> page = PageHelper.startPage(pageReq.getPage(), pageReq.getPageSize());
        List<Animal> animals = animalMapper.selectAll();
        List<AnimalResp> list = animals.stream().map(it -> {
            AnimalResp resp = new AnimalResp();
            BeanUtils.copyProperties(it, resp);
            JsonPoint jsonPoint = new JsonPoint(it.getLocation());
            resp.setLocation(jsonPoint);
            return resp;
        }).toList();
        return PageInfo.ok(page.getTotal(), pageReq, list);
    }
}
