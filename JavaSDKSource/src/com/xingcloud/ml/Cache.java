package com.xingcloud.ml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.xingcloud.ml.json.JSONException;
import com.xingcloud.ml.json.JSONUtil;
import com.xingcloud.ml.logger.MLLogger;

/**
 * 翻译词库类
 * @author YQL
 *
 */
public class Cache {

	public Map<String, String> words = new HashMap<String, String>();
	public String remoteFileMD5 = "";

	/***
	 * 更新本地缓存的词条翻译文件
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void updateLocalWords() {
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
	public void getLocalWords() {
		String fileName = ML.config.get("service_name") + "_"
				+ ML.config.get("locale") + ".txt";
		String filePath = ML.config.get("cache_dir") + File.separator +fileName;
		File file = new File(filePath);
		if (file.exists()) {
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new FileReader(filePath));
			} catch (FileNotFoundException e) {
			}
			StringBuffer data = new StringBuffer();
			String line = "";
			try {
				while ((line = reader.readLine()) != null) {
					data.append(line);
				}
			} catch (IOException e) {
				MLLogger.getLogger().error("Cache getLocalWords: " + e.getLocalizedMessage());
			}
			Map<String, Object> dataMap = null;
			try {
				dataMap = JSONUtil.toMap(data.toString());
			} catch (JSONException e) {
				MLLogger.getLogger().error("Cache getLocalWords: Cannot turn local file string into map");
				MLLogger.getLogger().error(e.getLocalizedMessage());
				return;
			}
			this.remoteFileMD5 = dataMap.get("md5").toString();
			try {
				this.words = (Map<String, String>) JSONUtil.toMap((String) dataMap.get("words"));
			} catch (Exception e) {
				MLLogger.getLogger().error("Cache getLocalWords: " + e.getLocalizedMessage());
			}
		}
	}

	/***
	 * 将目前内存中的词条翻译保存到本地
	 * @throws Exception
	 */
	public void cacheLocal() {
		String fileName = ML.config.get("service_name") + "_"
				+ ML.config.get("locale") + ".txt";
		String filePath = ML.config.get("cache_dir") + File.separator + fileName;
		File file = new File(filePath);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				MLLogger.getLogger().error("Cache cacheLocal: Create local file error ");
				MLLogger.getLogger().error(e.getLocalizedMessage());
			}
		}
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new FileOutputStream(filePath));
		} catch (FileNotFoundException e) {
			MLLogger.getLogger().error("Cache cacheLocal: " + e.getLocalizedMessage());
			return;
		}
		Map<String, String> filedata = new HashMap<String, String>();
		filedata.put("md5", this.remoteFileMD5);
		try {
			filedata.put("words", JSONUtil.toJSON(this.words));
			writer.println(JSONUtil.toJSON(filedata));
		} catch (JSONException e) {
			MLLogger.getLogger().error("Cache cacheLocal: " + e.getLocalizedMessage());
		}
		writer.flush();
		writer.close();
	}

}
