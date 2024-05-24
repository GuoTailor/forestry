package org.gyh.forestry.mapper;

import org.apache.ibatis.annotations.Param;
import org.gyh.forestry.domain.Role;
import org.gyh.forestry.domain.User;
import org.gyh.forestry.dto.req.UserPageReq;

import java.util.List;

/**
 * create by GYH on 2024/5/14
 */
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int insertUserRole(@Param("userId") Integer userId, @Param("roleId") Integer roleId);

    int deleteAllRole(@Param("userId") Integer userId);

    User findUserByUserName(String username);

    List<Role> findRolesByUserId(Integer userId);

    List<User> findAll();

    List<User> findByPage(UserPageReq req);

}
