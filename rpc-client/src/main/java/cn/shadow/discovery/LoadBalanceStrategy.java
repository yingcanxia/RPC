package cn.shadow.discovery;

import java.util.List;

public interface LoadBalanceStrategy {

	public String selectHost(List<String>repo);
}