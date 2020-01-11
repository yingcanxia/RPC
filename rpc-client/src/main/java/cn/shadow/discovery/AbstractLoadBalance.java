package cn.shadow.discovery;

import java.util.List;

public abstract class AbstractLoadBalance implements LoadBalanceStrategy{

	@Override
	public String selectHost(List<String> repo) {
		// TODO Auto-generated method stub
		if(repo==null||repo.size()==0) {
			return null;
		}
		if(repo.size()==1) {
			return repo.get(0);
		}else {
			
		}
		
		return null;
	}

	protected abstract String doSelect(List<String> repo);
}
