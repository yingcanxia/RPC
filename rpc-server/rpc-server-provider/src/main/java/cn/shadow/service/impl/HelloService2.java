package cn.shadow.service.impl;

import cn.shadow.annotation.RPCService;
import cn.shadow.entity.User;
import cn.shadow.service.IHellos;

@RPCService(value=IHellos.class,version = "v2.0")
public class HelloService2 implements IHellos{

	@Override
	public String sayHello(String context) {
		// TODO Auto-generated method stub
		System.out.println("[v2.0]request in sayHello:"+context);
		return "[v2.0]"+context;
	}

	@Override
	public String saveUser(User user) {
		// TODO Auto-generated method stub
		System.out.println("[v2.0]request in saveUser:"+user);
		return "[v2.0]SUCCESS";
	}

	

}
