package org.gyh.forestry.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.gyh.forestry.domain.PointInfo;
import org.gyh.forestry.domain.ScenicSpot;
import org.gyh.forestry.domain.User;
import org.gyh.forestry.dto.JsonPoint;
import org.gyh.forestry.dto.PageInfo;
import org.gyh.forestry.dto.req.*;
import org.gyh.forestry.dto.resp.PointInfoResp;
import org.gyh.forestry.dto.resp.ScenicSpotPointInfoResp;
import org.gyh.forestry.mapper.PointInfoMapper;
import org.gyh.forestry.mapper.ScenicSpotMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * create by GYH on 2024/4/6
 */
@Slf4j
@Service
public class PointInfoService {
    @Resource
    private PointInfoMapper pointInfoMapper;
    @Resource
    private ScenicSpotMapper scenicSpotMapper;

    /**
     * 新增点位
     */
    public PointInfoResp save(AddPointInfo pointInfo) {
        PointInfo pointInfo1 = new PointInfo();
        BeanUtils.copyProperties(pointInfo, pointInfo1);
        pointInfo1.setPoint(pointInfo.getPoint().toPoint());
        pointInfo1.setCreateTime(LocalDateTime.now());
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        pointInfo1.setCreator(user.getUsername());
        pointInfoMapper.insertSelective(pointInfo1);
        PointInfoResp resp = new PointInfoResp();
        BeanUtils.copyProperties(pointInfo1, resp);
        resp.setPoint(new JsonPoint(pointInfo1.getPoint()));
        return resp;
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

    public PageInfo<PointInfoResp> selectByPage(PointInfoPageReq pageReq) {
        try (Page<PointInfoResp> page = PageHelper.startPage(pageReq.getPage(), pageReq.getPageSize())) {
            List<PointInfo> pointInfoResps = pointInfoMapper.selectByPage(pageReq);
            List<PointInfoResp> list = pointInfoResps.stream().map(it -> {
                PointInfoResp resp = new PointInfoResp();
                BeanUtils.copyProperties(it, resp);
                if (it.getScenicSpotId() != null) {
                    ScenicSpot scenicSpot = scenicSpotMapper.selectByPrimaryKey(it.getScenicSpotId());
                    if (scenicSpot != null) {
                        resp.setScenicSpotName(scenicSpot.getName());
                    }
                }
                resp.setPoint(new JsonPoint(it.getPoint()));
                return resp;
            }).toList();
            return PageInfo.ok(page.getTotal(), pageReq, list);
        }
    }

    public PageInfo<ScenicSpotPointInfoResp> selectAnimalByPage(ScenicSpotPointPageReq pageReq) {
        try (Page<ScenicSpot> page = PageHelper.startPage(pageReq.getPage(), pageReq.getPageSize())) {
            List<ScenicSpot> scenicSpots = scenicSpotMapper.selectByName(pageReq);
            PointInfoPageReq pointPageReq = new PointInfoPageReq();
            BeanUtils.copyProperties(pageReq, pointPageReq);
            List<ScenicSpotPointInfoResp> list = scenicSpots.parallelStream().map(it -> {
                ScenicSpotPointInfoResp resp = new ScenicSpotPointInfoResp();
                BeanUtils.copyProperties(it, resp);
                resp.setScenicSpotName(it.getName());
                resp.setCreator(it.getCreator());
                List<PointInfo> pointInfoResps = pointInfoMapper.selectByPage(pointPageReq);
                resp.setPoints(pointInfoResps.stream().map(pointResponse -> {
                    PointInfoResp pointInfoResp = new PointInfoResp();
                    BeanUtils.copyProperties(pointResponse, pointInfoResp);
                    pointInfoResp.setPoint(new JsonPoint(pointResponse.getPoint()));
                    return pointInfoResp;
                }).toList());
                return resp;
            }).toList();
            return PageInfo.ok(page.getTotal(), pageReq, list);
        }
    }

    public List<PointInfoResp> getAll(PointInfoAllReq req) {
        List<PointInfo> pointInfoResps = pointInfoMapper.selectByPage(new PointInfoPageReq(req));
        return pointInfoResps.stream().map(it -> {
            PointInfoResp resp = new PointInfoResp();
            BeanUtils.copyProperties(it, resp);
            if (it.getScenicSpotId() != null) {
                ScenicSpot scenicSpot = scenicSpotMapper.selectByPrimaryKey(it.getScenicSpotId());
                if (scenicSpot != null) {
                    resp.setScenicSpotName(scenicSpot.getName());
                }
            }
            resp.setPoint(new JsonPoint(it.getPoint()));
            return resp;
        }).toList();
    }

    /**
     * 添加景区
     *
     * @param name 景区名字
     */
    public ScenicSpot addScenicSpot(String name) {
        ScenicSpot scenicSpot = new ScenicSpot();
        scenicSpot.setName(name);
        scenicSpot.setCreateTime(LocalDateTime.now());
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        scenicSpot.setCreator(user.getUsername());
        scenicSpotMapper.insertSelective(scenicSpot);
        return scenicSpot;
    }
}
