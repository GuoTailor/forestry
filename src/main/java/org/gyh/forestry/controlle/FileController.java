package org.gyh.forestry.controlle;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.gyh.forestry.dto.ResponseInfo;
import org.gyh.forestry.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * create by GYH on 2024/3/27
 */
@Tag(name = "文件")
@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    private FileService fileService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "上传文件", security = {@SecurityRequirement(name = "Authorization")})
    public ResponseInfo<String> uploadFile(@RequestPart("file") MultipartFile file) {
        return ResponseInfo.ok(fileService.uploadFile(file));
    }

    @PostMapping(value = "/upload/img", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "上传文件", security = {@SecurityRequirement(name = "Authorization")})
    public ResponseInfo<String> uploadImage(@RequestPart("file") MultipartFile file, @RequestPart("x") double x, @RequestPart("y") double y) {
        String msg = fileService.uploadFile(file);
        redisTemplate.opsForValue().set(msg, x + "," + y);
        return ResponseInfo.ok(msg);
    }
}
