package org.gyh.forestry.controlle;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.gyh.forestry.dto.ResponseInfo;
import org.gyh.forestry.dto.req.LoginUserReq;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * create by GYH on 2022/12/3
 */
@Tag(name = "登录")
@RestController
@RequestMapping("/login")
public class LoginController {

    @Operation(summary = "登录")
    @PostMapping(consumes = "application/x-www-form-urlencoded")
    @RequestBody(content = {@Content(mediaType = MediaType.APPLICATION_FORM_URLENCODED_VALUE)})
    public ResponseInfo<?> login(LoginUserReq loginUserReq) {
        return ResponseInfo.ok();
    }
}
