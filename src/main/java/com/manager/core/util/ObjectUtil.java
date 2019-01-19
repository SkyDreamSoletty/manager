package com.manager.core.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("all")
public class ObjectUtil {
	private static Gson gson =new GsonBuilder().serializeNulls().create();
	private static XStream xStream = new XStream(new DomDriver());

	@SuppressWarnings("unchecked")
	public static <T> T byteToObject(byte[] bytes, Class<T> clazz) {
		Object obj = null;
		if (bytes == null || bytes.length == 0) {
			return null;
		}
		try {
			ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
			ObjectInputStream oi = new ObjectInputStream(bi);
			obj = oi.readObject();
			bi.close();
			oi.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (T) obj;
	}

	public static byte[] objectToByte(Object obj) {
		byte[] bytes = null;
		if (obj == null) {
			return null;
		}
		try {
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			ObjectOutputStream oo = new ObjectOutputStream(bo);
			oo.writeObject(obj);
			bytes = bo.toByteArray();
			bo.close();
			oo.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (bytes);
	}

	/**
	 * 将javaBean 转换成map 默认过滤null字段
	 * 
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> objectToMap(Object obj) {
		return objectToMap(obj, true);
	}

	/**
	 * 将javaBean 转换成map
	 * 
	 * @param obj
	 * @param isFilterNull
	 * @return
	 */
	public static Map<String, Object> objectToMap(Object obj,
			boolean isFilterNull) {
		if (obj == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass(), Object.class);
			PropertyDescriptor[] propertyDescriptors = beanInfo
					.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				// 得到property对应的getter方法
				Method getter = property.getReadMethod();
				if(getter!=null){
					Object value = getter.invoke(obj);
					map.put(key, value);
				}
			}
		} catch (Exception e) {
			//System.out.println("objectToMap Error " + e);
			e.printStackTrace();
		}
		return map;

	}

	public static String toJson(Object Object) {
		return gson.toJson(Object);
	}

	public static <T> T fromJson(String json, Class<T> clazz) {
		return gson.fromJson(json, clazz);
	}

	public static String toXML(Object object) {
		StringWriter writer = new StringWriter();
		xStream.autodetectAnnotations(true);
		xStream.aliasSystemAttribute(null, "class"); // 去掉 class 属性  
		xStream.toXML(object, writer);
		return writer.toString();
	}

	public static <T> T fromXML(String xml, Class<T> clazz) {
		Object object = xStream.fromXML(xml);
		return (T) object;
	}
	public static <T> T fromXML(File file, Class<T> clazz) {
		xStream.processAnnotations(clazz);
		Object object = xStream.fromXML(file);
		return (T) object;
	}
	public static <T> T fromXML(String xml,Class<T> clazz,String nodeName){
		xStream.processAnnotations(clazz);
		xStream.omitField(clazz, nodeName);
		Object object = xStream.fromXML(xml);
		return (T) object;
	}
}
