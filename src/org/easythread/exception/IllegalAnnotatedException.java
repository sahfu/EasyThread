package org.easythread.exception;

/**
 * 使用EasyThread注解不正确时抛出
 * 
 * @author cong
 *
 */
public class IllegalAnnotatedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IllegalAnnotatedException(String string) {
		super(string);
	}
}
