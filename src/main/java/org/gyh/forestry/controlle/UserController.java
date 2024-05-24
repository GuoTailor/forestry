package org.gyh.forestry.controlle;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.gyh.forestry.domain.User;
import org.gyh.forestry.dto.PageInfo;
import org.gyh.forestry.dto.ResponseInfo;
import org.gyh.forestry.dto.req.UserPageReq;
import org.gyh.forestry.dto.resp.AddUserInfo;
import org.gyh.forestry.dto.req.UpdateUserReq;
import org.gyh.forestry.dto.resp.UserInfo;
import org.gyh.forestry.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * create by GYH on 2024/3/27
 */
@Tag(name = "用户")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    @Operation(summary = "添加用户", security = {@SecurityRequirement(name = "Authorization")})
    public ResponseInfo<User> addUser(@RequestBody @Valid AddUserInfo addUserInfo) {
        return ResponseInfo.ok(userService.addUser(addUserInfo));
    }

    @PostMapping("/find/page")
    @Operation(summary = "分页查询用户列表", security = {@SecurityRequirement(name = "Authorization")})
    public PageInfo<UserInfo> findByPage(@RequestBody UserPageReq pageReq) {
        return userService.findByPage(pageReq);
    }

    @PostMapping("/find/all")
    @Operation(summary = "查询全部用户列表", security = {@SecurityRequirement(name = "Authorization")})
    public ResponseInfo<List<UserInfo>> findAll() {
        return ResponseInfo.ok(userService.findAll());
    }

    @GetMapping()
    @Operation(summary = "获取用户信息，不传id就默认获取当前登录用户信息", security = {@SecurityRequirement(name = "Authorization")})
    public ResponseInfo<User> findById(@RequestParam(required = false) Integer id) {
        return ResponseInfo.ok(userService.findById(id));
    }

    @GetMapping("/{uid}")
    @Operation(summary = "删除用户", security = {@SecurityRequirement(name = "Authorization")})
    public ResponseInfo<Boolean> deleteUserById(@PathVariable Integer uid) {
        return ResponseInfo.ok(userService.deleteUserById(uid));
    }

    @PostMapping("/update")
    @Operation(summary = "修改用户信息", security = {@SecurityRequirement(name = "Authorization")})
    public ResponseInfo<User> updateUser(@RequestBody UpdateUserReq updateUserReq) {
        return ResponseInfo.ok(userService.updateUser(updateUserReq));
    }
}
