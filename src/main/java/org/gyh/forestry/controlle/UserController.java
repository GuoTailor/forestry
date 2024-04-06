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
import org.gyh.forestry.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public PageInfo<User> findByPage(@RequestBody UserPageReq pageReq) {
        return userService.findByPage(pageReq);
    }

    @PostMapping("/find/all")
    @Operation(summary = "查询全部用户列表", security = {@SecurityRequirement(name = "Authorization")})
    public ResponseInfo<List<User>> findAll() {
        return ResponseInfo.ok(userService.findAll());
    }

}
