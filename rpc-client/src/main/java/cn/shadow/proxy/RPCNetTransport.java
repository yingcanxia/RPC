package cn.shadow.proxy;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import cn.shadow.request.RpcRequest;

public class RPCNetTransport {
	private String serviceAddress;
	
	public RPCNetTransport(String serviceAddress) {
		this.serviceAddress=serviceAddress;
	}
	public Object send(RpcRequest request) {
		Socket socket=null;
		Object result=null;
		ObjectInputStream inputStream=null;
		ObjectOutputStream outputStream=null;
		try {
			String urls[]=serviceAddress.split(":");
			socket=new Socket(urls[0], Integer.parseInt(urls[1]));
			outputStream=new ObjectOutputStream(socket.getOutputStream());
			outputStream.writeObject(request);
			outputStream.flush();
			
			inputStream=new ObjectInputStream(socket.getInputStream());
			result=inputStream.readObject();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		return result;
	}
}
