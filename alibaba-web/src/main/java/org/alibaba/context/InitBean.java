package org.alibaba.context;

import javax.annotation.Resource;

import org.alibaba.service.CommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
@Component
public class InitBean implements InitializingBean, DisposableBean{
	@Resource
	private CommonService commonService;
	private static final Logger logger = LoggerFactory.getLogger(InitBean.class);
	@Override
	public void destroy() throws Exception {
		
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("项目路径：" + System.getProperty("webPath"));
	}

}
