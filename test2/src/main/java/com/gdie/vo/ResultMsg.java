package com.gdie.vo;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

/**
 * 返回信息
 * 
 * @author HHP
 * 
 */
public class ResultMsg implements Serializable{

	private Boolean success = false;

	private String message = "";

	private Object other;

	public static ResultMsg getInstance() {
		return new ResultMsg();
	}

	/**
	 * 设置返回的信息和成功与否标记，信息是空，表示成功，否则失败
	 * 
	 * @param message
	 * @return
	 */
	public static ResultMsg getInstance(String message) {
		return new ResultMsg(message);
	}

	public ResultMsg() {
		// TODO Auto-generated constructor stub
	}

	public ResultMsg(String message) {
		super();
		this.message = message;
		this.success = StringUtils.isEmpty(message); // 根据消息是否为设置成功失败信息，空表示成功
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getOther() {
		return other;
	}

	public void setOther(Object other) {
		this.other = other;
	}
}
