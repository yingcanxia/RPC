package cn.shadow.handler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

import cn.shadow.request.RpcRequest;

public class ProcessorHandler implements Runnable{
	private Socket socket;
	private Object service;
	public ProcessorHandler(Object service, Socket socket) {
		this.socket = socket;
		this.service=service;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		ObjectInputStream inputStream=null;
		ObjectOutputStream outputStream=null;
		try {
			inputStream=new ObjectInputStream(socket.getInputStream());
			RpcRequest request=(RpcRequest)inputStream.readObject();
			Object result=invoke(request);//获取相应的请求内容，并执行
			//将执行结果进行缓存
			outputStream=new ObjectOutputStream(socket.getOutputStream());
			outputStream.writeObject(result);
			outputStream.flush();
			//请求那个类，请求什么方法，
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
		//反射调用
		Object result=null;
		Object[]args=rpcRequest.getParamters();
		String className=rpcRequest.getClassName();
		String methodName=rpcRequest.getMethodName();
		Class<?>[]types=new Class[args.length];
		for(int i=0;i<args.length;i++) {
			types[i]=args[i].getClass();
		}
		try {
			Class clazz=Class.forName(className);
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
