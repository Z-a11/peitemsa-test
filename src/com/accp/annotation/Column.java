package com.accp.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
public @interface Column {
	 String name();
	  String type() default "varchar";
	  boolean isPrimaryKey() default false;   //是否是主键
	  boolean isnull() default true;   //是否为空
	  boolean isIdentity() default false;	//是否自增
	  boolean isForeignKey() default false; //外键
	  int length() default 20;//长度默认为20
}
