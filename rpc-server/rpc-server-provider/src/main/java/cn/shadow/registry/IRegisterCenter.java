package cn.shadow.registry;

public interface IRegisterCenter {

	/**
	 * 注册服务名称和地址，实现服务管理
	 * @param serviceName
	 * @param serviceAddress
	 */
	public void registry(String serviceName,String serviceAddress);
}
