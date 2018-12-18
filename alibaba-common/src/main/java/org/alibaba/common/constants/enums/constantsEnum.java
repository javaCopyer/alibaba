package org.alibaba.common.constants.enums;

public enum constantsEnum {
	LOG_OPT_TYPE_DEL("1", "删除");
	
	private String code;
	private String msg;
	private constantsEnum(String code, String msg) {
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
