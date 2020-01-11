package cn.shadow.discovery;

import java.util.List;
import java.util.Random;

public class RandomLoadBalance extends AbstractLoadBalance{

	@Override
	protected String doSelect(List<String> repo) {
		// TODO Auto-generated method stub
		int length=repo.size();
		Random random=new Random();//从repo中随机获取一个地方
		return repo.get(random.nextInt(length));
	}

}
