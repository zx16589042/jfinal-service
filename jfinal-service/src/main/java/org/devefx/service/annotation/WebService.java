package org.devefx.service.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Inherited
@Target(value=ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface WebService {
	public String value() default "";
	public Parameter[] parameter() default {};
	public @interface Parameter {
		public String value();
		public boolean required() default false;
		public String type() default "string";
		public String description() default "";
	}
}
