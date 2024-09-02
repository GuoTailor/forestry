package org.gyh.forestry.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.gyh.forestry.domain.AreaInfo;
import org.gyh.forestry.dto.PageInfo;
import org.gyh.forestry.dto.req.AddAreaInfo;
import org.gyh.forestry.dto.req.AreaInfoPageReq;
import org.gyh.forestry.dto.req.UpdateAreaInfo;
import org.gyh.forestry.dto.resp.AnimalManageResp;
import org.gyh.forestry.dto.resp.AreaInfoResp;
import org.gyh.forestry.exception.BusinessException;
import org.gyh.forestry.mapper.AreaInfoMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.List;

/**
 * create by GYH on 2024/8/13
 */
@Slf4j
@Service
public class AreaInfoService {
    @Resource
    private AreaInfoMapper areaInfoMapper;
    @Value("${spring.datasource.password}")
    private String password;

    /**
     * 添加区域
     */
    public AreaInfo addAreaInfo(AddAreaInfo addAreaInfo) {
        AreaInfo byName = areaInfoMapper.findByName(addAreaInfo.getName());
        if (byName != null) {
            throw new BusinessException("区域名字已存在");
        }
        AreaInfo areaInfo = new AreaInfo();
        BeanUtils.copyProperties(addAreaInfo, areaInfo);
        areaInfo.setCreateTime(LocalDateTime.now());
        areaInfoMapper.insertSelective(areaInfo);
        return areaInfo;
    }

    public String updateAreaInfo(UpdateAreaInfo updateAreaInfo) {
        AreaInfo areaInfo = new AreaInfo();
        BeanUtils.copyProperties(updateAreaInfo, areaInfo);
        areaInfoMapper.updateByPrimaryKeySelective(areaInfo);
        areaInfo = areaInfoMapper.selectByPrimaryKey(updateAreaInfo.getId());
        if (updateAreaInfo.getMoistureContent() != null) {
            String[] command = new String[]{"python3.8", "./pydir/main.py", "forestry", "postgres", password, areaInfo.getName(), areaInfo.getWeatherAddress() == null ? areaInfo.getName() : areaInfo.getWeatherAddress()};
            try {
                log.info("开始执行命令");
                Process process = Runtime.getRuntime().exec(command);
                // 读取输出流
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                BufferedReader errReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                StringBuilder sb = new StringBuilder();
                for (String line; (line = reader.readLine()) != null; log.info(line)) {
                    sb.append(line).append("\n");
                }
                for (String line; (line = errReader.readLine()) != null; log.error(line)) {
                    sb.append(line).append("\n");
                }
                reader.close();
                reader.close();
                int exitVal = process.waitFor();
                if (exitVal == 0) {
                    log.info("命令执行成功。");
                } else {
                    log.info("执行命令出错。");
                }
                return sb.toString();
            } catch (IOException | InterruptedException e) {
                log.error("执行命令出错", e);
                return e.getMessage();
            }
        }
        return "success";
    }

    /**
     * 分页查询区域信息
     */
    public PageInfo<AreaInfoResp> selectPage(AreaInfoPageReq pageReq) {
        try (Page<AnimalManageResp> page = PageHelper.startPage(pageReq.getPage(), pageReq.getPageSize())) {
            List<AreaInfoResp> all = areaInfoMapper.selectByPage(pageReq);
            return PageInfo.ok(page.getTotal(), pageReq, all);
        }
    }


    /**
     * 删除区域
     *
     * @param id 区域id
     */
    public void delete(Integer id) {
        areaInfoMapper.deleteByPrimaryKey(id);
    }
}
