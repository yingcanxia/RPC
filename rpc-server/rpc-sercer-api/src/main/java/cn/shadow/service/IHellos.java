package cn.shadow.service;

import cn.shadow.entity.User;

public interface IHellos {//提供接口
	String sayHello(String context);
	String saveUser(User user);
}
