package cn.shadow.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import cn.shadow.config.SpringConfig;
import cn.shadow.proxy.RPCProxyClient;
import cn.shadow.service.IHellos;

public class App {
	public static void main(String[] args) {
		/*
		 * RPCProxyClient client=new RPCProxyClient(); 
		 * IHellos hellos=client.clientProxy(IHellos.class, "localhost", 8088);
		 * String result=hellos.sayHello("666"); System.out.println(result);
		 */
		ApplicationContext context=new AnnotationConfigApplicationContext(SpringConfig.class);
		RPCProxyClient proxyClient=context.getBean(RPCProxyClient.class);
		IHellos hellos=proxyClient.clientProxy(IHellos.class, "localhost", 8088);
		String result=hellos.sayHello("666");
		System.out.println(result);
	}
}
