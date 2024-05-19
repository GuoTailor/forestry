package org.gyh.forestry.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jakarta.annotation.Resource;
import org.gyh.forestry.domain.OperationRecord;
import org.gyh.forestry.domain.Role;
import org.gyh.forestry.domain.User;
import org.gyh.forestry.dto.PageInfo;
import org.gyh.forestry.dto.req.UserPageReq;
import org.gyh.forestry.dto.resp.AddUserInfo;
import org.gyh.forestry.dto.resp.UserInfo;
import org.gyh.forestry.mapper.RoleMapper;
import org.gyh.forestry.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * create by GYH on 2023/7/11
 */
@Service
public class UserService implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.findUserByUserName(username);
        if (user == null)
            throw new UsernameNotFoundException("用户不存在");
        List<Role> roles = userMapper.findRolesByUserId(user.getId());
        user.setRoles(roles);
        return user;
    }

    public List<User> findAll() {
        return userMapper.findAll();
    }

    public User findById(Integer userId) {
        if (userId == null) {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userId = user.getId();
        }
        User user = userMapper.selectByPrimaryKey(userId);
        if (user != null) {
            List<Role> rolesByUserId = roleMapper.getRolesByUserId(userId);
            user.setRoles(rolesByUserId);
        }
        return user;
    }

    public Boolean deleteUserById(Integer uid) {
        return userMapper.deleteByPrimaryKey(uid) > 0;
    }

    /**
     * 分页查询
     * @param pageReq 分页条件
     */
    public PageInfo<UserInfo> findByPage(UserPageReq pageReq) {
        try (Page<OperationRecord> page = PageHelper.startPage(pageReq.getPage(), pageReq.getPageSize())) {
            List<User> all = userMapper.findByPage(pageReq);
            List<UserInfo> list = all.stream().map(it -> {
                UserInfo userInfo = new UserInfo();
                BeanUtils.copyProperties(it, userInfo);
                return userInfo;
            }).toList();
            return PageInfo.ok(page.getTotal(), pageReq, list);
        }
    }

    public User addUser(AddUserInfo addUserInfo) {
        User user = addUserInfo.toUser();
        user.setPassword(passwordEncoder.encode(addUserInfo.password()));
        user.setCreateTime(LocalDateTime.now());
        userMapper.insertSelective(user);
        if (!CollectionUtils.isEmpty(addUserInfo.roles())) {
            for (Integer roleId : addUserInfo.roles()) {
                userMapper.insertUserRole(user.getId(), roleId);
            }
        }
        return user;
    }

}
