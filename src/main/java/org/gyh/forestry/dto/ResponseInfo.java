package org.gyh.forestry.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import reactor.core.publisher.Mono;

/**
 * Created by gyh on 2021/2/4
 */
public class ResponseInfo<T> {
    public static final int OK_CODE = 1;
    public static final int FAILED_CODE = 0;
    @Schema(description = "状态码，成功：1，失败：0")
    private Integer code = 1;
    @Schema(description = "消息")
    private String msg;
    @Schema(description = "数据")
    private T data;

    public ResponseInfo(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResponseInfo(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseInfo() {
    }

    public static <T> ResponseInfo<T> ok() {
        return new ResponseInfo<>(OK_CODE, "成功");
    }

    public static <T> ResponseInfo<T> ok(String msg) {
        return new ResponseInfo<>(OK_CODE, msg);
    }

    public static <T> ResponseInfo<T> ok(T data) {
        return new ResponseInfo<>(OK_CODE, "成功", data);
    }


    public static <T> ResponseInfo<T> ok(String msg, T data) {
        return new ResponseInfo<>(OK_CODE, msg, data);
    }

    public static <T> ResponseInfo<T> failed() {
        return new ResponseInfo<>(FAILED_CODE, "失败");
    }

    public static <T> ResponseInfo<T> failed(String msg) {
        return new ResponseInfo<>(FAILED_CODE, msg);
    }

    public static <T> ResponseInfo<T> failed(int code, String msg) {
        return new ResponseInfo<>(code, msg);
    }

    public Mono<ResponseInfo<T>> toMono() {
        return Mono.just(this);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseInfo{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
