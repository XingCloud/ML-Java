package com.xingcloud.ml;

import java.util.HashMap;
import java.util.Map;

/**
 * 行云多语言SDK主类
 * @author Admin
 *
 */
public class ML {

	public static Map<String, String> config = new HashMap<String, String>();

	protected Cache cache;
	protected RestWrapper restRe = new RestWrapper();

	/**
	 * 构造函数，初始化的时候需要传入以下参数：
	 * @param serviceName  --string 是在行云平台申请多语言服务的服务名称；
	 * @param apiKey  --string 行云平台的每个多语言服务都会有一个给定的apiKey，服务的唯一标识
	 * @param sourceLang --string 未翻译词条对应语言的缩写（详细请查看行云平台具体文档）
	 * @param targetLang --string 翻译结果对应语言的缩写
	 * @param cacheDir --string 本地缓存词条翻译文件的存取文件夹名称
	 * @param autoUpdateFile --Boolean 是否自动从多语言服务器上更新本地缓存，默认为FALSE
	 * @param autoAddString  --Boolean 是否自动添加未翻译词条到多语言服务器，默认为FALSE
	 * @throws Exception
	 */
	public ML(String serviceName, String apiKey, String sourceLang,
			String targetLang, String cacheDir, Boolean autoUpdateFile,
			Boolean autoAddString) throws Exception {
		ML.config.put("service_name", serviceName);
		ML.config.put("api_key", apiKey);
		ML.config.put("source_lang", sourceLang);
		ML.config.put("locale", targetLang);
		ML.config.put("auto_update_file", autoUpdateFile.toString());
		ML.config.put("auto_add_string", autoAddString.toString());
		ML.config.put("cache_dir", cacheDir);
		this.Init();
	}

	protected void Init() throws Exception {
		cache = new Cache();
		if (Boolean.parseBoolean(ML.config.get("auto_update_file"))) {
			cache.updateLocalWords();
			cache.cacheLocal();
		} else {
			cache.getLocalWords();
		}
	}

	/***
	 * 翻译词条
	 * @param word 传入要翻译的词条
	 * @return
	 * @throws Exception
	 */
	public String trans(String word) throws Exception {
		if (ML.config.get("source_lang").equalsIgnoreCase(ML.config.get("target_lang"))) {
			return word;
		}
		if (this.cache.words.containsKey(word)) {
			return this.cache.words.get(word);
		} else {
			if (Boolean.parseBoolean(ML.config.get("auto_add_string"))) {
				restRe.stringAdd(word);
			}
			return word;
		}
	}
}
