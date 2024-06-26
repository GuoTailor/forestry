package org.gyh.forestry.service;

import lombok.extern.slf4j.Slf4j;
import org.gyh.forestry.domain.Menu;
import org.gyh.forestry.domain.User;
import org.gyh.forestry.domain.vo.MenuVO;
import org.gyh.forestry.domain.vo.MenuWithRole;
import org.gyh.forestry.dto.req.AddMenu;
import org.gyh.forestry.dto.req.UpdateMenuReq;
import org.gyh.forestry.exception.BusinessException;
import org.gyh.forestry.mapper.MenuMapper;
import org.gyh.forestry.mapper.MenuRoleMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author javaboy
 * @since 2024-01-03
 */
@Slf4j
@Service
public class MenuService {

    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private MenuRoleMapper menuRoleMapper;

    public MenuVO getMenusByUserId() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Menu> menus = menuMapper.getMenusByUserId(user.getId());
        Map<Integer, MenuVO> menuMaps = menus.stream().map(it -> {
            MenuVO vo = new MenuVO();
            BeanUtils.copyProperties(it, vo);
            vo.setChildren(new ArrayList<>());
            return vo;
        }).collect(Collectors.toMap(MenuVO::getId, Function.identity()));
        MenuVO root = null;
        for (MenuVO menuVO : menuMaps.values()) {
            if (menuVO.getParentId() == -1) {
                root = menuVO;
            } else {
                menuMaps.get(menuVO.getParentId()).getChildren().add(menuVO);
            }
        }
        return root;
    }

    @Cacheable(cacheNames = "AllMenus", key = "'AllMenus'")
    public List<MenuWithRole> getAllMenusWithRole() {
        log.info("getAllMenusWithRole>>>>>>>>>>>{}", Thread.currentThread().isVirtual());
        return menuMapper.getAllMenusWithRole();
    }

    public List<MenuVO> getAllMenus() {
        return menuMapper.getAllMenus();
    }

    @CacheEvict(cacheNames = "AllMenus", key = "'AllMenus'")
    public Menu addMenu(AddMenu addMenu) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(addMenu, menu);
        if (addMenu.getParentId() != null) {
            Menu menusByParentId = menuMapper.selectByPrimaryKey(addMenu.getParentId());
            if (menusByParentId == null) {
                throw new BusinessException("父级菜单不存在");
            }
        }
        menuMapper.insertSelective(menu);
        return menu;
    }

    @CacheEvict(cacheNames = "AllMenus", key = "'AllMenus'")
    public Boolean deleteMenuById(Integer rid) {
        return menuMapper.deleteByPrimaryKey(rid) > 0;
    }

    public List<Integer> getMidsByRid(Integer rid) {
        return menuMapper.getMidsByRid(rid);
    }

    @CacheEvict(cacheNames = "AllMenus", key = "'AllMenus'")
    public Menu updateMenu(UpdateMenuReq req) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(req, menu);
        if (req.getParentId() != null) {
            Menu menusByParentId = menuMapper.selectByPrimaryKey(req.getParentId());
            if (menusByParentId == null) {
                throw new BusinessException("父级菜单不存在");
            }
        }
        menuMapper.updateByPrimaryKeySelective(menu);
        return menu;
    }

    @CacheEvict(cacheNames = "AllMenus", key = "'AllMenus'")
    @Transactional(rollbackFor = Exception.class)
    public boolean updateMenuRole(Integer rid, List<Integer> mids) {
        menuRoleMapper.deleteByRid(rid);
        if (mids == null || mids.isEmpty()) {
            return true;
        }
        Integer result = menuRoleMapper.insertRecord(rid, mids);
        return result == mids.size();
    }
}
