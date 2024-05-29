package org.gyh.forestry.controlle;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.gyh.forestry.aspect.LogRecord;
import org.gyh.forestry.domain.Menu;
import org.gyh.forestry.domain.Role;
import org.gyh.forestry.domain.vo.MenuVO;
import org.gyh.forestry.dto.PageInfo;
import org.gyh.forestry.dto.ResponseInfo;
import org.gyh.forestry.dto.req.*;
import org.gyh.forestry.service.MenuService;
import org.gyh.forestry.service.RoleService;
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

    @PostMapping("/role/page")
    @Operation(summary = "分页获取角色", security = {@SecurityRequirement(name = "Authorization")})
    public PageInfo<Role> getRoleByPage(@RequestBody RolePageReq req) {
        return roleService.getRoleByPage(req);
    }

    @Operation(summary = "添加角色", security = {@SecurityRequirement(name = "Authorization")})
    @PostMapping("/role")
    @LogRecord(model = "菜单", method = "添加角色")
    public ResponseInfo<Role> addRole(@RequestBody AddRole role) {
        return ResponseInfo.ok(roleService.addRole(role));
    }

    @Operation(summary = "删除角色", security = {@SecurityRequirement(name = "Authorization")})
    @DeleteMapping("/role/{rid}")
    @LogRecord(model = "菜单", method = "删除角色")
    public ResponseInfo<?> deleteRoleById(@PathVariable Integer rid) {
        if (roleService.deleteRoleById(rid) == 1) {
            return ResponseInfo.ok("删除成功!");
        }
        return ResponseInfo.failed("删除失败!");
    }

    @Operation(summary = "修改角色", security = {@SecurityRequirement(name = "Authorization")})
    @PostMapping("/role/update")
    @LogRecord(model = "菜单", method = "修改角色")
    public ResponseInfo<?> updateRole(@RequestBody Role role) {
        if (roleService.updateRole(role) == 1) {
            return ResponseInfo.ok("修改成功!");
        }
        return ResponseInfo.failed("修改失败!");
    }

    @Operation(summary = "获取所有菜单", security = {@SecurityRequirement(name = "Authorization")})
    @GetMapping("/menus/all")
    public ResponseInfo<List<MenuVO>> getAllMenus() {
        return ResponseInfo.ok(menuService.getAllMenus());
    }

    @Operation(summary = "添加菜单", security = {@SecurityRequirement(name = "Authorization")})
    @PostMapping("/menus/add")
    @LogRecord(model = "菜单", method = "添加菜单")
    public ResponseInfo<Menu> addMenu(@Valid @RequestBody AddMenu addMenu) {
        return ResponseInfo.ok(menuService.addMenu(addMenu));
    }

    @Operation(summary = "修改菜单", security = {@SecurityRequirement(name = "Authorization")})
    @PostMapping("/menus/update")
    @LogRecord(model = "菜单", method = "修改菜单")
    public ResponseInfo<Menu> updateMenu(@RequestBody UpdateMenuReq req) {
        return ResponseInfo.ok(menuService.updateMenu(req));
    }

    @Operation(summary = "删除菜单", security = {@SecurityRequirement(name = "Authorization")})
    @GetMapping("/menus/{rid}")
    @LogRecord(model = "菜单", method = "删除菜单")
    public ResponseInfo<Boolean> deleteMenuById(@PathVariable Integer rid) {
        return ResponseInfo.ok(menuService.deleteMenuById(rid));
    }

    @Operation(summary = "获取当前用户的所有菜单", security = {@SecurityRequirement(name = "Authorization")})
    @GetMapping("/menus")
    public ResponseInfo<MenuVO> getMenusByUserId() {
        return ResponseInfo.ok(menuService.getMenusByUserId());
    }

    @Operation(summary = "获取指定角色的所有菜单", security = {@SecurityRequirement(name = "Authorization")})
    @GetMapping("/mids/{rid}")
    public ResponseInfo<List<Integer>> getMidsByRid(@PathVariable Integer rid) {
        return ResponseInfo.ok(menuService.getMidsByRid(rid));
    }

    @Operation(summary = "更新角色的菜单", security = {@SecurityRequirement(name = "Authorization")})
    @PostMapping("/")
    @LogRecord(model = "菜单", method = "更新角色的菜单")
    public ResponseInfo<?> updateMenuRole(@RequestBody @Valid UpdateMenuRoleRep req) {
        if (menuService.updateMenuRole(req.getRid(), req.getMids())) {
            return ResponseInfo.ok("更新成功!");
        }
        return ResponseInfo.failed("更新失败!");
    }

}
