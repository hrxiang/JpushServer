package com.mb.jpush.common;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


public class DwzAjaxDone {
	private int statusCode = DwzResults.STATUS_CODE_SUCCESS;// 请求状态码

	private String message = null;// 错误信息

	private String callbackType = null;

	private String forwardUrl = null;// 跳转URL

	private String navTabId = null;

	private String[] params = null;

	private String ajaxForward(int statusCode, String message, String navTabId,
			String forwardUrl, String callbackType, String[] params) {
		this.statusCode = statusCode;
		this.message = message;
		this.forwardUrl = forwardUrl;
		this.navTabId = navTabId;
		this.callbackType = callbackType;
		this.params = params;
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		request.setAttribute("statusCode", statusCode);
		request.setAttribute("message", message);
		request.setAttribute("navTabId", navTabId);
		request.setAttribute("forwardUrl", forwardUrl);
		request.setAttribute("callbackType", callbackType);
		if (null != params) {
			request.setAttribute("params",
					Arrays.toString(params).replace("[", "").replace("]", ""));
		} else {
			request.setAttribute("params", params);
		}
		return DwzResults.OPERATION_DONE;

	}

	/**
	 * ajax 提交后跳转 提示成功不关闭当前页面
	 * 
	 * @param message
	 * @return
	 */
	protected String ajaxForwardSuccess(String message, String[] params) {
		return ajaxForward(DwzResults.STATUS_CODE_SUCCESS, message, "", "", "",
				params);
	}

	/**
	 * ajax 提交后跳转 提示成功并关闭当前页面
	 * 
	 * @param message
	 * @return
	 */
	protected String ajaxForwardSuccessCloseCurrent(String message,
			String[] params) {
		return ajaxForward(DwzResults.STATUS_CODE_SUCCESS, message, "", "",
				DwzResults.CALL_BACK_TYPE_CLOASE_CURRENT, params);
	}

	/**
	 * ajax 提交后跳转 提示失败不关闭当前页面
	 * 
	 * @param message
	 * @return
	 */
	protected String ajaxForwardError(String message, String[] params) {
		return ajaxForward(DwzResults.STATUS_CODE_ERROR, message, "", "", "",
				params);
	}

	/**
	 * ajax 提交失败后跳转 提示失败并关闭当前页面
	 * 
	 * @param message
	 * @return
	 */
	protected String ajaxForwardErrorCloseCurrnet(String message,
			String[] params) {
		return ajaxForward(DwzResults.STATUS_CODE_ERROR, message, "", "",
				DwzResults.CALL_BACK_TYPE_CLOASE_CURRENT, params);
	}

	public int getStatusCode() {

		return statusCode;

	}

	public String getMessage() {

		return message;

	}

	public String getForwardUrl() {

		return forwardUrl;

	}

	public void setForwardUrl(String forwardUrl) {

		this.forwardUrl = forwardUrl;

	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCallbackType() {
		return callbackType;
	}

	public void setCallbackType(String callbackType) {
		this.callbackType = callbackType;
	}

	public String getNavTabId() {
		return navTabId;
	}

	public void setNavTabId(String navTabId) {
		this.navTabId = navTabId;
	}

	public String[] getParams() {
		return params;
	}

	public void setParams(String[] params) {
		this.params = params;
	}
}
