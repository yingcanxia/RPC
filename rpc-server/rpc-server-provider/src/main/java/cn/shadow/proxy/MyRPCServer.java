package cn.shadow.proxy;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import cn.shadow.annotation.RPCService;
import cn.shadow.handler.ProcessorHandler;
import cn.shadow.handler.ProcessorHandler2;

@Component
@ComponentScan(basePackages = "cn.shadow")
public class MyRPCServer implements ApplicationContextAware,InitializingBean{

	ExecutorService executorService=Executors.newCachedThreadPool();
	private int port;
	private Map<String, Object>handlerMap=new HashMap<String, Object>();
	public MyRPCServer(int port) {
		this.port = port;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		ServerSocket serverSocket=null;
		try {
			serverSocket=new ServerSocket(port);
			while(true) {
				Socket socket=serverSocket.accept();//Á´½Ó×èÈûBIOÄ£Ê½
				executorService.execute(new ProcessorHandler2(handlerMap,socket));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(serverSocket!=null) {
				
				try {
					serverSocket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		Map<String,Object> serviceBeanMap= applicationContext.getBeansWithAnnotation(RPCService.class);
		if(!serviceBeanMap.isEmpty()) {
			for(Object serverBean:serviceBeanMap.values()) {
				RPCService rpcService=serverBean.getClass().getAnnotation(RPCService.class);
				String serviceName=rpcService.value().getName();
				String version=rpcService.version();
				if(!StringUtils.isEmpty(version)) {
					serviceName+="-"+version;
				}
				handlerMap.put(serviceName, serverBean);
			}
		}
	}

}
