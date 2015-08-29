/**
 * z.z.w.common.ClassTools.java
 */
package z.z.w.common;

import java.lang.reflect.Method;

/**
 * @author Wu Zhenzhen
 * @version 2013-7-29 下午08:17:02
 */
public class ClassTools {

	/**
	 * <br>
	 * Created on: 2013-7-29 下午08:17:02
	 */
	private ClassTools() {

	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-7-29 下午08:51:18
	 * 
	 * @param clazz
	 * @param methodName
	 * @param parameterTypes
	 * @return
	 */
	public static Method getDeclaredMethod(Class<?> clazz, String methodName,
			Class<?>... parameterTypes) {
		try {
			return clazz.getDeclaredMethod(methodName, parameterTypes);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-7-29 下午08:53:20
	 * 
	 * @param method
	 * @param clazz
	 * @param parameters
	 * @return
	 */
	public static Object invoke(Method method, Class<?> clazz,
			Object... parameters) {
		try {
			return method.invoke(clazz, parameters);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-7-29 下午08:17:16
	 * 
	 * @param className
	 * @return
	 */
	public static Class<?> getClass(String className) {
		try {
			return (Class.forName(className));
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-7-29 下午08:17:44
	 * 
	 * @param clazz
	 * @param paramClazz
	 * @param param
	 * @return
	 */
	public static Object getInterfaceInstanceByParam(Class<?> clazz,
			Class<?> paramClazz, Object param) {
		try {
			return clazz.getConstructor(paramClazz).newInstance(param);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-7-29 下午08:20:30
	 * 
	 * @param className
	 * @return
	 */
	public static Object newInstance(String className) {
		try {
			return getClass(className).newInstance();
		} catch (Exception e) {
			return null;
		}
	}

}
