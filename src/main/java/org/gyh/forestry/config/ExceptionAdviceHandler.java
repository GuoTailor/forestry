package org.gyh.forestry.config;

import org.gyh.forestry.dto.ResponseInfo;
import org.gyh.forestry.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * 所有状态码返回200根据响应判断是否失败
 */
@Slf4j
@RestControllerAdvice
public class ExceptionAdviceHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseInfo<Void> handleError(BusinessException e) {
        log.error("业务异常:{}", e.getMessage());
        return ResponseInfo.failed(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseInfo<Void> handleError(MethodArgumentNotValidException e) {
        return argsError(e.getBindingResult().getFieldErrors());
    }

    private ResponseInfo<Void> argsError(List<FieldError> fieldErrors) {
        StringBuilder stringBuilder = new StringBuilder();
        for (FieldError fieldError : fieldErrors) {
            stringBuilder.append("[").append(fieldError.getField()).append("] ")
                    .append(fieldError.getDefaultMessage()).append(",");
        }
        ResponseInfo<Void> baseResp = new ResponseInfo<>();
        baseResp.setCode(ResponseInfo.FAILED_CODE);
        baseResp.setMsg(stringBuilder.toString());
        return baseResp;
    }
}
