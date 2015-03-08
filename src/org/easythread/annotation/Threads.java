package org.easythread.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标注此注解代表线程运行的方法，参数 num 代表运行多少这样的线程
 * 
 * @author cong
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Threads {
	public int num() default 1;
}
