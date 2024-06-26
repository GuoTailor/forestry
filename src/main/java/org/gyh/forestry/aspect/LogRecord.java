package org.gyh.forestry.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * create by GYH on 2024/3/27
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogRecord {
    /**
     * 模块名字
     */
    String model();

    /**
     * 操作类型
     */
    String method();
}
