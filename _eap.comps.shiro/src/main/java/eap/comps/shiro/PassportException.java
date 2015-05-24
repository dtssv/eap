package eap.comps.shiro;

import eap.exception.ErrorMsg;

/**
 * <p> Title: </p>
 * <p> Description: </p>
 * @作者 chiknin@gmail.com
 * @创建时间 
 * @版本 1.00
 * @修改记录
 * <pre>
 * 版本       修改人         修改时间         修改内容描述
 * ----------------------------------------
 * 
 * ----------------------------------------
 * </pre>
 */
public class PassportException extends Exception {

	protected ErrorMsg errorMsg;

	public PassportException() {
		super();
		this.errorMsg = new ErrorMsg();
	}

	public PassportException(String message) {
		super(message);
		this.errorMsg = new ErrorMsg();
	}

	public PassportException(String message, Throwable cause) {
		super(message, cause);
		this.errorMsg = new ErrorMsg();
	}

	public PassportException(String msgCode, String[] msgParams, String message, Throwable cause) {
		super(message, cause);
		this.errorMsg = new ErrorMsg(msgCode, msgParams);
	}

	public PassportException(String msgCode, String message, Throwable cause) {
		super(message, cause);
		this.errorMsg = new ErrorMsg(msgCode);
	}

	public PassportException(String msgCode, String message) {
		super(message);
		this.errorMsg = new ErrorMsg(msgCode);
	}

	public PassportException(String msgCode, String msgParam, String message, Throwable cause) {
		super(message, cause);
		this.errorMsg = new ErrorMsg(msgCode, msgParam);
	}

	public ErrorMsg getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(ErrorMsg errorMsg) {
		this.errorMsg = errorMsg;
	}
}