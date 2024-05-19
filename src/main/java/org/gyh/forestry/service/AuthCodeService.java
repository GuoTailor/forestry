package org.gyh.forestry.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jakarta.annotation.Resource;
import org.gyh.forestry.domain.AuthCode;
import org.gyh.forestry.domain.OperationRecord;
import org.gyh.forestry.domain.User;
import org.gyh.forestry.dto.PageInfo;
import org.gyh.forestry.dto.req.AddAuthCodeReq;
import org.gyh.forestry.dto.req.AuthCodePageReq;
import org.gyh.forestry.mapper.AuthCodeMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * create by GYH on 2024/5/18
 */
@Service
public class AuthCodeService {
    @Resource
    private AuthCodeMapper authCodeMapper;

    public Integer insert(AddAuthCodeReq authCodeReq) {
        AuthCode authCode = new AuthCode();
        BeanUtils.copyProperties(authCode, authCode);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        authCode.setHandler(user.getUsername());
        authCode.setCreateTime(LocalDateTime.now());
        return authCodeMapper.insert(authCode);
    }

    public PageInfo<AuthCode> getByPage(AuthCodePageReq authCodeReq) {
        try (Page<OperationRecord> page = PageHelper.startPage(authCodeReq.getPage(), authCodeReq.getPageSize())) {
            return PageInfo.ok(page.getTotal(), authCodeReq, authCodeMapper.selectAll());
        }
    }

    public Integer deleteById(Integer id) {
        return authCodeMapper.deleteByPrimaryKey(id);
    }

    public Integer updateById(AuthCode authCode) {
        return authCodeMapper.updateByPrimaryKeySelective(authCode);
    }

}
