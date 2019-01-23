package org.alibaba.Converters;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 解决使用fastjson swagger使用不了的问题
* @author ZC  
* @date 2018年10月31日 下午2:53:36
* @version
 */
public class FastJsonHttpMessageConverterEx extends FastJsonHttpMessageConverter {
	 private ObjectMapper mapper = new ObjectMapper();  
	 
	 
	@Override
	public void write(Object t, Type type, MediaType contentType, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		if (type == springfox.documentation.swagger.web.UiConfiguration.class) {  
            HttpHeaders headers = outputMessage.getHeaders();  
            ByteArrayOutputStream outnew = new ByteArrayOutputStream();  
            mapper.writeValue(outnew, t);  
            outnew.flush();  
            headers.setContentLength(outnew.size());  
            OutputStream out = outputMessage.getBody();  
            outnew.writeTo(out);  
            outnew.close();  
        } else {  
            super.write(t, type, contentType, outputMessage);  
        }  
	}

}
