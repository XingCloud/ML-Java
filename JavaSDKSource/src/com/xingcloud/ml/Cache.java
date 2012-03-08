package com.xingcloud.ml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
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
 * 
 * @author YQL
 * 
 */
public class Cache {

	public static String SNAPSHOT_FILE_SUFFIX = ".snapshot";

	public Map<String, String> words = new HashMap<String, String>();

	/***
	 * 更新本地缓存的词条翻译文件（所有以json为后缀名的文件都将会下载）
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void updateLocalWords() {
		RestWrapper restRep = new RestWrapper();
		Map<String, Serializable> fileSnapshot = restRep.getFileSnapshot();
		String requestPrefix = (String) fileSnapshot.get("request_prefix");
		Map<String, String> newFileMap = (Map<String, String>) fileSnapshot
				.get("data");
		String localSnapshotURI = ML.config.get(ML.CACHE_DIR) + File.separator
				+ ML.config.get(ML.SERVICE_NAME) + "_"
				+ ML.config.get(ML.TARGET_LANG) + SNAPSHOT_FILE_SUFFIX;
		File localSnapshotFile = new File(localSnapshotURI);
		if (localSnapshotFile.exists()) {
			String data = getFileContent(localSnapshotURI);
			Map<String, Object> fileMap = null;
			try {
				fileMap = JSONUtil.toMap(data.toString());
			} catch (JSONException e) {
				MLLogger.getLogger().severe(
						"Cannot turn local file string into map");
				MLLogger.getLogger().severe(e.getLocalizedMessage());
				return;
			}
			for (String filename : newFileMap.keySet()) {
				if (filename.endsWith(ML.WORD_FILE_SUFFIX)) {
					if (fileMap.containsKey(filename)) {
						if (!fileMap.get(filename).equals(
								newFileMap.get(filename))) {
							String filecotent = restRep.get(requestPrefix + "/"
									+ filename, null);
							writeFileToLocalDir(
									ML.config.get(ML.CACHE_DIR)
											+ File.separator
											+ ML.config.get(ML.SERVICE_NAME)
											+ "_"
											+ ML.config.get(ML.TARGET_LANG)
											+ "_" + filename, filecotent);
						}
					} else {
						String filecotent = restRep.get(requestPrefix + "/"
								+ filename, null);
						writeFileToLocalDir(
								ML.config.get(ML.CACHE_DIR) + File.separator
										+ ML.config.get(ML.SERVICE_NAME) + "_"
										+ ML.config.get(ML.TARGET_LANG) + "_"
										+ filename, filecotent);
					}
				}
			}
		} else {
			for (String filename : newFileMap.keySet()) {
				if (filename.endsWith(ML.WORD_FILE_SUFFIX)) {
					String filecotent = restRep.get(requestPrefix + "/"
							+ filename, null);
					writeFileToLocalDir(ML.config.get(ML.CACHE_DIR)
							+ File.separator + ML.config.get(ML.SERVICE_NAME)
							+ "_" + ML.config.get(ML.TARGET_LANG) + "_"
							+ filename, filecotent);
				}
			}
		}
		try {
			writeFileToLocalDir(ML.config.get(ML.CACHE_DIR) + File.separator
					+ ML.config.get(ML.SERVICE_NAME) + "_"
					+ ML.config.get(ML.TARGET_LANG) + SNAPSHOT_FILE_SUFFIX,JSONUtil.toJSON(newFileMap));
		} catch (JSONException e) {
			MLLogger.getLogger().severe(e.getLocalizedMessage());
		}

	}
	
	/***
	 * 加载本地缓存的词条翻译文件
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void getLocalWords() {
		File file = new File(ML.config.get(ML.CACHE_DIR));
		File[] files = file.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				if (dir.getAbsolutePath().equals(ML.config.get(ML.CACHE_DIR))) {
					if (name.endsWith(ML.WORD_FILE_SUFFIX)) {
						return true;
					}
				}
				return false;
			}
		});
		for (File f : files) {
			String content = getFileContent(f.getAbsolutePath());
			try {
				words.putAll((Map<String, String>)JSONUtil.toMap(content));
			} catch (JSONException e) {
				MLLogger.getLogger().severe(e.getMessage());
			}
		}
	}

	protected void writeFileToLocalDir(String filePath, String content) {
		File file = new File(filePath);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				MLLogger.getLogger().severe("Create local file error ");
				MLLogger.getLogger().severe(e.getLocalizedMessage());
			}
		}
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new FileOutputStream(filePath));
		} catch (FileNotFoundException e) {
			MLLogger.getLogger().severe(e.getLocalizedMessage());
			return;
		}
		writer.println(content);
		writer.flush();
		writer.close();
	}

	protected String getFileContent(String filePath) {
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
				MLLogger.getLogger().severe(e.getLocalizedMessage());
			}
			return data.toString();
		}
		return null;
	}

	

}
