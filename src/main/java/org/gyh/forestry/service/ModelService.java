package org.gyh.forestry.service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
