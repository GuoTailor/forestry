package org.gyh.forestry.service;

import org.gyh.forestry.domain.Role;
import org.gyh.forestry.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 */
@Service
public class RoleService {
    @Autowired
    private RoleMapper roleMapper;
    public List<Role> getAllRoles() {
        return roleMapper.getAllRoles();
    }

    public Integer addRole(Role role) {
        if (!role.getAuthority().startsWith("ROLE_")) {
            role.setAuthority("ROLE_" + role.getAuthority());
        }
        return roleMapper.insert(role);
    }

    public Integer updateRole(Role role) {
        return roleMapper.updateByPrimaryKeySelective(role);
    }

    public Integer deleteRoleById(Integer rid) {
        return roleMapper.deleteByPrimaryKey(rid);
    }
}
