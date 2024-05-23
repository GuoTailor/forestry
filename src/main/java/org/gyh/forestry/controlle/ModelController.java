package org.gyh.forestry.controlle;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.gyh.forestry.dto.ResponseInfo;
import org.gyh.forestry.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * create by GYH on 2024/5/23
 */
@Tag(name = "模型管理")
@RestController
@RequestMapping("/model")
public class ModelController {
    @Autowired
    private ModelService modelService;

    @PostMapping("/upload")
    @Operation(summary = "上传模型", security = {@SecurityRequirement(name = "Authorization")}, parameters = {
            @Parameter(name = "name", description = "模型名字", required = true),
            @Parameter(name = "file", description = "模型文件", required = true)})
    public ResponseInfo<Boolean> uploadModel(String name, MultipartFile file) {
        return ResponseInfo.ok(true);
    }
}
