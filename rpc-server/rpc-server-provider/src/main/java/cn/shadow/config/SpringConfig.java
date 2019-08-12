package cn.shadow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import cn.shadow.proxy.MyRPCServer;

@Configuration
@ComponentScan(basePackages = "cn.shadow")
public class SpringConfig {
	@Bean(name="myRPCServer")
	public MyRPCServer getMyRPCServer() {
		return new MyRPCServer(8088);
		
	}
}
