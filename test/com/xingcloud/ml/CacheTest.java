package com.xingcloud.ml;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

public class CacheTest {

	@Test
	public void testUpdateLocalWords() throws Exception {
		ML.config.put("service_name", "javatest");
		ML.config.put("api_key", "d460020a652ff2a7086204d7e094efa6");
		ML.config.put("locale", "en");
		ML.config.put("cache_dir", "");
		Cache cache = new Cache();
		cache.updateLocalWords();
		System.out.println(cache.words.toString());
	}

	@Test
	public void testCacheLocal() throws Exception {
		ML.config.put("service_name", "javatest");
		ML.config.put("api_key", "d460020a652ff2a7086204d7e094efa6");
		ML.config.put("locale", "en");
		ML.config.put("cache_dir", "/home/think/workspace/mljavasdk");
		Cache cache = new Cache();
		cache.cacheLocal();
		assertTrue((new File(ML.config.get("cache_dir")+"/"+ML.config.get("service_name") + "_"
				+ ML.config.get("locale") + ".txt")).exists());
	}

	@Test
	public void testGetLocalWords() throws Exception {
		ML.config.put("service_name", "javatest");
		ML.config.put("api_key", "d460020a652ff2a7086204d7e094efa6");
		ML.config.put("locale", "en");
		ML.config.put("cache_dir", "");
		Cache cache = new Cache();
		cache.getLocalWords();
		System.out.println(cache.words);
		System.out.println(cache.remoteFileMD5);
	}
	
	@Test
	public void test1() throws Exception
	{
		ML.config.put("service_name", "javatest");
		ML.config.put("api_key", "d460020a652ff2a7086204d7e094efa6");
		ML.config.put("locale", "en");
		Cache cache = new Cache();
		cache.updateLocalWords();
		cache.cacheLocal();
		cache.getLocalWords();
		System.out.println(cache.words);
	}
}
