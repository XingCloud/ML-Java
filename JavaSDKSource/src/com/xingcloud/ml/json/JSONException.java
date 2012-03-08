package com.xingcloud.ml.json;

public class JSONException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public JSONException(String message) {
		super(message);
	}

	public JSONException(String message, Throwable throwable) {
		super(message, throwable);
	}
}