package com.xingcloud.ml;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

public class CacheTest {

	@Test
	public void testUpdateLocalWords() throws Exception {
		ML.config.put("service_name", "javatest");
		ML.config.put("api_key", "98d21e42db699c5ef061434c69f57694");
		ML.config.put("locale", "en");
		ML.config.put("cache_dir", "D:\\test");
		Cache cache = new Cache();
		cache.updateLocalWords();
		System.out.println(cache.words.toString());
	}

	@Test
	public void testCacheLocal() throws Exception {
		ML.config.put("service_name", "javatest");
		ML.config.put("api_key", "98d21e42db699c5ef061434c69f57694");
		ML.config.put("locale", "en");
		ML.config.put("cache_dir", "D:\\test");
		Cache cache = new Cache();
		cache.updateLocalWords();
		cache.cacheLocal();
		System.out.println(ML.config.get("cache_dir"));
		assertTrue((new File(ML.config.get("cache_dir"))).exists());
	}

	@Test
	public void testGetLocalWords() throws Exception {
		ML.config.put("service_name", "javatest");
		ML.config.put("api_key", "98d21e42db699c5ef061434c69f57694");
		ML.config.put("locale", "en");
		ML.config.put("cache_dir", "D:\\test");
		Cache cache = new Cache();
		cache.getLocalWords();
		System.out.println(cache.words);
		System.out.println(cache.remoteFileMD5);
	}
	
	@Test
	public void test1() throws Exception
	{
		ML.config.put("service_name", "javatest");
		ML.config.put("api_key", "98d21e42db699c5ef061434c69f57694");
		ML.config.put("locale", "en");
		Cache cache = new Cache();
		cache.updateLocalWords();
		cache.cacheLocal();
		cache.getLocalWords();
		System.out.println(cache.words);
	}
}
