package cn.shadow.discovery;

public interface IServiceDiscovery {
	//根据服务名称返回服务地址
	public String discovery(String serviceName);
}
