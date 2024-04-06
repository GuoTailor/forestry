package org.gyh.forestry.config;

import org.gyh.forestry.dto.ResponseInfo;
import org.gyh.forestry.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
}
