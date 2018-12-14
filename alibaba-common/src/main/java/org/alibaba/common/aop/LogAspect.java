package org.alibaba.common.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.alibaba.common.annotation.LogAdd;
import org.apache.commons.lang.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.alibaba.druid.util.StringUtils;
@Component
@Aspect
public class LogAspect{
	 @Pointcut("@annotation(org.alibaba.common.annotation.LogAdd)")  
     public  void log() {}  
	 
	 
	 @Before("log()")  
     public void doBefore(JoinPoint joinPoint) { 
		 String className = joinPoint.getTarget().getClass().getName();
		 String methodName = joinPoint.getSignature().getName();
		 String args = Arrays.toString(joinPoint.getArgs());
		 LogAdd logAdd = getAnnotation(joinPoint, methodName, LogAdd.class);
		 String title = logAdd.title();
		 String opt = logAdd.opt();
		 System.out.println("前置通知：" + title+ "," + opt + "执行代码：" + className + "." + methodName + "(" +args +")");
	 }
	 
	 @AfterReturning(pointcut="log()",returning="result")
	 public void AfterReturning(JoinPoint joinPoint, Object result) { 
		 System.out.println("后置通知：" + "运行没有异常返回" + result.toString());
	 }
	 
	 @AfterThrowing(pointcut="log()",throwing="e")
	 public void AfterReturning(JoinPoint joinPoint, Throwable e) { 
		 System.out.println("运行异常通知：" + "运行异常返回" + e.getMessage());
	 }
	 
	 /**
	  * 
	  * @param joinPoint
	  * @param methodName
	  * @param annotationClass
	  * @return
	  * @author ZC
	  * @date 2018年9月21日 上午11:17:36
	  */
	 private <T extends Annotation> T getAnnotation(JoinPoint joinPoint, String methodName, Class<T> annotationClass) {
		 Method[] methods = joinPoint.getTarget().getClass().getMethods();
		 if(ArrayUtils.isNotEmpty(methods)) {
			 for (Method method : methods) {
				if(StringUtils.equals(methodName, method.getName())) {
					return method.getAnnotation(annotationClass);
				}
			}
		 }
		 return null;
	 }

}
