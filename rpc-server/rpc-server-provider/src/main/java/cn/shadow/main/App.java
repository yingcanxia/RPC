package cn.shadow.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import cn.shadow.config.SpringConfig;
import cn.shadow.proxy.RPCProxyServer;
import cn.shadow.service.IHellos;
import cn.shadow.service.impl.HelloService;

public class App {
	public static void main(String[] args) {
		/*
		 * IHellos helloService=new HelloService(); RPCProxyServer rpcProxyServer=new
		 * RPCProxyServer(); rpcProxyServer.publisher(helloService, 8088);
		 */
		ApplicationContext context=new AnnotationConfigApplicationContext(SpringConfig.class);
		((AbstractApplicationContext) context).start();
		
	}
}
