package org.gyh.forestry.controlle;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.gyh.forestry.aspect.LogRecord;
import org.gyh.forestry.dto.PageInfo;
import org.gyh.forestry.dto.ResponseInfo;
import org.gyh.forestry.dto.req.AddAnimalRecognitionReq;
import org.gyh.forestry.dto.req.AnimalRecognitionPageReq;
import org.gyh.forestry.dto.resp.AnimalRecognitionResp;
import org.gyh.forestry.dto.resp.RecognitionResp;
import org.gyh.forestry.service.AnimalRecognitionService;
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
@RequestMapping("/animal/recognition")
public class AnimalRecognitionController {
    @Autowired
    private AnimalRecognitionService animalRecognitionService;

    @PostMapping(value = "/recognition", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "识别动物", security = {@SecurityRequirement(name = "Authorization")})
    @LogRecord(model = "动物监测", method = "识别动物")
    public ResponseInfo<List<RecognitionResp>> recognition(@RequestPart("files") List<MultipartFile> files) {
        return ResponseInfo.ok(animalRecognitionService.recognition(files));
    }

    @Operation(summary = "分页查询动物", security = {@SecurityRequirement(name = "Authorization")})
    @PostMapping("/page")
    public PageInfo<AnimalRecognitionResp> selectByPage(@RequestBody AnimalRecognitionPageReq pageReq) {
        return animalRecognitionService.selectByPage(pageReq);
    }

    /**
     * 获取随机的10张图片
     */
    @Operation(summary = "获取随机的10张图片", security = {@SecurityRequirement(name = "Authorization")})
    @GetMapping("/random")
    public ResponseInfo<List<String>> randomImage(@RequestParam("pointName") String pointName) {
        return ResponseInfo.ok(animalRecognitionService.randomImage(pointName));
    }

    @Operation(summary = "获取详情", security = {@SecurityRequirement(name = "Authorization")})
    @GetMapping()
    public ResponseInfo<AnimalRecognitionResp> selectById(@RequestParam Integer id) {
        return ResponseInfo.ok(animalRecognitionService.selectById(id));
    }

    @Operation(summary = "添加动物识别结果", security = {@SecurityRequirement(name = "Authorization")})
    @PostMapping("/add")
    @LogRecord(model = "动物监测", method = "添加动物识别结果")
    public ResponseInfo<List<AnimalRecognitionResp>> addAnimal(@RequestBody List<AddAnimalRecognitionReq> animalReqs) {
        return ResponseInfo.ok(animalRecognitionService.addAnimal(animalReqs));
    }

    @Operation(summary = "算法添加动物识别结果")
    @PostMapping("/addAnimal")
    public ResponseInfo<Boolean> addAnimal(@RequestBody AddAnimalRecognitionReq animalReq) {
        return ResponseInfo.ok(animalRecognitionService.addAnimal(animalReq));
    }
}
