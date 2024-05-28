package org.gyh.forestry.controlle;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.gyh.forestry.domain.Model;
import org.gyh.forestry.dto.PageInfo;
import org.gyh.forestry.dto.ResponseInfo;
import org.gyh.forestry.dto.req.ModelPageReq;
import org.gyh.forestry.dto.resp.ModelResp;
import org.gyh.forestry.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    public ResponseInfo<Model> uploadModel(String name, MultipartFile file) {
        return ResponseInfo.ok(modelService.uploadModel(name, file));
    }

    /**
     * 启用停用
     */
    @GetMapping("/switch")
    @Operation(summary = "启用或停用模型", security = {@SecurityRequirement(name = "Authorization")}, parameters = {
            @Parameter(name = "id", description = "模型id", required = true),
            @Parameter(name = "enable", description = "true：启用，false：停用", required = true)})
    public ResponseInfo<Model> switchModel(@RequestParam("id") Integer id, @RequestParam("enable") Boolean enable) {
       return ResponseInfo.ok(modelService.switchModel(id, enable));
    }

    @PostMapping("/reupload")
    @Operation(summary = "上传模型", security = {@SecurityRequirement(name = "Authorization")}, parameters = {
            @Parameter(name = "id", description = "模型id", required = true),
            @Parameter(name = "file", description = "模型文件", required = true)})
    public ResponseInfo<Model> reUpload(Integer id, MultipartFile file) {
        return ResponseInfo.ok(modelService.reUpload(id, file));
    }

    @PostMapping("/page")
    @Operation(summary = "上传模型", security = {@SecurityRequirement(name = "Authorization")})
    public PageInfo<ModelResp> getModels(@RequestBody ModelPageReq pageReq) {
        return modelService.getModels(pageReq);
    }
}
