package org.gyh.forestry.service;

import jakarta.annotation.Resource;
import org.gyh.forestry.domain.Role;
import org.gyh.forestry.domain.User;
import org.gyh.forestry.dto.PageInfo;
import org.gyh.forestry.dto.req.UserPageReq;
import org.gyh.forestry.dto.resp.AddUserInfo;
import org.gyh.forestry.mapper.RoleMapper;
import org.gyh.forestry.mapper.UserMapper;
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

    public PageInfo<User> findByPage(UserPageReq pageReq) {
        return PageInfo.ok(userMapper.countByPage(pageReq), pageReq, userMapper.findByPage(pageReq));
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
