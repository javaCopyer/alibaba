package org.alibaba.controller.common;

public enum ResEnum {
	/**
	 * 通用
	 */
	SUCCESS("000000","操作成功!"),
	FAILURE("000001","服务器异常,请重试!"),
	PARAMS_IS_NULL("000002","参数非空"),
	NOT_ENOUGH_MONEY("000003","余额不足"),
	NOT_PERMISSION("000004","无权限访问"),
	NOT_LOGIN("000005","尚未登录"),
	NOT_LOCATION_LOGIN("000007","异地登录");
	
	private String code;
	private String msg;
	
	private ResEnum(String code,String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
