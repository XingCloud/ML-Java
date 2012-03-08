package com.xingcloud.ml;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class RestRepositoryTest {
	
	@Before
	public void setup() {
		ML.config.put("service_name", "javatest");
		ML.config.put("api_key", "94d97f99bc67a69ff6afb31a820ef275");
		ML.config.put("locale", "en");
		ML.config.put("cache_dir", "D:\\test");
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetFileInfo() throws Exception {
		RestWrapper rr = new RestWrapper();
		Map<String, Serializable> result = rr.getFileInfo();
		System.out.println(result);
		Map<String, String> data = (Map<String, String>) result.get("data");
		System.out.println(data.get("request_address"));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetFileInfo1() throws Exception {
		RestWrapper rr = new RestWrapper();
		Map<String, Serializable> result = rr.getFileInfo("helloworld.json");
		System.out.println(result);
		Map<String, String> data = (Map<String, String>) result.get("data");
		System.out.println(data.get("request_address"));
	}
	
	@Test
	public void testGetFile() {
		RestWrapper rr = new RestWrapper();
		Map<String, Serializable> result = rr.getFile("helloworld.json");
		System.out.println(result);
	}
	
	@Test
	public void testStringAdd1() throws Exception
	{
		RestWrapper rr = new RestWrapper();
		rr.stringAdd("你好");
	}

	@Test
	public void testStringAdd2() throws Exception
	{
		RestWrapper rr = new RestWrapper();
		List<String> stringlist = new ArrayList<String>();
		stringlist.add("世界");
		stringlist.add("人力资源部");
		rr.stringAdd(stringlist);
	}
	
	@Test
	public void testStringAdd3() throws Exception
	{
		RestWrapper rr = new RestWrapper();
		rr.stringAdd("你好", "helloworld.json");
	}
	
	@Test
	public void testStringAdd4() throws Exception
	{
		RestWrapper rr = new RestWrapper();
		List<String> stringlist = new ArrayList<String>();
		stringlist.add("世界");
		stringlist.add("人力资源部");
		rr.stringAdd(stringlist, "hiworld.json");
	}
	
	@Test
	public void testGetDefaultFile() throws Exception
	{
		RestWrapper rr = new RestWrapper();
		Map<String, Serializable> result = rr.getDefaultFile();
		System.out.println(result.toString());
	}
	
	@Test
	public void testGetFileSnapshot1()
	{
		RestWrapper rr = new RestWrapper();
		Map<String, Serializable> result = rr.getFileSnapshot();
		System.out.println(result.toString());
	}
	
	@Test
	public void testGetFileSnapshot2()
	{
		RestWrapper rr = new RestWrapper();
		Map<String, Serializable> result = rr.getFileSnapshot("helloworld.json");
		System.out.println(result.toString());
	}
	
	@Test
	public void testGetFileSnapshot3()
	{
		RestWrapper rr = new RestWrapper();
		List<String> filelist = new ArrayList<String>();
		filelist.add("helloworld.json");
		filelist.add("gre.json");
		Map<String, Serializable> result = rr.getFileSnapshot(filelist);
		System.out.println(result.toString());
	}
}
