package org.easythread.core;

/**
 * 处理class对象
 * 
 * @author cong
 *
 */
public interface Handler {
	/**
	 * 处理对象并返会一个实例
	 * 
	 * @param clazz
	 *            要处理的clazz的对象
	 * @return 一个新创建的对象实例;
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public Object handle();

	/**
	 * 获得此 Handler绑定的 Class
	 * 
	 * @return 此 Handler 的 Class
	 */
	public Class acquireClass();
}
