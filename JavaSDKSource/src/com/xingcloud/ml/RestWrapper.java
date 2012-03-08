package com.xingcloud.ml;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xingcloud.ml.json.JSONException;
import com.xingcloud.ml.json.JSONUtil;
import com.xingcloud.ml.logger.MLLogger;
import com.xingcloud.ml.security.MD5;

/**
 * REST接口包装类
 * 
 * @author YQL
 * 
 */
public class RestWrapper {

	protected Map<String, String> config;

	protected String baseURL = "http://i.xingcloud.com/api/v1";
	protected String fileInfoURL = baseURL + "/file/info";
	protected String fileDownloadURL = baseURL + "/file/download";
	protected String stringAddURL = baseURL + "/string/add";
	protected String fileSnapshotURL = baseURL + "/file/snapshot";

	/***
	 * rest接口包装类 无参构造函数，参数从ML的config传入
	 */
	public RestWrapper() {
		this.config = ML.config;
	}

	/***
	 * rest接口包装类
	 * 
	 * @param config
	 *            cofig参数包括： service_name： 服务名称 api_key：服务的api key locale： 目标语言
	 * 
	 */
	public RestWrapper(String serviceName, String apiKey, String locale) {
		this.config.put(ML.SERVICE_NAME, serviceName);
		this.config.put(ML.API_KEY, apiKey);
		this.config.put(ML.TARGET_LANG, locale);
	}

	/***
	 * 获取默认文件信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<String, Serializable> getFileInfo() {
		return this.getFileInfo(null);
	}
	
	/***
	 * 获取默认文件信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Serializable> getFileInfo(String filename) {
		Map<String, Serializable> data = this.getBasicParam();
		if(data == null) {
			return null;
		}
		data.put("locale", config.get(ML.TARGET_LANG));
		if(filename != null) {
			data.put("file_path", filename);
		}
		String result = this.get(this.fileInfoURL, data);
		Map<String, Serializable> resultMaps;
		try {
			resultMaps = JSONUtil.toMap(result);
		} catch (JSONException e) {
			MLLogger.getLogger().severe("Cannot not turn file info response into json");
			MLLogger.getLogger().severe(e.getLocalizedMessage());
			return null;
		}
		return resultMaps;
	}

	/***
	 * 获取默认文件内容
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<String, Serializable> getDefaultFile() {
		return this.getFile(null);
	}
	
	/***
	 * 获取文件内容
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Serializable> getFile(String filename) {
		Map<String, Serializable> fileInfoData = this.getFileInfo(filename);
		String defaultFileURL = ((Map<String, String>) fileInfoData.get("data"))
				.get("request_address");
		String result = this.get(defaultFileURL, null);
		Map<String, Serializable> responseData = new HashMap<String, Serializable>();
		try {
			responseData.put("filecontent",
					(Serializable) JSONUtil.toMap(result));
		} catch (JSONException e) {
			MLLogger.getLogger().severe("Cannot not turn default file response into json");
			MLLogger.getLogger().severe(e.getLocalizedMessage());
			return null;
		}
		responseData.put("filemd5",
				((Map<String, String>) fileInfoData.get("data")).get("md5"));
		return responseData;
	}

	/***
	 * string/add rest接口
	 * 
	 * @param string
	 * @throws Exception
	 */
	public void stringAdd(String string) {
		this.stringAdd(string, null);
	}
	
	public void stringAdd(String string, String filename) {
		Map<String, Serializable> data = this.getBasicParam();
		if(data == null) {
			return;
		}
		data.put("data", string);
		if(filename != null){
			data.put("create", 1);
			data.put("file_path", filename);
		}
		@SuppressWarnings("unused")
		String result = this.post(stringAddURL, data);
	}

	/***
	 * string/add rest接口
	 * 
	 * @param stringList
	 * @throws Exception
	 */
	public void stringAdd(List<String> stringList) {
		this.stringAdd(stringList, null);
	}
	
	public void stringAdd(List<String> stringList, String filename) {
		Map<String, Serializable> data = this.getBasicParam();
		if(data == null) {
			return;
		}
		try {
			data.put("data", JSONUtil.toJSON(stringList));
		} catch (JSONException e) {
			MLLogger.getLogger().severe("Cannot not turn string list into json string");
			MLLogger.getLogger().severe(e.getLocalizedMessage());
			return;
		}
		if(filename != null) {
			data.put("create", 1);
			data.put("file_path", filename);
		}
		@SuppressWarnings("unused")
		String result = this.post(stringAddURL, data);
	}
	
