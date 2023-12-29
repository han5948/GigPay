package com.nemo.kr.core;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


import org.springframework.stereotype.Component;


/** 
 *  통계 디비로 접속할경우 기존디비와 구분하기위한 어노테이션
 * 	Mapper interface에서 @SumMapper(ServiceImpl Class의 @Resource Name) 으로 스캔 
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface MessageMapper {
	 String value() default "";
}