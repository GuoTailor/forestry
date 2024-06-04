package org.gyh.forestry.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.gyh.forestry.domain.Animal;
import org.gyh.forestry.domain.User;
import org.gyh.forestry.dto.JsonPoint;
import org.gyh.forestry.dto.PageInfo;
import org.gyh.forestry.dto.req.AddAnimalReq;
import org.gyh.forestry.dto.req.AnimalPageReq;
import org.gyh.forestry.dto.resp.AnimalResp;
import org.gyh.forestry.dto.resp.RecognitionResp;
import org.gyh.forestry.mapper.AnimalMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

/**
 * create by GYH on 2024/3/24
 */
@Slf4j
@Service
public class AnimalService {
    @Resource
    private AnimalMapper animalMapper;
    @Autowired
    private FileService fileService;

    /**
     * 图片识别
     */
    public List<RecognitionResp> recognition(List<MultipartFile> files) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = user.getId();
        log.info("1 {}", Thread.currentThread().isVirtual());
        return files.parallelStream().map(it -> {
            String path = fileService.uploadFile(userId, it);
            log.info("2 {}", Thread.currentThread().isVirtual());
            RecognitionResp resp = new RecognitionResp();
            resp.setPic(path);
            return resp;
        }).toList();
    }

    /**
     * 添加
     */
    public List<Animal> addAnimal(List<AddAnimalReq> animalReqs) {
        return animalReqs.stream().map(animalReq -> {
            Animal animal = new Animal();
            BeanUtils.copyProperties(animalReq, animal);
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            animal.setCreator(user.getUsername());
            animal.setCreateTime(LocalDateTime.now());
            animalMapper.insertSelective(animal);
            return animal;
        }).toList();
    }

    public AnimalResp selectById(Integer id) {
        Animal animal = animalMapper.selectByPrimaryKey(id);
        AnimalResp resp = new AnimalResp();
        BeanUtils.copyProperties(animal, resp);
        JsonPoint jsonPoint = new JsonPoint(animal.getLocation());
        resp.setLocation(jsonPoint);
        return resp;
    }

    public PageInfo<AnimalResp> selectByPage(AnimalPageReq pageReq) {
        Page<Animal> page = PageHelper.startPage(pageReq.getPage(), pageReq.getPageSize());
        List<Animal> animals = animalMapper.selectByQuery(pageReq);
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
