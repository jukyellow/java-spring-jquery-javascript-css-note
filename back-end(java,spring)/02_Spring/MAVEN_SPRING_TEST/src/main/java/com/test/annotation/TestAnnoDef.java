package com.test.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(value={java.lang.annotation.ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface TestAnnoDef {
	public String value() default "test annotation val";
	public String name() default "test annotation name";
	Class<? extends TestJob>[] jobs() default {};
}
