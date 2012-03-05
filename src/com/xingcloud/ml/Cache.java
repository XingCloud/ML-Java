package com.xingcloud.ml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.xingcloud.ml.json.JSONUtil;

/**
 * 翻译词库类
 * @author Admin
 *
 */
public class Cache {

	public Map<String, String> words = new HashMap<String, String>();
	public String remoteFileMD5;

	/***
	 * 更新本地缓存的词条翻译文件
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void updateLocalWords() throws Exception {
		RestWrapper restRep = new RestWrapper();
		this.getLocalWords();
		if (!((Map<String, String>) restRep.getFileInfo().get("data")).get(
				"md5").equals(remoteFileMD5)) {
			Map<String, Serializable> result = restRep.getDefaultFile();
			words = (Map<String, String>) result.get("filecontent");
			remoteFileMD5 = (String) result.get("filemd5");
		}
	}

	/***
	 * 加载本地缓存的词条翻译文件
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void getLocalWords() throws Exception {
		String fileName = ML.config.get("service_name") + "_"
				+ ML.config.get("locale") + ".txt";
		File file = new File(ML.config.get("cache_dir") + fileName);
		if (file.exists()) {
			BufferedReader reader = new BufferedReader(new FileReader(
					ML.config.get("cache_dir") + fileName));
			String line = "";
			while ((line = reader.readLine()) != null) {
				String[] results = line.split(" = ", 2);
				if (results[0].trim().equalsIgnoreCase("words")) {
					this.words = JSONUtil.toMap(results[1]);
				}
				if (results[0].trim().equalsIgnoreCase("md5")) {
					this.remoteFileMD5 = results[1];
				}
			}
		}
	}

	/***
	 * 将目前内存中的词条翻译保存到本地
	 * @throws Exception
	 */
	public void cacheLocal() throws Exception {
		String fileName = ML.config.get("service_name") + "_"
				+ ML.config.get("locale") + ".txt";
		File file = new File(ML.config.get("cache_dir") + fileName);
		if (!file.exists()) {
			file.createNewFile();
		}
		PrintWriter writer = new PrintWriter(new FileOutputStream(
				ML.config.get("cache_dir") + fileName));
		writer.println("words = " + JSONUtil.toJSON(words));
		writer.println("md5 = " + remoteFileMD5);
		writer.flush();
		writer.close();
	}

}
