package cn.shadow.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

@Target(ElementType.TYPE)//作用范围类\接口
@Retention(RetentionPolicy.RUNTIME)//表示有效范围，保留到运行时的状态
@Component//启动时会被spring扫描
public @interface RPCService {
	//拿到服务的类与接口
	Class<?> value();
	String version() default "";
}
