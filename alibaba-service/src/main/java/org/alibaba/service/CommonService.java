package org.alibaba.service;


public interface CommonService {
	public <T> T toBean(String json, Class<T> cla);
	void test();
	public String annotationAspectTest(String testStr);
}
