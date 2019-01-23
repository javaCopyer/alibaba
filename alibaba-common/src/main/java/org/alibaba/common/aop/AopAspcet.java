package org.alibaba.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AopAspcet {
	private Logger logger = LoggerFactory.getLogger(AopAspcet.class);
	public void doAccessCheck(JoinPoint jp) {
		logger.info("前置通知");
    }

    
    public void doAfter(JoinPoint jp) {
        logger.info("后置通知");
    }

    
    public void after(JoinPoint jp) {
        logger.info("最终通知");
    }

    
    public void doAfterThrow(JoinPoint jp,Throwable ex) {
        logger.info("例外通知");
    }


    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
        logger.info("进入环绕通知");
        Object object = pjp.proceed();// 执行该方法
        logger.info("退出方法");
        return object;
    }

    
    public Object doBasic(ProceedingJoinPoint pjp) throws Throwable {
        logger.info("进入环绕通知...");
        Object object = pjp.proceed();// 执行该方法
        logger.info("退出方法...");
        return object;
    }
}
