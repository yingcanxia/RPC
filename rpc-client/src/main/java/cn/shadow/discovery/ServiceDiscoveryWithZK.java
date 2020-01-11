package cn.shadow.discovery;

import java.util.ArrayList;
import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class ServiceDiscoveryWithZK implements IServiceDiscovery{
	List<String>serviceRepos=new ArrayList<String>();
	CuratorFramework curatorFramework=null;
	{
		curatorFramework=CuratorFrameworkFactory.builder()
				.connectString(ZKConfig.CONNECT_STRING)
				.sessionTimeoutMs(5000)
				.retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();//衰减式的重试
		curatorFramework.start();
	}
	/**
	 * 实现服务的获取与查找
	 * 需要设置监听
	 * 需要做服务地址的本地缓存
	 */
	@Override
	public String discovery(String serviceName) {
		//完成了服务地址的查找（服务地址被删除）
		String path="/"+serviceName;
		try {
			serviceRepos=curatorFramework.getChildren().forPath(path);
			registryWatch(path);
			//完成了服务地址的查找
			//服务地址发生变化针对已有的地址进行负载均衡
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LoadBalanceStrategy loadBalanceStrategy=new RandomLoadBalance();
		return loadBalanceStrategy.selectHost(serviceRepos);
	}

	private void registryWatch(final String path) throws Exception {
		PathChildrenCache nodeCache=new PathChildrenCache(curatorFramework, "/data",true);
		PathChildrenCacheListener nodeListener=(curatorFramework1,pathChildrenCacheEvent)->{
			//拉姆达表达式:NodeCacheListener是一个接口new一个接口需要自己实现他的方法不是应表达式的方法如下
			serviceRepos=curatorFramework1.getChildren().forPath(path);//再次更新本地缓存地址
		};
		nodeCache.getListenable().addListener(nodeListener);
		nodeCache.start(PathChildrenCache.StartMode.NORMAL);
	}
}
