package org.alibaba.common.annotation;

import static java.lang.annotation.ElementType.METHOD
;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;


@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface LogAdd {
	public static final String OPT_TYPE_DEL = "删除";
	public static final String OPT_TYPE_EDIT = "修改";
	public static final String OPT_TYPE_INSERT = "新增";
	public static final String OPT_TYPE_SELECT = "查看";
	public String opt() default "";
	public String title() default "";
}
