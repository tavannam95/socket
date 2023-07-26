package com.a2m.auth.util;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AjaxResult implements Serializable{
	private static final long serialVersionUID = 1L;
	private String status;
	private String message;
	private Object responseData;
}
