ML PHP SDK
=============

使用说明
--------

	import com.xingcloud.ml.ML;

	ML ml = new ML("javatest", "98d21e42db6961434c69f57694", "cn", "en", "D:\\test", true, true);
	String word1 = ml.trans("北京");
	System.out.println(word1);
	String word2 = ml.trans("人民大会堂");
	System.out.println(word2);
	

