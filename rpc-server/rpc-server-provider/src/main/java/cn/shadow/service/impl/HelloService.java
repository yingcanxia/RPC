package cn.shadow.service.impl;

import cn.shadow.annotation.RPCService;
import cn.shadow.entity.User;
import cn.shadow.service.IHellos;

@RPCService(value=IHellos.class,version = "v1.0")
public class HelloService implements IHellos{

	@Override
	public String sayHello(String context) {
		// TODO Auto-generated method stub
		System.out.println("[v1.0]request in sayHello:"+context);
		return "[v1.0]"+context;
	}

	@Override
	public String saveUser(User user) {
		// TODO Auto-generated method stub
		System.out.println("[v1.0]request in saveUser:"+user);
		return "[v1.0]SUCCESS";
	}

	

}
