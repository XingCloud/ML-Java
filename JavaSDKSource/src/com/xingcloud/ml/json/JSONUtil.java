package com.xingcloud.ml.json;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser.Feature;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectReader;
import org.codehaus.jackson.map.ObjectWriter;

/**
 * 
 * JSON工具类
 *
 */
public class JSONUtil {
	private static ObjectMapper objectMapper = new ObjectMapper(new JsonFactory().configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true).configure(Feature.INTERN_FIELD_NAMES, false));
	private static ObjectWriter objectWriter = null;
	
	static{
		objectWriter = objectMapper.writer();
	}
	
	/**
	 * 判断对象是否为合法JSON字符串
	 * @param Object object 
	 * @return boolean
	 */
	public static boolean mayBeJSON(Object object){
		if(object == null
			|| !String.class.isAssignableFrom(object.getClass())){
			return false;
		}
		String string = (String) object;
		if(string.isEmpty()){
			return false;
		}
		char head = string.charAt(0);
		return head == '[' || head == '{';
	}
	
	/**
	 * 判断对象是否为合法JSON Object的字符串
	 * @param Object object 
	 * @return boolean
	 */
	public static boolean mayBeJSONObject(Object object){
		if(object == null
			|| !String.class.isAssignableFrom(object.getClass())){
			return false;
		}
		String string = (String) object;
		if(string.isEmpty()){
			return false;
		}
		char head = string.charAt(0);
		return head == '{';
	}
	
	/**
	 * 判断对象是否为合法JSON Array的字符串
	 * @param Object object 
	 * @return boolean
	 */
	public static boolean mayBeJSONArray(Object object){
		if(object == null
			|| !String.class.isAssignableFrom(object.getClass())){
			return false;
		}
		String string = (String) object;
		if(string.isEmpty()){
			return false;
		}
		char head = string.charAt(0);
		return head == '[';
	}
	
	/**
	 * 将JSON串转换为对象
	 * @param String json JSON串
	 * @param Class<T> clazz 指定的对象类型
	 * @return T
	 * @throws Exception 
	 */	
	@SuppressWarnings("unchecked")
	public static <T> T toObject(String json, Class<T> clazz) throws JSONException{
		if(json == null
			|| json.isEmpty()){
			return null;
		}
		ObjectReader reader = objectMapper.reader(clazz);
		try {
			return (T) reader.readValue(json);
		} catch (Exception e) {
			throw new JSONException(e.getMessage(), e);
		}
	}
	
	/**
	 * 将JSON串转换为List
	 * @param String json JSON串
	 * @return List
	 * @throws Exception 
	 */	
	@SuppressWarnings("rawtypes")
	public static List toList(String json) throws JSONException{
		if(json == null
			|| json.isEmpty()){
			return null;
		}
		ObjectReader reader = objectMapper.reader(List.class);
		try {
			return reader.readValue(json);
		} catch (Exception e) {
			throw new JSONException(e.getMessage(), e);
		}
	}
	
	/**
	 * 将JSON串转换为Map
	 * @param String json JSON串
	 * @return Map
	 * @throws Exception 
	 */	
	@SuppressWarnings("rawtypes")
	public static Map toMap(String json) throws JSONException{
		if(json == null
			|| json.isEmpty()){
			return null;
		}
		ObjectReader reader = objectMapper.reader(Map.class);		
		try {
			return reader.readValue(json);
		} catch (Exception e) {
			throw new JSONException(e.getMessage(), e);
		}
	}
	
	/**
	 * 将对象转换为JSON串
	 * @return String
	 * @throws Exception 
	 */
	public static String toJSON(Object object) throws JSONException{
		if(object == null){
			return null;
		}
		if(String.class.isAssignableFrom(object.getClass())){
			return (String) object;
		}
		try {
			return objectWriter.writeValueAsString(object);
		} catch (Exception e) {
			throw new JSONException(e.getMessage(), e);
		}
	}
	
	public static void clearAll(){
		objectMapper = null;
		objectWriter = null;
	}
}