package org.alibaba.common.util;

import java.lang.reflect.Field;

import java.text.NumberFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * list排序类
* @author ZC  
* @date 2018年9月19日 下午2:42:53
* @version
 */
public class SortUtil {
	
	
	/**
	 * 给数字对象左边按照指定长度添加0
	 * @param numObj 数字对象
	 * @param length 指定长度
	 * @return
	 */
	public static String addZero2Str(Number numObj, int length) {
		NumberFormat nf = NumberFormat.getInstance();
		// 设置是否使用分组
		nf.setGroupingUsed(false);
		// 设置最大整数位数
		nf.setMaximumIntegerDigits(length);
		// 设置最小整数位数
		nf.setMinimumIntegerDigits(length);
		return nf.format(numObj);
	}
	
	/**
	 * 根据属性数组排序
	 * @param list
	 * @param sortNameArr
	 * @param isAsc true升序 false降序
	 */
	public static <T> void listSort(List<T> list, String[] sortNameArr, boolean isAsc) {
		Collections.sort(list, new Comparator<T>() {

			@Override
			public int compare(T o1, T o2) {
				int result = 0;
				try {
					for (int i = 0; i < sortNameArr.length; i++) {
						result = compareObject(sortNameArr[i], isAsc, o1, o2);
						if (result != 0) {
							break;
						}
					} 
				} catch (Exception e) {
					e.printStackTrace();
				}
				return result;
			}
		});
	}
	
	/**
	 * 根据属性数组给list排序， 每个属性指定是降序还是升序
	 * @param list
	 * @param sortNameArr
	 * @param typeArr
	 */
	public static <T> void listSort(List<T> list, String[] sortNameArr, boolean[] typeArr) {
		if(sortNameArr.length != typeArr.length) {
			throw new RuntimeException("排序数组元素个数和升降序数组元素个数不同");
		}
		Collections.sort(list, new Comparator<T>() {

			@Override
			public int compare(T o1, T o2) {
				int result = 0;
				try {
					for (int i = 0; i < sortNameArr.length; i++) {
						result = compareObject(sortNameArr[i], typeArr[i], o1, o2);
						if (result != 0) {
							break;
						}
					} 
				} catch (Exception e) {
					e.printStackTrace();
				}
				return result;
			}
		});
	}
	
	/**
	 * 两个对象的比较排序
	 * 排序属性可以为数字(byte, int, long short, double float等，支持整数，负数，0)， 可以为时间类型, String, char
	 * --->对于数字：统一转换为固定长度的字符串解决,比如数字3和123，转换为"003"和"123" ;再比如"-15"和"7"转换为"-015"和"007" 
	 * --->对于日期：可以先把日期转化为long类型的数字，数字的解决方法如上
	 * @param sortName 属性名称
	 * @param isAsc true升序 false倒序
	 * @param a 目标对象
	 * @param b 目标对象
	 * @return
	 * @throws Exception 
	 */
	public static <T> int compareObject(String sortName, boolean isAsc, T a, T b) throws Exception {
		int result;
		Object valueA = getFieldValue(a, sortName);
		Object valueB = getFieldValue(b, sortName);
		String strA = valueA.toString();
		String strB = valueB.toString();
		if (valueA instanceof Number && valueB instanceof Number) {
			int maxLen = Math.max(strA.length(), strB.length());
			strA = addZero2Str((Number)valueA, maxLen);
			strB = addZero2Str((Number)valueB, maxLen);
		} else if (valueA instanceof Date && valueB instanceof Date) {
			long timeA = ((Date) valueA).getTime();
			long timeB = ((Date) valueB).getTime();
			int maxLen = Long.toString(Math.max(timeA, timeB)).length();
			strA = addZero2Str((Number)valueA, maxLen);
			strB = addZero2Str((Number)valueB, maxLen);
		}
		if (isAsc) {
			result = strA.compareTo(strB);
		} else {
			result = strB.compareTo(strA);
		}
		return result;
	}
	
	
	/**
	 * 获取对象属性的值
	 * @param obj  属性所在的对象
	 * @param fieldName 属性名称
	 * @return
	 * @throws Exception
	 */
	public static Object getFieldValue(Object obj, String fieldName) throws Exception {
		Object object = null;
		Field field = obj.getClass().getDeclaredField(fieldName);
		boolean accessiable = field.isAccessible();
		if (!accessiable) {
			//修改private, protected修饰的为可访问的
			field.setAccessible(true);
			object = field.get(obj);
			//还原状态
			field.setAccessible(accessiable);
			return object;
		}
		return object;
	}

}
