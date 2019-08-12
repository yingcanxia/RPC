package cn.shadow.request;

import java.io.Serializable;

public class RpcRequest implements Serializable{//传入的请求
	private String className;
	private String methodName;
	private Object[] paramters;
	private String version;
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public Object[] getParamters() {
		return paramters;
	}
	public void setParamters(Object[] paramters) {
		this.paramters = paramters;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	 
}
