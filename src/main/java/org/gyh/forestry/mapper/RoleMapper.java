package org.gyh.forestry.mapper;

import org.gyh.forestry.domain.Role;
import org.gyh.forestry.dto.req.RolePageReq;

import java.util.List;

/**
 * create by GYH on 2024/5/18
 */
public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> getAllRoles();

    List<Role> getByReq(RolePageReq req);

    List<Role> getRolesByUserId(Integer userId);
}
