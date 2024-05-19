package org.gyh.forestry.controlle;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.gyh.forestry.dto.CameraResp;
import org.gyh.forestry.dto.req.CameraInfoReq;
import org.gyh.forestry.dto.req.CameraUploadReq;
import org.springframework.web.bind.annotation.*;

/**
 * create by GYH on 2024/4/26
 */
@Tag(name = "红外相机")
@RestController
@RequestMapping("/lnfraredCamera")
public class LnfraredCameraController {

    @PostMapping("/UploadInfo")
    @Operation(summary = "图片（视频）上传接口参数")
    public CameraResp uploadInfo(CameraUploadReq uploadReq) {

        return CameraResp.ok(uploadReq.toString());
    }

    @PostMapping("/CamerasInfo")
    @Operation(summary = "相机状态（每天心跳API）上报")
    public CameraResp CamerasInfo(CameraInfoReq cameraInfoReq) {
        return CameraResp.ok(cameraInfoReq.toString());
    }

    @GetMapping("/SetCode/imei/{ImeiVal}")
    @Operation(summary = "设置参数设置后， 相机端的获取")
    public String getConfig(@PathVariable("ImeiVal") String ImeiVal) {
        return "#E#i0#cp#s14#fl#v5#T2024:01:09:09:30##";
    }
}
