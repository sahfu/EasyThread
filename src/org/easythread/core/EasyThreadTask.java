package org.easythread.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 线程任务
 * 
 * @author cong
 */
public class EasyThreadTask extends Thread {

	private Method method;
	private Method beforeMethod;
	private Method afterMethod;
	private Object instance;

	public EasyThreadTask(Method method, Object instance, Method beforeMethods,
			Method afterMethods, String name) {
		super(name);
		this.afterMethod = afterMethods;
		this.beforeMethod = beforeMethods;
		this.method = method;
		this.instance = instance;
	}

	@Override
	public void run() {
		try {
			if (beforeMethod != null)
				beforeMethod.invoke(instance);
			method.invoke(instance);
			if (afterMethod != null)
				afterMethod.invoke(instance);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			Throwable throwable = e.getTargetException();
			throwable.printStackTrace();
		}
	}

}
