package com.cava.examples.services.book.modelError;

import lombok.Data;

@Data
public class ModelError {
    private int code;
    private String message;
    private String messageError;

    public ModelError() {
    }

	public ModelError(int code, String message, String messageError) {
		super();
		this.code = code;
		this.message = message;
		this.messageError = messageError;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessageError() {
		return messageError;
	}

	public void setMessageError(String messageError) {
		this.messageError = messageError;
	}
    
}
