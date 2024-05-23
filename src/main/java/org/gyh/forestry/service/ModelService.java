package org.gyh.forestry.service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.gyh.forestry.domain.Model;
import org.gyh.forestry.domain.User;
import org.gyh.forestry.mapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

/**
 * create by GYH on 2024/5/23
 */
@Service
@Slf4j
public class ModelService {
    @Resource
    private ModelMapper modelMapper;
    @Autowired
    private FileService fileService;

    public void uploadModel(String name, MultipartFile file) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String path = fileService.uploadFile(file);
        Model model = new Model();
        model.setName(name);
        model.setPath(path);
        model.setEnable(true);
        model.setCreator(user.getUsername());
        model.setCreatedTime(LocalDateTime.now());
    }
}
