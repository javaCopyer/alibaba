package org.alibaba.common.util;

import java.io.UnsupportedEncodingException;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.support.RequestContext;

public class MessageI18nUtil {
	public static MessageI18nUtil DEFAULT_INSTANCE;
	public static ResourceBundle BUNDLE = ResourceBundle.getBundle("messages");
	
	/**
	 * 获取单例实例
	 * @param properties
	 * @return
	 */
	public static MessageI18nUtil getInstance(String properties) {
		if (null == DEFAULT_INSTANCE) {
			DEFAULT_INSTANCE = new MessageI18nUtil();
		}
		if (null != properties) {
			BUNDLE = ResourceBundle.getBundle(properties);
		}
		return DEFAULT_INSTANCE;
	}
	
	public static MessageI18nUtil getInstance() {
		if (null == DEFAULT_INSTANCE) {
			DEFAULT_INSTANCE = new MessageI18nUtil();
		}
		return DEFAULT_INSTANCE;
	}
	
   public static String getConfigByName(String name) {
        String value = "";
        try {
            value = new String(BUNDLE.getString(name).getBytes("iso8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return value;
    }
   
   /**
    * 从国际化资源配置文件中根据key获取value  方法二
    * @param request
    * @param key
    * @return
    */
   public static String getMessage(HttpServletRequest request, String key){
       RequestContext requestContext = new RequestContext(request);
       String value = requestContext.getMessage(key);
       return value;
   }

}
