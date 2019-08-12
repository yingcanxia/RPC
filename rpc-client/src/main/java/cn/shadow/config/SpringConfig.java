package cn.shadow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.shadow.proxy.RPCProxyClient;

@Configuration
public class SpringConfig {
	
	@Bean(name="rpcProxyClient")
	public RPCProxyClient proxyClient() {
		
		return new RPCProxyClient();
	}
}
