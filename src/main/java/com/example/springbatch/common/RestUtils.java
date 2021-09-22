package com.example.springbatch.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class RestUtils {

	private RestUtils() {
		throw new IllegalStateException("Can not instanciate");
	}

	public static <T> ResponseEntity<RestResponse<T>> successResponse(final T data, final HttpStatus statusCode,
			final String message) {
		return new ResponseEntity<RestResponse<T>>(new RestResponse<T>(null, message, data), statusCode);
	}

	public static <T> ResponseEntity<RestResponse<T>> successResponse(final T data, final String message) {
		return successResponse(data, HttpStatus.OK, message);

	}

	public static <T> ResponseEntity<RestResponse<T>> successResponse(final T data, final HttpStatus statusCode) {
		return new ResponseEntity<RestResponse<T>>(new RestResponse<T>(data), statusCode);
	}

	public static <T> ResponseEntity<RestResponse<T>> successResponse(final T data) {
		return successResponse(data, HttpStatus.OK);

	}

	public static <T> ResponseEntity<RestResponse<?>> errorResponse(final String errorMessage, final String errorCode,
			final HttpStatus statusCode) {
		return new ResponseEntity<RestResponse<?>>(new RestResponse<T>(errorCode, errorMessage, null), statusCode);

	}

	public static <T> ResponseEntity<RestResponse<?>> errorResponseWithPayload(final String errorMessage,
			final String errorCode, final HttpStatus statusCode, T payload) {
		return new ResponseEntity<RestResponse<?>>(new RestResponse<T>(errorCode, errorMessage, payload), statusCode);

	}
}
