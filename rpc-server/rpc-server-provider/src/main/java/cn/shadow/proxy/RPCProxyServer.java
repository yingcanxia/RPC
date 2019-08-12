package cn.shadow.proxy;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.shadow.handler.ProcessorHandler;

public class RPCProxyServer {
	ExecutorService executorService=Executors.newCachedThreadPool();
	public void publisher(Object service,int port) {
		ServerSocket serverSocket=null;
		try {
			serverSocket=new ServerSocket(port);
			while(true) {
				Socket socket=serverSocket.accept();//Á´½Ó×èÈûBIOÄ£Ê½
				executorService.execute(new ProcessorHandler(service,socket));
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
}
