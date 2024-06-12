package org.gyh.forestry.controlle;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.gyh.forestry.aspect.LogRecord;
import org.gyh.forestry.domain.Animal;
import org.gyh.forestry.dto.PageInfo;
import org.gyh.forestry.dto.ResponseInfo;
import org.gyh.forestry.dto.req.AddAnimalReq;
import org.gyh.forestry.dto.req.AnimalPageReq;
import org.gyh.forestry.dto.resp.AnimalResp;
import org.gyh.forestry.dto.resp.RecognitionResp;
import org.gyh.forestry.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * create by GYH on 2024/3/30
 */
@Tag(name = "动物监测")
@RestController
@RequestMapping("/animal")
public class AnimalController {
    @Autowired
    private AnimalService animalService;

    @PostMapping(value = "/recognition", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "识别动物", security = {@SecurityRequirement(name = "Authorization")})
    @LogRecord(model = "动物监测", method = "识别动物")
    public ResponseInfo<List<RecognitionResp>> recognition(@RequestPart("files") List<MultipartFile> files) {
        return ResponseInfo.ok(animalService.recognition(files));
    }

    @Operation(summary = "分页查询动物", security = {@SecurityRequirement(name = "Authorization")})
    @PostMapping("/page")
    public PageInfo<AnimalResp> selectByPage(@RequestBody AnimalPageReq pageReq) {
        return animalService.selectByPage(pageReq);
    }

    @Operation(summary = "获取详情", security = {@SecurityRequirement(name = "Authorization")})
    @GetMapping()
    public ResponseInfo<AnimalResp> selectById(@RequestParam Integer id) {
        return ResponseInfo.ok(animalService.selectById(id));
    }

    @Operation(summary = "添加动物识别结果", security = {@SecurityRequirement(name = "Authorization")})
    @PostMapping("/add")
    @LogRecord(model = "动物监测", method = "添加动物识别结果")
    public ResponseInfo<List<AnimalResp>> addAnimal(@RequestBody List<AddAnimalReq> animalReqs) {
        return ResponseInfo.ok(animalService.addAnimal(animalReqs));
    }
}
