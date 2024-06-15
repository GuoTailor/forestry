package org.gyh.forestry.service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.gyh.forestry.mapper.AnimalTypeMapper;
import org.springframework.stereotype.Service;

/**
 * create by GYH on 2024/6/15
 */
@Slf4j
@Service
public class AnimalTypeService {
    @Resource
    private AnimalTypeMapper animalTypeMapper;

}
