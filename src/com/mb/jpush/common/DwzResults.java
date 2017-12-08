package com.mb.jpush.common;

public class DwzResults {
	/** ajax请求返回的RESULT **/
	public final static String OPERATION_DONE = "ajaxDone";
	
	/**请求超时*/
	public final static int STATUS_CODE_TIMEOUT = 301;
	
	/** 请求错误状态码 **/
	public final static int STATUS_CODE_ERROR = 300;

	/** 请求错成功态码 **/
	public final static int STATUS_CODE_SUCCESS = 200;
	
	/**回调类型*/
	public final static String CALL_BACK_TYPE_CLOASE_CURRENT = "closeCurrent";
	
	public final static String CALL_BACK_TYPE_FORWARD = "forward";
}
