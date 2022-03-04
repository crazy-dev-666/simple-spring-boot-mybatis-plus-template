package cn.dev666.simple.template.annotation;

import java.lang.annotation.*;

/**
 *  支持接口匿名访问
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Anonymous {
}
