package org.gyh.forestry.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.gyh.forestry.domain.AnimalManage;
import org.gyh.forestry.domain.AnimalRecognition;
import org.gyh.forestry.domain.User;
import org.gyh.forestry.dto.JsonPoint;
import org.gyh.forestry.dto.PageInfo;
import org.gyh.forestry.dto.req.AddAnimalRecognitionReq;
import org.gyh.forestry.dto.req.AnimalRecognitionPageReq;
import org.gyh.forestry.dto.resp.AnimalRecognitionResp;
import org.gyh.forestry.dto.resp.RecognitionResp;
import org.gyh.forestry.dto.resp.SelectDistance;
import org.gyh.forestry.mapper.AnimalManageMapper;
import org.gyh.forestry.mapper.AnimalRecognitionMapper;
import org.gyh.forestry.mapper.PointInfoMapper;
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
    @Autowired
    private PointInfoMapper pointInfoMapper;
    @Resource
    private AnimalManageMapper animalManageMapper;

    /**
     * 图片识别
     */
    public List<RecognitionResp> recognition(List<MultipartFile> files) {
        return files.stream().map(it -> {
            String path = fileService.uploadRecognitionFile(it);
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

    /**
     * ai识别后调用
     */
    public boolean addAnimal(AddAnimalRecognitionReq animalReq) {
        SelectDistance selectDistance = pointInfoMapper.selectDistance(animalReq.getLocation().getX(), animalReq.getLocation().getY());
        for (AddAnimalRecognitionReq.AnimalInfo animalInfo : animalReq.getAnimalInfo()) {
            if (animalRecognitionMapper.selectRepeat(animalReq.getLocation().toPoint(), animalInfo.getType(), animalInfo.getName(), animalReq.getPic()) > 0) {
                continue;
            }
            AnimalManage animalManage = animalManageMapper.selectByName(animalInfo.getName());
            if (animalManage == null) {
                continue;
            }
            AnimalRecognition animalRecognition = new AnimalRecognition();
            BeanUtils.copyProperties(animalReq, animalRecognition);
            if (selectDistance != null && selectDistance.getDistance() < 50) {
                animalRecognition.setPointId(selectDistance.getId());
            }
            animalRecognition.setType(animalInfo.getType());
            animalRecognition.setName(animalInfo.getName());
            animalRecognition.setDetails(animalInfo.getDetails());
            animalRecognition.setCreateTime(LocalDateTime.now());
            animalRecognition.setPic(fileService.getPath(animalReq.getPic()));
            animalRecognition.setLocation(animalReq.getLocation().toPoint());
            animalRecognition.setCreateTime(animalReq.getCreateTime());
            animalRecognitionMapper.insertSelective(animalRecognition);
        }
        return true;
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

    /**
     * 获取随机的10张图片
     */
    public List<String> randomImage() {
        return animalRecognitionMapper.randomImage();
    }
}
