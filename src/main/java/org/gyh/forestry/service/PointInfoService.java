package org.gyh.forestry.service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.gyh.forestry.domain.PointInfo;
import org.gyh.forestry.domain.User;
import org.gyh.forestry.dto.req.AddPointInfo;
import org.gyh.forestry.dto.req.UpdatePointInfo;
import org.gyh.forestry.dto.resp.JsonPoint;
import org.gyh.forestry.dto.resp.PointInfoResp;
import org.gyh.forestry.mapper.PointInfoMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * create by GYH on 2024/4/6
 */
@Slf4j
@Service
public class PointInfoService {
    @Resource
    private PointInfoMapper pointInfoMapper;


    /**
     * 新增点位
     */
    public PointInfo save(AddPointInfo pointInfo) {
        PointInfo pointInfo1 = new PointInfo();
        BeanUtils.copyProperties(pointInfo, pointInfo1);
        pointInfo1.setPoint(pointInfo.getPoint().toPoint());
        pointInfo1.setCreatedTime(LocalDateTime.now());
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        pointInfo1.setCreator(user.getUsername());
        pointInfoMapper.insertSelective(pointInfo1);
        return pointInfo1;
    }

    public boolean deleteById(Integer id) {
        return pointInfoMapper.deleteByPrimaryKey(id) == 1;
    }

    public void updateById(UpdatePointInfo pointInfo) {
        PointInfo pointInfo1 = new PointInfo();
        BeanUtils.copyProperties(pointInfo, pointInfo1);
        pointInfo1.setPoint(pointInfo.getPoint().toPoint());
        pointInfoMapper.updateByPrimaryKeySelective(pointInfo1);
    }

    public PointInfoResp selectById(Integer id) {
        PointInfo pointInfo = pointInfoMapper.selectByPrimaryKey(id);
        PointInfoResp resp = new PointInfoResp();
        BeanUtils.copyProperties(pointInfo, resp);
        resp.setPoint(new JsonPoint(pointInfo.getPoint()));
        return resp;
    }


}
