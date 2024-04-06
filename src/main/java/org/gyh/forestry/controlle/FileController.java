package org.gyh.forestry.controlle;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.gyh.forestry.dto.ResponseInfo;
import org.gyh.forestry.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @PostMapping("/upload")
    @Operation(summary = "上传文件", security = {@SecurityRequirement(name = "Authorization")})
    public ResponseInfo<?> uploadFile(MultipartFile file) {
        fileService.uploadFile(file);
        return ResponseInfo.ok();
    }
}
