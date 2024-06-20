package org.gyh.forestry.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jakarta.annotation.Resource;
import org.gyh.forestry.domain.AnimalManage;
import org.gyh.forestry.dto.PageInfo;
import org.gyh.forestry.dto.req.AddAnimalManageReq;
import org.gyh.forestry.dto.req.AnimalManagePageReq;
import org.gyh.forestry.dto.req.UpdateAnimalManageReq;
import org.gyh.forestry.dto.resp.AnimalManageResp;
import org.gyh.forestry.mapper.AnimalManageMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * create by GYH on 2024/6/17
 */
@Service
public class AnimalManageService {
    @Resource
    private AnimalManageMapper animalManageMapper;

    public AnimalManage addAnimalManage(AddAnimalManageReq req) {
        AnimalManage animalManage = new AnimalManage();
        animalManage.setName(req.getName());
        animalManage.setAnimalTypeId(req.getAnimalTypeId());
        animalManage.setDescribe(req.getDescribe());
        animalManage.setEnable(true);
        animalManage.setCreateTime(LocalDateTime.now());
        animalManageMapper.insertSelective(animalManage);
        return animalManage;
    }

    /**
     * 更新动物
     * @param req 更新参数
     * @return 是否更新成功
     */
    public boolean updateAnimalManage(UpdateAnimalManageReq req) {
        AnimalManage animalManage = new AnimalManage();
        animalManage.setId(req.getId());
        animalManage.setEnable(req.getEnable());
        animalManage.setAnimalTypeId(req.getAnimalTypeId());
        animalManage.setName(req.getName());
        animalManage.setDescribe(req.getDescribe());

        return animalManageMapper.updateByPrimaryKeySelective(animalManage) == 1;
    }

    /**
     * 分页查询动物
     * @param pageReq 分页参数
     * @return 返回分页数据
     */
    public PageInfo<AnimalManageResp> selectByPage(AnimalManagePageReq pageReq) {
        try (Page<AnimalManageResp> page = PageHelper.startPage(pageReq.getPage(), pageReq.getPageSize())) {
            List<AnimalManageResp> all = animalManageMapper.selectByPage(pageReq);
            return PageInfo.ok(page.getTotal(), pageReq, all);
        }
    }

    public boolean deleteAnimal(Integer id) {
        return animalManageMapper.deleteByPrimaryKey(id) == 1;
    }
}
