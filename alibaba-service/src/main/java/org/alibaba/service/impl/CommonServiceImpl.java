package org.alibaba.service.impl;

import org.alibaba.common.annotation.LogAdd;
import org.alibaba.common.util.JsonUtil;
import org.alibaba.service.CommonService;
import org.springframework.stereotype.Service;
@Service
public class CommonServiceImpl implements CommonService{

	@Override
	public <T> T toBean(String json, Class<T> cla) {
		return JsonUtil.toBean(json, cla);
	}

	@Override
	public void test() {
		System.out.println("测试CommonServiceImpl方法 ========");
	}
	@Override
	@LogAdd(opt=LogAdd.OPT_TYPE_SELECT,title="测试注解AOP")
	public String annotationAspectTest(String testStr) {
		System.out.println("测试annotationAspectTest方法 ========");
		return "测试返回数据";
	}

}
