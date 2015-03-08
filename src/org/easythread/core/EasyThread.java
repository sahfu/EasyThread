package org.easythread.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import org.easythread.annotation.ThreadAfter;
import org.easythread.annotation.ThreadBefore;
import org.easythread.annotation.Threads;
import org.easythread.exception.IllegalAnnotatedException;

/**
 * 执行EasyThread
 * 
 * @author cong
 *
 */
public class EasyThread {

	/**
	 * 查找指定类的所有注解了指定annotation 的方法
	 * 
	 * @param clazz
	 *            要查找的类
	 * @param annotation
	 *            要查找的注解
	 * @return 含有指定注解method 的 List
	 */
	private static List<Method> getAllAnnotatedMehtod(Class clazz,
			Class annotation) {
		List<Method> list = new LinkedList<Method>();
		for (Method method : clazz.getMethods()) {
			if (method.isAnnotationPresent(annotation)) {
				list.add(method);
			}
		}
		return list;
	}

	/**
	 * 查找指定类的唯一注解了指定annotation 的方法
	 * 
	 * @param clazz
	 *            要查找的类
	 * @param annotation
	 *            要查找的注解
	 * @return 返回注解的方法
	 * @throws IllegalAnnotatedException
	 *             当此注解不唯一时抛出
	 */
	private static Method getUniqueAnnotatedMehtod(Class clazz, Class annotation)
			throws IllegalAnnotatedException {
		Method uniqueMethod = null;
		for (Method method : clazz.getMethods()) {
			if (method.isAnnotationPresent(annotation)) {
				if (uniqueMethod == null)
					uniqueMethod = method;
				else
					throw new IllegalAnnotatedException(
							"@ThreadBefore or @ThreadAfter is redundant");
			}
		}
		return uniqueMethod;
	}

	/**
	 * 获取要运行的线程数量
	 * 
	 * @param method
	 * @return
	 * @throws IllegalAnnotatedException
	 */
	private static int getThreadNum(Method method)
			throws IllegalAnnotatedException {
		if (!method.isAnnotationPresent(Threads.class)) {
			throw new IllegalAnnotatedException(
					"this method did not annotate @Threads");
		}
		Threads threadAnnotation = method.getAnnotation(Threads.class);
		return threadAnnotation.num();
	}

	/**
	 * 执行EasyThread
	 * 
	 * @param clazz
	 *            注解了的class
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 * @throws IllegalAnnotatedException
	 *             使用EasyThread注解不正确时抛出
	 */
	private static void run0(Handler handler) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			InstantiationException, IllegalAnnotatedException {
		// 准备切面函数
		List<Method> runMethods = getAllAnnotatedMehtod(handler.acquireClass(),
				Threads.class);
		if (runMethods.isEmpty())
			throw new IllegalAnnotatedException("no method annotate @Threads");
		Method beforeMethod = getUniqueAnnotatedMehtod(handler.acquireClass(),
				ThreadBefore.class);
		Method afterMethod = getUniqueAnnotatedMehtod(handler.acquireClass(),
				ThreadAfter.class);
		// 运行线程
		for (Method method : runMethods) {
			int threadNum = getThreadNum(method);
			String methodName = method.getName();
			if (threadNum == 1) {
				new EasyThreadTask(method, handler.handle(), beforeMethod,
						afterMethod, methodName).start();
			} else {
				for (int i = 0; i < threadNum; i++) {
					StringBuffer threadName = new StringBuffer(methodName);
					threadName.append("-");
					threadName.append(i + 1);
					new EasyThreadTask(method, handler.handle(), beforeMethod,
							afterMethod, threadName.toString()).start();
				}
			}
		}
	}

	/**
	 * 执行EasyThread
	 * 
	 * @param clazz
	 *            有@Threads注解了的class
	 */
	public static void run(Class clazz) {
		try {
			run0(new NoArgsConstructHandler(clazz));
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAnnotatedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 执行EasyThread 调用有参数的构造函数
	 * 
	 * @param clazz
	 *            有@Threads注解了的class
	 * @param objects
	 *            构造函数的参数
	 */
	public static void run(Class clazz, Object... objects) {
		try {
			run0(new ArgsConstructHandler(clazz, objects));
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAnnotatedException e) {
			e.printStackTrace();
		}
	}

}
