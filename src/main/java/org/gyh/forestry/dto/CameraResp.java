package org.gyh.forestry.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * create by GYH on 2024/4/26
 */
@Data
public class CameraResp {
    @JsonProperty("Code")
    private String code;
    private String msg;

    public static CameraResp ok() {
        CameraResp cameraResp = new CameraResp();
        cameraResp.setCode("1");
        cameraResp.setMsg("success");
        return cameraResp;
    }

    public static CameraResp ok(String msg) {
        CameraResp cameraResp = new CameraResp();
        cameraResp.setCode("1");
        cameraResp.setMsg(msg);
        return cameraResp;
    }

    public static CameraResp failed() {
        CameraResp cameraResp = new CameraResp();
        cameraResp.setCode("0");
        cameraResp.setMsg("failed");
        return cameraResp;
    }

    public static CameraResp failed(String msg) {
        CameraResp cameraResp = new CameraResp();
        cameraResp.setCode("0");
        cameraResp.setMsg(msg);
        return cameraResp;
    }

}
