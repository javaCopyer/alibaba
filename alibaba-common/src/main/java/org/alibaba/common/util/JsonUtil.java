package org.alibaba.common.util;

import com.alibaba.fastjson.JSON;

public class JsonUtil {
	public static <T> T toBean(String json, Class<T> cla) {
		return JSON.parseObject(json, cla);
	}
}
