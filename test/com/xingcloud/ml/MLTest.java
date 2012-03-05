package com.xingcloud.ml;

import org.junit.Test;

public class MLTest {

	
	//autoUpdateFile = false
	//autoAddString = false
	@Test
	public void testTrans4() throws Exception {
		ML ml = new ML("java_sdk_test", "f6d7f290ec74bb2bb847ee15a6ace3ce", "cn", "en", "/home/think/workspace/mljavasdk", false, false);
		String s1 = ml.trans("北京");
		System.out.println(s1);
		String s2 = ml.trans("人民大会堂");
		System.out.println(s2);
	}

	//autoUpdateFile = false
	@Test
	public void testTrans2() throws Exception {
		ML ml = new ML("java_sdk_test", "f6d7f290ec74bb2bb847ee15a6ace3ce", "cn", "en", "/home/think/workspace/mljavasdk", false,true);
		String s1 = ml.trans("你好");
		System.out.println(s1);
		String s2 = ml.trans("两会");
		System.out.println(s2);
	}
	
	//autoAddString = false
	@Test
	public void testTrans3() throws Exception {
		ML ml = new ML("java_sdk_test", "f6d7f290ec74bb2bb847ee15a6ace3ce", "cn", "en", "/home/think/workspace/mljavasdk", true, false);
		String s1 = ml.trans("你好");
		System.out.println(s1);
		String s2 = ml.trans("人民");
		System.out.println(s2);
	}
	
	@Test
	public void testTrans1() throws Exception {
		ML ml = new ML("java_sdk_test", "f6d7f290ec74bb2bb847ee15a6ace3ce", "cn", "en", "/home/think/workspace/mljavasdk", true,true);
		String s1 = ml.trans("你好");
		System.out.println(s1);
		String s2 = ml.trans("内部推荐");
		System.out.println(s2);
	}
	
	@Test
	public void testTrans5() throws Exception {
		ML ml = new ML("java_sdk_test", "f6d7f290ec74bb2bb847ee15a6ace3ce", "cn", "en", "/home/think/workspace/mljavasdk", true, true);
		String s1 = ml.trans("你好");
		System.out.println(s1);
	}
}
