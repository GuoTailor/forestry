package org.gyh.forestry.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.gyh.forestry.domain.Model;
import org.gyh.forestry.domain.OperationRecord;
import org.gyh.forestry.domain.User;
import org.gyh.forestry.dto.PageInfo;
import org.gyh.forestry.dto.PageReq;
import org.gyh.forestry.dto.req.ModelPageReq;
import org.gyh.forestry.dto.resp.ModelResp;
import org.gyh.forestry.dto.resp.UserInfo;
import org.gyh.forestry.exception.BusinessException;
import org.gyh.forestry.mapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * create by GYH on 2024/5/23
 */
@Service
@Slf4j
public class ModelService {
    private final ReentrantLock lock = new ReentrantLock();
    @Resource
    private ModelMapper modelMapper;
    @Autowired
    private FileService fileService;

    public Model uploadModel(String name, MultipartFile file) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String path = fileService.uploadFile(file);
        Model model = new Model();
        model.setName(name);
        model.setPath(path);
        model.setEnable(false);
        model.setCreator(user.getUsername());
        model.setCreatedTime(LocalDateTime.now());
        modelMapper.insertSelective(model);
        return model;
    }

    /**
     * 启用停用
     */
    public Model switchModel(Integer id, Boolean enable) {
        Model model = modelMapper.selectByPrimaryKey(id);
        if (model == null) {
            throw new BusinessException("模型不存在");
        }
        model.setEnable(enable);
        if (enable) {
            lock.lock();
            try {
                if (modelMapper.selectByEnable() != null) {
                    throw new BusinessException("已存在启用的模型");
                }
                modelMapper.updateByPrimaryKeySelective(model);
                return model;
            } finally {
                lock.unlock();
            }
        } else {
            modelMapper.updateByPrimaryKeySelective(model);
            return model;
        }
    }

    public Model reUpload(Integer id, MultipartFile file) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Model model = modelMapper.selectByPrimaryKey(id);
        if (model == null) {
            throw new BusinessException("模型不存在");
        }
        String path = fileService.uploadFile(file);
        model.setId(id);
        model.setCreator(user.getUsername());
        model.setPath(path);
        model.setEnable(null);
        modelMapper.updateByPrimaryKeySelective(model);
        return modelMapper.selectByPrimaryKey(id);
    }

    public PageInfo<ModelResp> getModels(ModelPageReq pageReq) {
        try (Page<ModelResp> page = PageHelper.startPage(pageReq.getPage(), pageReq.getPageSize())) {
            List<ModelResp> all = modelMapper.findByPage(pageReq);
            return PageInfo.ok(page.getTotal(), pageReq, all);
        }
    }
}
