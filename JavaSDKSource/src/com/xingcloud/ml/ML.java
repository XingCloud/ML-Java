package com.xingcloud.ml;

import java.util.HashMap;
import java.util.Map;

/**
 * 行云多语言SDK主类
 * 
 * @author YQL
 * 
 */
public class ML {

	public static Map<String, String> config = new HashMap<String, String>();
	public static String SERVICE_NAME = "service_name";
	public static String API_KEY = "api_key";
	public static String SOURCE_LANG = "source_lang";
	public static String TARGET_LANG = "locale";
	public static String AUTO_UPDATE_FILE = "auto_update_file";
	public static String AUTO_ADD_STRING = "auto_add_string";
	public static String CACHE_DIR = "cache_dir";
	public static String WORD_FILE_SUFFIX = ".json";

	protected Cache cache = null;
	protected RestWrapper restRe = new RestWrapper();

	/**
	 * 构造函数，初始化的时候需要传入以下参数：
	 * 
	 * @param serviceName
	 *            --string 是在行云平台申请多语言服务的服务名称；
	 * @param apiKey
	 *            --string 行云平台的每个多语言服务都会有一个给定的apiKey，服务的唯一标识
	 * @param sourceLang
	 *            --string 未翻译词条对应语言的缩写（详细请查看行云平台具体文档）
	 * @param targetLang
	 *            --string 翻译结果对应语言的缩写
	 * @param cacheDir
	 *            --string 本地缓存词条翻译文件的存取文件夹名称
	 * @param autoUpdateFile
	 *            --Boolean 是否自动从多语言服务器上更新本地缓存
	 * @param autoAddString
	 *            --Boolean 是否自动添加未翻译词条到多语言服务器
	 * @throws Exception
	 */
	public ML(String serviceName, String apiKey, String sourceLang,
			String targetLang, String cacheDir, Boolean autoUpdateFile,
			Boolean autoAddString) {
		ML.config.put(ML.SERVICE_NAME, serviceName);
		ML.config.put(ML.API_KEY, apiKey);
		ML.config.put(ML.SOURCE_LANG, sourceLang);
		ML.config.put(ML.TARGET_LANG, targetLang);
		ML.config.put(ML.AUTO_UPDATE_FILE, autoUpdateFile.toString());
		ML.config.put(ML.AUTO_ADD_STRING, autoAddString.toString());
		ML.config.put(ML.CACHE_DIR, cacheDir);
		this.initCache();
	}

	protected void initCache() {
		cache = new Cache();
		if (Boolean.parseBoolean(ML.config.get(ML.AUTO_UPDATE_FILE))) {
			cache.updateLocalWords();
		}
		cache.getLocalWords();
	}

	/***
	 * 翻译词条
	 * 
	 * @param word
	 *            传入要翻译的词条
	 * @return
	 * @throws Exception
	 */
	public String trans(String word) {
		return this.trans(word, null);
	}

	public String trans(String word, String filename) {
		if (ML.config.get(ML.SOURCE_LANG).equalsIgnoreCase(
				ML.config.get(ML.TARGET_LANG))) {
			return word;
		}
		if (this.cache.words.containsKey(word)) {
			return this.cache.words.get(word);
		} else {
			if (Boolean.parseBoolean(ML.config.get(ML.AUTO_ADD_STRING))) {
				if (filename == null) {
					restRe.stringAdd(word);
				} else {
					restRe.stringAdd(word, filename + ML.WORD_FILE_SUFFIX);
				}
			}
			return word;
		}
	}
}
