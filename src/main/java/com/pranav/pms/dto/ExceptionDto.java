package com.pranav.pms.dto;

import lombok.Data;

@Data
public class ExceptionDto {
	
	private String errorCode;
	private String errorMessage;
	private String timeStamp;

}