	public Map<String, Serializable> getFileSnapshot() {
		return this.getFileSnapshot("");
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Serializable> getFileSnapshot(String filename) {
		Map<String, Serializable> data = this.getBasicParam();
		if(data == null) {
			return null;
		}
		data.put("locale", config.get(ML.TARGET_LANG));
		if(filename != null && !filename.equalsIgnoreCase("")) {
			data.put("file_path", filename);
		}
		String result = this.get(this.fileSnapshotURL, data);
		Map<String, Serializable> resultMaps;
		try {
			resultMaps = JSONUtil.toMap(result);
		} catch (JSONException e) {
			MLLogger.getLogger().severe("Cannot not turn file info response into json");
			MLLogger.getLogger().severe(e.getLocalizedMessage());
			return null;
		}
		return resultMaps;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Serializable> getFileSnapshot(List<String> filelist) {
		Map<String, Serializable> data = this.getBasicParam();
		if(data == null) {
			return null;
		}
		data.put("locale", config.get(ML.TARGET_LANG));
		StringBuffer filestring = new StringBuffer();
		for(int i = 0; i < filelist.size(); i++) {
			filestring.append("file_path=" + filelist.get(i) + "&");
		}
		filestring.delete(0, 10);
		filestring.deleteCharAt(filestring.length()-1);
		data.put("file_path", filestring.toString());
		String result = this.get(this.fileSnapshotURL, data);
		Map<String, Serializable> resultMaps;
		try {
			resultMaps = JSONUtil.toMap(result);
		} catch (JSONException e) {
			MLLogger.getLogger().severe("Cannot not turn file info response into json");
			MLLogger.getLogger().severe(e.getLocalizedMessage());
			return null;
		}
		return resultMaps;
	}
	
	protected Map<String, Serializable> getBasicParam() {
		Map<String, Serializable> data = new HashMap<String, Serializable>();
		data.put("service_name", config.get(ML.SERVICE_NAME));
		Date date = new Date();
		String timeStamp = Long.toString(date.getTime());
		data.put("timestamp", timeStamp);
		String hash = "";
		try {
			hash = MD5.encode(timeStamp + config.get(ML.API_KEY));
		} catch (Exception e1) {
			MLLogger.getLogger().severe("MD5 encode error");
			MLLogger.getLogger().severe(e1.getMessage());
			return null;
		}
		data.put("hash", hash);
		return data;
	}

	public String post(String url, Map<String, Serializable> data) {
		try {
			URL u = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) u.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			OutputStreamWriter out = new OutputStreamWriter(
					conn.getOutputStream());
			StringBuffer dataStr = new StringBuffer();
			for (String s : data.keySet()) {
				dataStr.append(s + "=" + data.get(s).toString() + "&");
			}
			dataStr.deleteCharAt(dataStr.length() - 1);
			out.write(dataStr.toString());
			out.flush();
			out.close();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "utf-8"));
			StringBuffer responseData = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				responseData.append(line);
			}
			reader.close();
			return responseData.toString();
		} catch (MalformedURLException e) {
			MLLogger.getLogger().severe("URL error: " + url);
			MLLogger.getLogger().severe(e.getLocalizedMessage());
		} catch (ProtocolException e) {
			MLLogger.getLogger().severe("Protocol error");
			MLLogger.getLogger().severe(e.getLocalizedMessage());
		} catch (UnsupportedEncodingException e) {
			MLLogger.getLogger().severe(e.getLocalizedMessage());
		} catch (IOException e) {
			MLLogger.getLogger().severe(e.getLocalizedMessage());
		}
		return null;
	}

	public String get(String url, Map<String, Serializable> data) {
		try {
			StringBuffer dataStr = new StringBuffer();
			String getURL = url;
			if (data != null) {
				for (String s : data.keySet()) {
					dataStr.append(s + "=" + data.get(s).toString() + "&");
				}
				dataStr.deleteCharAt(dataStr.length() - 1);
				getURL += "?" + dataStr.toString();
			}
			URL u = new URL(getURL);
			HttpURLConnection conn = (HttpURLConnection) u.openConnection();
			conn.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "utf-8"));
			StringBuffer responseData = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				responseData.append(line);
			}
			reader.close();
			return responseData.toString();
		} catch (MalformedURLException e) {
			MLLogger.getLogger().severe("URL error: " + url);
			MLLogger.getLogger().severe(e.getLocalizedMessage());
		} catch (UnsupportedEncodingException e) {
			MLLogger.getLogger().severe(e.getLocalizedMessage());
		} catch (IOException e) {
			MLLogger.getLogger().severe(e.getLocalizedMessage());
		}
		return null;
	}

}
