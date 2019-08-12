package cn.shadow.handler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import cn.shadow.request.RpcRequest;

public class ProcessorHandler2 implements Runnable{
	private Socket socket;
	private Map<String, Object>handlerMap;
	public ProcessorHandler2(Map<String, Object>handlerMap, Socket socket) {
		this.socket = socket;
		this.handlerMap=handlerMap;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		ObjectInputStream inputStream=null;
		ObjectOutputStream outputStream=null;
		try {
			inputStream=new ObjectInputStream(socket.getInputStream());
			RpcRequest request=(RpcRequest)inputStream.readObject();
			Object result=invoke(request);//��ȡ��Ӧ���������ݣ���ִ��
			//��ִ�н�����л���
			outputStream=new ObjectOutputStream(socket.getOutputStream());
			outputStream.writeObject(result);
			outputStream.flush();
			//�����Ǹ��࣬����ʲô������
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(inputStream!=null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(outputStream!=null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	private Object invoke(RpcRequest rpcRequest) {
		//�������
		String serviceName=rpcRequest.getClassName();
		String methodName=rpcRequest.getMethodName();
		String version=rpcRequest.getVersion();
		Object[]args=rpcRequest.getParamters();
		if(!StringUtils.isEmpty(version)) {
			serviceName+="-"+version;
		}
		Object service=handlerMap.get(serviceName);
		if(service==null) {
			throw new RuntimeException("service not found"+serviceName);
		}
		
		Object result=null;
		Class<?>[]types=new Class[args.length];
		for(int i=0;i<args.length;i++) {
			types[i]=args[i].getClass();
		}
		serviceName=serviceName.split("-")[0];
		try {
			Class clazz=Class.forName(serviceName);
			Method method= clazz.getMethod(methodName, types);
			result=method.invoke(service, args);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
}
