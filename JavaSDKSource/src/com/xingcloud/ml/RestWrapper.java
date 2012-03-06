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
		this.config.put("service_name", serviceName);
		this.config.put("api_key", apiKey);
		this.config.put("locale", locale);
	}

	/***
	 * 获取默认文件信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Serializable> getFileInfo() {
		Map<String, Serializable> data = new HashMap<String, Serializable>();
		data.put("service_name", config.get("service_name"));
		Date date = new Date();
		String timeStamp = Long.toString(date.getTime());
		data.put("timestamp", timeStamp);
		String hash = "";
		try {
			hash = MD5.encode(timeStamp + config.get("api_key"));
		} catch (Exception e1) {
			MLLogger.getLogger().error(
					"RestWrapper getFileInfo: MD5 encode error");
			MLLogger.getLogger().error(e1.getMessage());
			return null;
		}
		data.put("hash", hash);
		data.put("locale", config.get("locale"));
		String result = this.get(this.fileInfoURL, data);
		Map<String, Serializable> resultMaps;
		try {
			resultMaps = JSONUtil.toMap(result);
		} catch (JSONException e) {
			MLLogger.getLogger()
					.error("RestWrapper getFileInfo: Cannot not turn file info response into json");
			MLLogger.getLogger().error(e.getLocalizedMessage());
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
	@SuppressWarnings("unchecked")
	public Map<String, Serializable> getDefaultFile() {
		Map<String, Serializable> fileInfoData = this.getFileInfo();
		String defaultFileURL = ((Map<String, String>) fileInfoData.get("data"))
				.get("request_address");
		String result = this.get(defaultFileURL, null);
		Map<String, Serializable> responseData = new HashMap<String, Serializable>();
		try {
			responseData.put("filecontent",
					(Serializable) JSONUtil.toMap(result));
		} catch (JSONException e) {
			MLLogger.getLogger()
					.error("RestWrapper getDefaultFile: Cannot not turn default file response into json");
			MLLogger.getLogger().error(e.getLocalizedMessage());
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
		Map<String, Serializable> data = new HashMap<String, Serializable>();
		data.put("service_name", config.get("service_name"));
		Date date = new Date();
		String timeStamp = Long.toString(date.getTime());
		data.put("timestamp", timeStamp);
		String hash = "";
		try {
			hash = MD5.encode(timeStamp + config.get("api_key"));
		} catch (Exception e) {
			MLLogger.getLogger().error("RestWrapper stringAdd: MD5 encode error");
			MLLogger.getLogger().error(e.getMessage());
			return;
		}
		data.put("hash", hash);
		data.put("data", string);
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
		Map<String, Serializable> data = new HashMap<String, Serializable>();
		data.put("service_name", config.get("service_name"));
		Date date = new Date();
		String timeStamp = Long.toString(date.getTime());
		data.put("timestamp", timeStamp);
		String hash = "";
		try {
			hash = MD5.encode(timeStamp + config.get("api_key"));
		} catch (Exception e) {
			MLLogger.getLogger().error("RestWrapper stringAdd: MD5 encode error");
			MLLogger.getLogger().error(e.getMessage());
			return;
		}
		data.put("hash", hash);
		try {
			data.put("data", JSONUtil.toJSON(stringList));
		} catch (JSONException e) {
			MLLogger.getLogger()
					.error("RestWrapper stringAdd: Cannot not turn string list into json string");
			MLLogger.getLogger().error(e.getLocalizedMessage());
			return;
		}
		@SuppressWarnings("unused")
		String result = this.post(stringAddURL, data);
	}

	protected String post(String url, Map<String, Serializable> data) {
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
			MLLogger.getLogger().error("RestWrapper post: URL error: " + url);
			MLLogger.getLogger().error(e.getLocalizedMessage());
		} catch (ProtocolException e) {
			MLLogger.getLogger().error("RestWrapper post: Protocol error");
			MLLogger.getLogger().error(e.getLocalizedMessage());
		} catch (UnsupportedEncodingException e) {
			MLLogger.getLogger().error(
					"RestWrapper post: " + e.getLocalizedMessage());
		} catch (IOException e) {
			MLLogger.getLogger().error(
					"RestWrapper post: " + e.getLocalizedMessage());
		}
		return null;
	}

	protected String get(String url, Map<String, Serializable> data) {
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
			MLLogger.getLogger().error("RestWrapper get: URL error: " + url);
			MLLogger.getLogger().error(e.getLocalizedMessage());
		} catch (UnsupportedEncodingException e) {
			MLLogger.getLogger().error(
					"RestWrapper get: " + e.getLocalizedMessage());
		} catch (IOException e) {
			MLLogger.getLogger().error(
					"RestWrapper get: " + e.getLocalizedMessage());
		}
		return null;
	}

}
