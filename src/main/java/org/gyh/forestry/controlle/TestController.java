package org.gyh.forestry.controlle;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import org.gyh.forestry.domain.User;
import org.gyh.forestry.dto.ResponseInfo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * create by GYH on 2024/5/30
 */
@Tag(name = "test")
@RestController
@RequestMapping("/test")
public class TestController {

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "添加用户", security = {@SecurityRequirement(name = "Authorization")})
    public ResponseInfo<String> test(@RequestBody TestBody testBody) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseInfo.ok("test" + testBody.getBody(), user.getUsername());
    }

    @Data
    public static class TestBody {
        private String body;
    }
}
