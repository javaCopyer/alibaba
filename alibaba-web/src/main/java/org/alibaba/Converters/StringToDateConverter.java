package org.alibaba.Converters;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

public class StringToDateConverter implements Converter<String, Date>{
	@Override
	public Date convert(String s) {
		if(StringUtils.isBlank(s)) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
