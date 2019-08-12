package cn.shadow.proxy;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import cn.shadow.request.RpcRequest;

public class RPCNetTransport {
	private String host;
	private int port;
	public RPCNetTransport(String host, int port) {
		this.host = host;
		this.port = port;
	}
	public Object send(RpcRequest request) {
		Socket socket=null;
		Object result=null;
		ObjectInputStream inputStream=null;
		ObjectOutputStream outputStream=null;
		try {
			socket=new Socket(host, port);
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
