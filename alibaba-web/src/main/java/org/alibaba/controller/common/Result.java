package org.alibaba.controller.common;

import java.util.HashMap;
import java.util.Map;


/**
 * 响应数据包装
 * @author sky
 *
 */
public class Result extends HashMap<String, Object>{
	public static final String CODE = "code";
	public static final String MSG = "msg";
	private static final long serialVersionUID = 375538241045996923L;

	public Result() {
		this.put(CODE, 0);
	}
	
	public static Result error() {
        return error(500, "系统发生错误，请重试或联系管理员解决！");
    }
	
	public static Result error(String msg) {
		return error(500, msg);
	}
	
	public static Result error(int code, String msg) {
		Result result = new Result();
		result.put(CODE, code);
		result.put(MSG, msg);
		return result;
	}

	 public static Result ok(String msg) {
		 Result r = new Result();
	     r.put("msg", msg);
	     return r;
	 }

    public static Result ok(Map<String, Object> map) {
    	Result r = new Result();
        r.putAll(map);
        return r;
    }

    public static Result ok() {
        return new Result();
    }

    @Override
    public Result put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
