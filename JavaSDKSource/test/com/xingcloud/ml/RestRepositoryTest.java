package com.xingcloud.ml;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class RestRepositoryTest {

	@SuppressWarnings("unchecked")
	@Test
	public void testGetFileInfo() throws Exception {
		ML.config.put("service_name", "javatest");
		ML.config.put("api_key", "98d21e42db699c5ef061434c69f57694");
		ML.config.put("locale", "en");
		RestWrapper rr = new RestWrapper();
		Map<String, Serializable> result = rr.getFileInfo();
		System.out.println(result);
		Map<String, String> data = (Map<String, String>) result.get("data");
		System.out.println(data.get("request_address"));
	}
	
	@Test
	public void testStringAdd1() throws Exception
	{
		ML.config.put("service_name", "javatest");
		ML.config.put("api_key", "98d21e42db699c5ef061434c69f57694");
		ML.config.put("locale", "en");
		RestWrapper rr = new RestWrapper();
		rr.stringAdd("你好");
	}

	@Test
	public void testStringAdd2() throws Exception
	{
		ML.config.put("service_name", "javatest");
		ML.config.put("api_key", "98d21e42db699c5ef061434c69f57694");
		ML.config.put("locale", "en");
		RestWrapper rr = new RestWrapper();
		List<String> stringlist = new ArrayList<String>();
		stringlist.add("世界");
		stringlist.add("人力资源部");
		rr.stringAdd(stringlist);
	}
	
	@Test
	public void testGetDefaultFile() throws Exception
	{
		ML.config.put("service_name", "javatest");
		ML.config.put("api_key", "98d21e42db699c5ef061434c69f57694");
		ML.config.put("locale", "en");
		RestWrapper rr = new RestWrapper();
		Map<String, Serializable> result = rr.getDefaultFile();
		System.out.println(result.toString());
	}
}
