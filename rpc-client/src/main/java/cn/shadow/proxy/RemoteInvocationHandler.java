package cn.shadow.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import cn.shadow.request.RpcRequest;

public class RemoteInvocationHandler implements InvocationHandler{
	
	private String host;
	private int port;
	
	public RemoteInvocationHandler(String host, int port) {
		this.host = host;
		this.port = port;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// TODO Auto-generated method stub
		System.out.println("进入");
		//请求数据额包装
		RpcRequest request=new RpcRequest();
		request.setClassName("cn.shadow.service.IHellos");
		request.setMethodName("sayHello");
		request.setParamters(args);
		request.setVersion("v1.0");
		//此处通过远程通信
		RPCNetTransport netTransport=new RPCNetTransport(host, port);
		Object result=netTransport.send(request);
		return result;
	}

}
