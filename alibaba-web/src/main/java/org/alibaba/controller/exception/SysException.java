package org.alibaba.controller.exception;

public class SysException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7927259241529623631L;
	private String code;//状态码
    public SysException(String message, String code) {
        super(message);
        this.code = code;
    }
    public String getCode() {
        return code;
    }

}
