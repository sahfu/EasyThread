package org.easythread.core;

/**
 * 处理使用无参构造函数的Class对象
 * 
 * @author cong
 *
 */
public class NoArgsConstructHandler implements Handler {
	private Class clazz;

	public NoArgsConstructHandler(Class clazz) {
		this.clazz = clazz;
	}

	@Override
	public Object handle() {
		Object object = null;
		try {
			object = clazz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return object;
	}

	@Override
	public Class acquireClass() {
		return clazz;
	}

}
