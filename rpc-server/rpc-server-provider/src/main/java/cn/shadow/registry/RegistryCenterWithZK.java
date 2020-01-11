package cn.shadow.registry;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

public class RegistryCenterWithZK implements IRegisterCenter{
	
	CuratorFramework curatorFramework=null;
	{
		curatorFramework=CuratorFrameworkFactory.builder()
				.connectString(ZKConfig.CONNECT_STRING)
				.sessionTimeoutMs(5000)
				.retryPolicy(new ExponentialBackoffRetry(1000, 3))
				//命名空间
				.namespace("registry")
				.build();//衰减式的重试
		curatorFramework.start();
	}
	@Override
	public void registry(String serviceName, String serviceAddress) {
		// TODO Auto-generated method stub
		String servicePath=serviceName;
		try {
			//判断该节点是否存在
			if(curatorFramework.checkExists().forPath(servicePath)==null) {
				curatorFramework.create().creatingParentContainersIfNeeded()
				.withMode(CreateMode.PERSISTENT)
				.forPath(servicePath);
			}
			//serviceAddress这个玩意对应的是IP和端口号
			String addressPath=servicePath+"/"+serviceAddress;
			curatorFramework.create().withMode(CreateMode.EPHEMERAL).forPath(addressPath);
			System.out.println("服务注册成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		/* createData(curatorFramework); */
	}

}
