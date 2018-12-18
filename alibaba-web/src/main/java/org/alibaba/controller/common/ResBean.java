package org.alibaba.controller.common;
public class ResBean {
	
	private String errorCode;
	
	private String msg;
	
	private Object datas;

	public ResBean() {
		this.errorCode = ResEnum.SUCCESS.getCode();
		this.msg = ResEnum.SUCCESS.getMsg();
		this.datas = new Object();
	}
	
 
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}


	public Object getDatas() {
		return datas;
	}


	public void setDatas(Object datas) {
		this.datas = datas;
	}
}
