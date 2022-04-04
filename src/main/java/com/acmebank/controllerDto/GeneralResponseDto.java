package com.acmebank.controllerDto;

public class GeneralResponseDto<T> {

	private String code;
	private  String message;
	private T response;
	private static String SUCCESS_CODE ="000";
	private static String FAIL_CODE="001"; // may design different error code if business requires.

	
	public GeneralResponseDto(String code,String message, T response) {
		super();
		this.code = code;
		this.response = response;
		this.message = message;
	}
	
	public static<T> GeneralResponseDto<T> success(T response)
	{
		return new GeneralResponseDto<T>(SUCCESS_CODE,"success",response);
	}
	
	public static<T> GeneralResponseDto<T> fail(T response,String errorMessage)
	{
		return new GeneralResponseDto<T>(FAIL_CODE,errorMessage,response);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public T getResponse() {
		return response;
	}

	public void setResponse(T response) {
		this.response = response;
	}

	public  String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
