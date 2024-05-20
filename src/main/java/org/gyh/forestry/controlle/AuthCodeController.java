package org.gyh.forestry.controlle;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.gyh.forestry.domain.AuthCode;
import org.gyh.forestry.dto.PageInfo;
import org.gyh.forestry.dto.ResponseInfo;
import org.gyh.forestry.dto.req.AddAuthCodeReq;
import org.gyh.forestry.dto.req.AuthCodePageReq;
import org.gyh.forestry.dto.req.AuthCodeUpdateReq;
import org.gyh.forestry.service.AuthCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * create by GYH on 2024/5/20
 */
@Tag(name = "授权码")
@RestController
@RequestMapping("/code")
public class AuthCodeController {
    @Autowired
    private AuthCodeService authCodeService;

    @PostMapping("/add")
    @Operation(summary = "添加授权码", security = {@SecurityRequirement(name = "Authorization")})
    public ResponseInfo<Boolean> addCode(@RequestBody AddAuthCodeReq authCodeReq) {
        Integer insert = authCodeService.insert(authCodeReq);
        return ResponseInfo.ok(insert == 1);
    }

    @PostMapping("/page")
    @Operation(summary = "分页查询授权码", security = {@SecurityRequirement(name = "Authorization")})
    public PageInfo<AuthCode> getByPage(@RequestBody AuthCodePageReq authCodeReq) {
        return authCodeService.getByPage(authCodeReq);
    }

    @GetMapping("/delete")
    @Operation(summary = "删除授权码", security = {@SecurityRequirement(name = "Authorization")})
    public ResponseInfo<Boolean> deleteById(@RequestParam("id") Integer id) {
        Integer delete = authCodeService.deleteById(id);
        return ResponseInfo.ok(delete == 1);
    }
    
    @PostMapping("/update")
    @Operation(summary = "更新授权码", security = {@SecurityRequirement(name = "Authorization")})
    public ResponseInfo<Boolean> updateById(@RequestBody AuthCodeUpdateReq req) {
        Integer update = authCodeService.updateById(req);
        return ResponseInfo.ok(update == 1);
    }
}
