package org.alibaba.controller.exception;


import org.alibaba.controller.common.ResBean;
import org.alibaba.controller.common.ResEnum;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(SysException.class) 
	   public ResBean customHandler(SysException e){
			e.printStackTrace();
			ResBean resBean = new ResBean();
			resBean.setErrorCode(ResEnum.FAILURE.getCode());
			resBean.setMsg(e.getMessage());
			return resBean;
	   }
	
	
	   //其他未处理的异常
	   @ExceptionHandler(Exception.class)
	   public Object exceptionHandler(Exception e){
		   e.printStackTrace();
		   	ResBean resBean = new ResBean();
			resBean.setErrorCode(ResEnum.FAILURE.getCode());
			resBean.setMsg(ResEnum.FAILURE.getMsg());
			return resBean;
	   }
}
