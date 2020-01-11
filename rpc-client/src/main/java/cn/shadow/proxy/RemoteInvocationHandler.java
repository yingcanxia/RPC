package cn.shadow.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.springframework.util.StringUtils;

import cn.shadow.discovery.IServiceDiscovery;
import cn.shadow.request.RpcRequest;

public class RemoteInvocationHandler implements InvocationHandler{
	
	private IServiceDiscovery serviceDiscovery;
	private String version;
	
	
	public RemoteInvocationHandler(IServiceDiscovery serviceDiscovery,String version) {
		this.serviceDiscovery=serviceDiscovery;
		this.version=version;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// TODO Auto-generated method stub
		System.out.println("����");
		//�������ݶ��װ
		RpcRequest request=new RpcRequest();
		request.setClassName("cn.shadow.service.IHellos");
		request.setMethodName("sayHello");
		request.setParamters(args);
		request.setVersion(version);
		//�˴�ͨ��Զ��ͨ��
		String serviceName=request.getClassName();
		if(!StringUtils.isEmpty(version)) {
			serviceName=serviceName+"-"+version;
		}
		String serviceAddress=serviceDiscovery.discovery(serviceName);
		RPCNetTransport netTransport=new RPCNetTransport(serviceAddress);
		Object result=netTransport.send(request);
		return result;
	}

}
