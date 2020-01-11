package cn.shadow.proxy;

import java.lang.reflect.Proxy;

import cn.shadow.discovery.IServiceDiscovery;
import cn.shadow.discovery.ServiceDiscoveryWithZK;

public class RPCProxyClient {
	private IServiceDiscovery serviceDiscovery=new ServiceDiscoveryWithZK();
	
	
	
	public <T> T clientProxy(final Class<T> interfaceClass,String version) {
		return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[] {interfaceClass}, new RemoteInvocationHandler(serviceDiscovery,version));
	}
}
