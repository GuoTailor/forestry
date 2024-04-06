package org.gyh.forestry.mapper;


import org.apache.ibatis.annotations.Param;
import org.gyh.forestry.domain.Role;
import org.gyh.forestry.domain.User;
import org.gyh.forestry.dto.req.UserPageReq;

import java.util.List;

/**
 * create by GYH on 2023/7/12
 */
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertUserRole(@Param("userId") Integer userId, @Param("roleId") Integer roleId);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    User findUserByUserName(String username);

    List<Role> findRolesByUserId(Integer userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> findAll();

    List<User> findByPage(UserPageReq req);

    Long countByPage(UserPageReq req);
}
