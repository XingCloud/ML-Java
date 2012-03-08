package com.xingcloud.ml;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class MLTest {

	protected Map<String, String> config = new HashMap<String, String>();
	
	@Before
	public void setup() {
		config.put(ML.SERVICE_NAME, "javatest");
		config.put(ML.API_KEY, "94d97f99bc67a69ff6afb31a820ef275");
		config.put(ML.SOURCE_LANG, "cn");
		config.put(ML.TARGET_LANG, "en");
		config.put(ML.CACHE_DIR, "D:\\test");
	}
	
	//autoUpdateFile = false
	//autoAddString = false
	@Test
	public void testTrans4() throws Exception {
		ML ml = new ML(config.get(ML.SERVICE_NAME), config.get(ML.API_KEY), config.get(ML.SOURCE_LANG), config.get(ML.TARGET_LANG), config.get(ML.CACHE_DIR), false, false);
		String s1 = ml.trans("北京");
		System.out.println(s1);
		String s2 = ml.trans("人民大会堂");
		System.out.println(s2);
	}

	//autoUpdateFile = false
	@Test
	public void testTrans2() throws Exception {
		ML ml = new ML(config.get(ML.SERVICE_NAME), config.get(ML.API_KEY), config.get(ML.SOURCE_LANG), config.get(ML.TARGET_LANG), config.get(ML.CACHE_DIR), false,true);
		String s1 = ml.trans("你好");
		System.out.println(s1);
		String s2 = ml.trans("两会");
		System.out.println(s2);
	}
	
	//autoAddString = false
	@Test
	public void testTrans3() throws Exception {
		ML ml = new ML(config.get(ML.SERVICE_NAME), config.get(ML.API_KEY), config.get(ML.SOURCE_LANG), config.get(ML.TARGET_LANG), config.get(ML.CACHE_DIR), true, false);
		String s1 = ml.trans("你好");
		System.out.println(s1);
		String s2 = ml.trans("人民");
		System.out.println(s2);
	}
	
	@Test
	public void testTrans1() throws Exception {
		ML ml = new ML(config.get(ML.SERVICE_NAME), config.get(ML.API_KEY), config.get(ML.SOURCE_LANG), config.get(ML.TARGET_LANG), config.get(ML.CACHE_DIR), true,true);
		String s1 = ml.trans("你好");
		System.out.println(s1);
		String s2 = ml.trans("内部推荐");
		System.out.println(s2);
	}
	
	@Test
	public void testTrans5() throws Exception {
		ML ml = new ML(config.get(ML.SERVICE_NAME), config.get(ML.API_KEY), config.get(ML.SOURCE_LANG), config.get(ML.TARGET_LANG), config.get(ML.CACHE_DIR), true, true);
		String s1 = ml.trans("你好");
		System.out.println(s1);
	}
	
	@Test
	public void testTrans6() throws Exception {
		ML ml = new ML(config.get(ML.SERVICE_NAME), config.get(ML.API_KEY), config.get(ML.SOURCE_LANG), config.get(ML.TARGET_LANG), config.get(ML.CACHE_DIR), true, true);
		String s1 = ml.trans("核心词汇", "helloworld.json");
		System.out.println(s1);
	}
	
	@Test
	public void testTrans7() throws Exception {
		ML ml = new ML(config.get(ML.SERVICE_NAME), config.get(ML.API_KEY), config.get(ML.SOURCE_LANG), config.get(ML.TARGET_LANG), config.get(ML.CACHE_DIR), true, true);
		String s1 = ml.trans("新东方", "gre.json");
		System.out.println(s1);
	}
}
