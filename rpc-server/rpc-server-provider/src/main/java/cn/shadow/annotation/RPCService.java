package cn.shadow.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

@Target(ElementType.TYPE)//���÷�Χ��\�ӿ�
@Retention(RetentionPolicy.RUNTIME)//��ʾ��Ч��Χ������������ʱ��״̬
@Component//����ʱ�ᱻspringɨ��
public @interface RPCService {
	//�õ����������ӿ�
	Class<?> value();
	String version() default "";
}
