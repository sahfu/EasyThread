package org.easythread.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 
 * 
 * @author cong
 */
public class EasyThreadTask extends Thread {

	private Method method;
	private Method beforeMethod;
	private Method afterMethod;
	private Class clazz;
	private Object instance;

	public EasyThreadTask(Method method, Class clazz, Method beforeMethods,
			Method afterMethods,String name) {
		super(name);
		this.afterMethod = afterMethods;
		this.beforeMethod = beforeMethods;
		this.method = method;
		this.clazz = clazz;
	}

	@Override
	public void run() {
		try {
			Object instance = clazz.newInstance();
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
		} catch (InstantiationException e) {

		} 
	}

}
