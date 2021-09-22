package com.example.springbatch.common;

import lombok.Data;

@Data
public class RestResponse<T> {
	private String message;
	private String errorCode;
	private T data;

	public RestResponse() {
	}

	public RestResponse(T data) {
		this(null, null, data);
	}

	public RestResponse(String errorCode, String message, T data) {
		this.message = message;
		this.errorCode = errorCode;
		this.data = data;
	}
}
