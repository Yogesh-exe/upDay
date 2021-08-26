package com.upday.article.exception.handler;

import lombok.Getter;

@Getter
class ErrorDetails {

	private final String errorMessage;

	private final String rectification;

	public ErrorDetails(String errorMessage) {
		this(errorMessage,"N/A");
	}

	public ErrorDetails(String errorMessage, String rectification) {
		this.errorMessage = errorMessage;
		this.rectification = rectification;
	}
}
