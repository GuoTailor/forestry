package org.gyh.forestry.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.gyh.forestry.domain.Role;
import org.gyh.forestry.dto.PageInfo;
import org.gyh.forestry.dto.req.AddRole;
import org.gyh.forestry.dto.req.RolePageReq;
import org.gyh.forestry.exception.BusinessException;
import org.gyh.forestry.mapper.RoleMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private MenuService menuService;

    public List<Role> getAllRoles() {
        return roleMapper.getAllRoles();
    }

    public PageInfo<Role> getRoleByPage(RolePageReq pageReq) {
        try (Page<Role> page = PageHelper.startPage(pageReq.getPage(), pageReq.getPageSize())) {
            List<Role> byReq = roleMapper.getByReq(pageReq);
            return PageInfo.ok(page.getTotal(), pageReq, byReq);
        }
    }

    public Role addRole(AddRole addRole) {
        if (addRole.getSortNum() != null) {
            int count = roleMapper.countBySort(addRole.getSortNum());
            if (count >= 1) {
                throw new BusinessException("排序重复");
            }
        }
        if (!addRole.getAuthority().startsWith("ROLE_")) {
            addRole.setAuthority("ROLE_" + addRole.getAuthority());
        }
        Role role = new Role();
        BeanUtils.copyProperties(addRole, role);
        roleMapper.insertSelective(role);
        menuService.updateMenuRole(role.getId(), addRole.getMenuIds());
        return role;
    }

    public Integer updateRole(Role role) {
        return roleMapper.updateByPrimaryKeySelective(role);
    }

    public Integer deleteRoleById(Integer rid) {
        return roleMapper.deleteByPrimaryKey(rid);
    }
}
