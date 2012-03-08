ML Java SDK
=============

使用说明
--------

	import com.xingcloud.ml.ML;

	ML ml = new ML("javatest", "98d21e42db6961434c69f57694", "cn", "en", "D:\\test", true, true);
	String word1 = ml.trans("多语言");
	System.out.println(word1);
	String word2 = ml.trans("测试", "publicwords");
	System.out.println(word2);
	
备注
--------
  目前词库文件采用全部加载的方式进行
