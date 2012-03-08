package com.xingcloud.ml;

import org.junit.Before;
import org.junit.Test;

public class CacheTest {
	
	@Before
	public void setup() {
		ML.config.put(ML.SERVICE_NAME, "javatest");
		ML.config.put(ML.API_KEY, "94d97f99bc67a69ff6afb31a820ef275");
		ML.config.put(ML.TARGET_LANG, "en");
		ML.config.put(ML.CACHE_DIR, "D:\\test");
	}

	@Test
	public void testUpdateLocalWords(){
		Cache cache = new Cache();
		cache.updateLocalWords();
	}
	
	@Test
	public void testGetLocalWords() {
		Cache cache = new Cache();
		cache.getLocalWords();
		System.out.println(cache.words.toString());
	}

}
