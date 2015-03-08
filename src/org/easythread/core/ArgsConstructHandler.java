package org.easythread.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ArgsConstructHandler implements Handler {

	private Constructor constructor;
	private Class clazz;
	private Object[] objects;

	public ArgsConstructHandler(Class clazz, Object[] objects) {
		this.clazz = clazz;
		this.objects = objects;
		Class[] classes = new Class[objects.length];
		for (int i = 0; i < classes.length; i++) {
			classes[i] = objects[i].getClass();
		}
		try {
			this.constructor = clazz.getConstructor(classes);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Object handle() {
		Object object = null;
		try {
			object = constructor.newInstance(objects);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return object;
	}

	@Override
	public Class acquireClass() {
		return clazz;
	}

}
