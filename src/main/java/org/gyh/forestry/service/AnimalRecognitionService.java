package org.gyh.forestry.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.gyh.forestry.domain.AnimalRecognition;
import org.gyh.forestry.domain.User;
import org.gyh.forestry.dto.JsonPoint;
import org.gyh.forestry.dto.PageInfo;
import org.gyh.forestry.dto.req.AddAnimalRecognitionReq;
import org.gyh.forestry.dto.req.AnimalRecognitionPageReq;
import org.gyh.forestry.dto.resp.AnimalRecognitionResp;
import org.gyh.forestry.dto.resp.RecognitionResp;
import org.gyh.forestry.exception.BusinessException;
import org.gyh.forestry.mapper.AnimalRecognitionMapper;
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
public class AnimalRecognitionService {
    @Resource
    private AnimalRecognitionMapper animalRecognitionMapper;
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
    public List<AnimalRecognitionResp> addAnimal(List<AddAnimalRecognitionReq> animalReqs) {
        return animalReqs.stream().map(animalReq -> {
            AnimalRecognition animalRecognition = new AnimalRecognition();
            BeanUtils.copyProperties(animalReq, animalRecognition);
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            animalRecognition.setCreator(user.getUsername());
            animalRecognition.setCreateTime(LocalDateTime.now());
            animalRecognitionMapper.insertSelective(animalRecognition);
            AnimalRecognitionResp resp = new AnimalRecognitionResp();
            BeanUtils.copyProperties(animalRecognition, resp);
            JsonPoint jsonPoint = new JsonPoint(animalRecognition.getLocation());
            resp.setLocation(jsonPoint);
            return resp;
        }).toList();
    }

    public boolean addAnimal(AddAnimalRecognitionReq animalReq, HttpServletRequest request) {
        if (!request.getRemoteAddr().equals("0:0:0:0:0:0:0:1")) {
            throw new BusinessException("该接口只允许内部调用");
        }
        AnimalRecognition animalRecognition = new AnimalRecognition();
        BeanUtils.copyProperties(animalReq, animalRecognition);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        animalRecognition.setCreator(user.getUsername());
        animalRecognition.setCreateTime(LocalDateTime.now());
        return animalRecognitionMapper.insertSelective(animalRecognition) == 1;
    }

    public AnimalRecognitionResp selectById(Integer id) {
        AnimalRecognition animalRecognition = animalRecognitionMapper.selectByPrimaryKey(id);
        AnimalRecognitionResp resp = new AnimalRecognitionResp();
        BeanUtils.copyProperties(animalRecognition, resp);
        JsonPoint jsonPoint = new JsonPoint(animalRecognition.getLocation());
        resp.setLocation(jsonPoint);
        return resp;
    }

    /**
     * 分页查询动物
     */
    public PageInfo<AnimalRecognitionResp> selectByPage(AnimalRecognitionPageReq pageReq) {
        try (Page<AnimalRecognition> page = PageHelper.startPage(pageReq.getPage(), pageReq.getPageSize())) {
            List<AnimalRecognition> animalRecognitions = animalRecognitionMapper.selectByQuery(pageReq);
            List<AnimalRecognitionResp> list = animalRecognitions.stream().map(it -> {
                AnimalRecognitionResp resp = new AnimalRecognitionResp();
                BeanUtils.copyProperties(it, resp);
                JsonPoint jsonPoint = new JsonPoint(it.getLocation());
                resp.setLocation(jsonPoint);
                return resp;
            }).toList();
            return PageInfo.ok(page.getTotal(), pageReq, list);
        }
    }
}
