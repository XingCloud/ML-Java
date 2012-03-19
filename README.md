ML Java SDK
=============

使用说明

SDK入口：ML类
通过new 一个ML类并传入相关参数来获得多语言实例
#### 参数类型
	 * @param serviceName
	 *            --string 是在行云平台申请多语言服务的服务名称。如“javatest”
	 * @param apiKey
	 *            --string 行云平台的每个多语言服务都会有一个给定的apiKey，服务的唯一标识。如“98d21e42db6961434c69f57694”
	 * @param sourceLang
	 *            --string 未翻译词条对应语言的缩写（详细请查看行云平台具体文档）
	 * @param targetLang
	 *            --string 翻译结果对应语言的缩写，注意：如果与原始语言相同, 则不翻译直接原文返回
	 * @param cacheDir
	 *            --string 本地缓存词条翻译文件的存取文件夹名称
	 * @param autoUpdateFile
	 *            --Boolean 是否自动从多语言服务器上更新本地缓存
	 * @param autoAddString
	 *            --Boolean 是否自动添加未翻译词条到多语言服务器
	 
	public ML(String serviceName, String apiKey, String sourceLang,
			String targetLang, String cacheDir, Boolean autoUpdateFile,
			Boolean autoAddString)



翻译语句方法：ML.trans（）

此方法分为：1）添加到默认文件的方法：public String trans(String word)
	    2）添加到指定文件的方法：public String trans(String word, String filename)

#### 参数类型
	 * @param word
	 *            --string 需要翻译的词句
	 * @param filename
	 *            --string 词句在服务器上保存的文件名称
#### 返回值
     翻译好的词句

#### 代码示例
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
