package org.gyh.forestry.controlle;

import org.gyh.forestry.domain.Menu;
import org.gyh.forestry.domain.Role;
import org.gyh.forestry.domain.vo.MenuVO;
import org.gyh.forestry.dto.ResponseInfo;
import org.gyh.forestry.dto.req.AddMenu;
import org.gyh.forestry.service.MenuService;
import org.gyh.forestry.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 */
@Tag(name = "菜单")
@RestController
@RequestMapping("/system/permission")
public class MenuController {

    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;

    @GetMapping("/")
    @Operation(summary = "获取所有角色", security = {@SecurityRequirement(name = "Authorization")})
    public ResponseInfo<List<Role>> getAllRoles() {
        return ResponseInfo.ok(roleService.getAllRoles());
    }

    @Operation(summary = "获取所有菜单", security = {@SecurityRequirement(name = "Authorization")})
    @GetMapping("/menus/all")
    public ResponseInfo<List<MenuVO>> getAllMenus() {
        return ResponseInfo.ok(menuService.getAllMenus());
    }

    @Operation(summary = "添加菜单", security = {@SecurityRequirement(name = "Authorization")})
    @PostMapping("/menus/all")
    public ResponseInfo<Menu> addMenu(@RequestBody AddMenu addMenu) {
        return ResponseInfo.ok(menuService.addMenu(addMenu));
    }

    @Operation(summary = "删除菜单", security = {@SecurityRequirement(name = "Authorization")})
    @GetMapping("/menus/{rid}")
    public ResponseInfo<Boolean> deleteMenuById(@PathVariable Integer rid) {
        return ResponseInfo.ok(menuService.deleteMenuById(rid));
    }

    @Operation(summary = "获取当前用户的所有菜单", security = {@SecurityRequirement(name = "Authorization")})
    @GetMapping("/menus")
    public ResponseInfo<List<MenuVO>> getMenusByHrId() {
        return ResponseInfo.ok(menuService.getMenusByHrId());
    }

    @Operation(summary = "获取指定角色的所有菜单", security = {@SecurityRequirement(name = "Authorization")})
    @GetMapping("/mids/{rid}")
    public ResponseInfo<List<Integer>> getMidsByRid(@PathVariable Integer rid) {
        return ResponseInfo.ok(menuService.getMidsByRid(rid));
    }

    @Operation(summary = "更新角色的菜单", security = {@SecurityRequirement(name = "Authorization")})
    @PutMapping("/")
    public ResponseInfo<?> updateMenuRole(Integer rid, Integer[] mids) {
        if (menuService.updateMenuRole(rid, mids)) {
            return ResponseInfo.ok("更新成功!");
        }
        return ResponseInfo.failed("更新失败!");
    }

    @Operation(summary = "添加角色", security = {@SecurityRequirement(name = "Authorization")})
    @PostMapping("/role")
    public ResponseInfo<?> addRole(@RequestBody Role role) {
        if (roleService.addRole(role) == 1) {
            return ResponseInfo.ok("添加成功!");
        }
        return ResponseInfo.failed("添加失败!");
    }

    @Operation(summary = "删除角色", security = {@SecurityRequirement(name = "Authorization")})
    @DeleteMapping("/role/{rid}")
    public ResponseInfo<?> deleteRoleById(@PathVariable Integer rid) {
        if (roleService.deleteRoleById(rid) == 1) {
            return ResponseInfo.ok("删除成功!");
        }
        return ResponseInfo.failed("删除失败!");
    }
}
