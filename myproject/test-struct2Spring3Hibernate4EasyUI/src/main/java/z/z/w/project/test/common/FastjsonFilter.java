/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.common.FastjsonFilter.java
 *         Desc: 主要用于过滤不需要序列化的属性，或者包含需要序列化的属性
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-3 下午04:43:27 
 *   LastChange: 2013-11-3 下午04:43:27 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.common;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.collection.internal.PersistentSet;

import com.alibaba.fastjson.serializer.PropertyFilter;

/**
 * z.z.w.project.test.common.FastjsonFilter.java
 */
public class FastjsonFilter implements PropertyFilter {

	private final Set<String> includes = new HashSet<String>();
	private final Set<String> excludes = new HashSet<String>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.alibaba.fastjson.serializer.PropertyFilter#apply(java.lang.Object,
	 * java.lang.String, java.lang.Object)
	 */
	public boolean apply(Object source, String name, Object value) {
		if (value != null && (value instanceof PersistentSet)) {// 避免hibernate对象循环引用，一切Set属性不予序列化
			return false;
		}
		if (excludes.contains(name)) {
			return false;
		}
		if (excludes.contains(source.getClass().getSimpleName() + "." + name)) {
			return false;
		}
		if (includes.size() == 0 || includes.contains(name)) {
			return true;
		}
		if (includes.contains(source.getClass().getSimpleName() + "." + name)) {
			return true;
		}
		return false;
	}

	/**
	 * <br>
	 * Created on: 2013-11-3 下午04:44:54
	 * 
	 * @return the includes
	 */
	public Set<String> getIncludes() {
		return includes;
	}

	/**
	 * <br>
	 * Created on: 2013-11-3 下午04:44:54
	 * 
	 * @return the excludes
	 */
	public Set<String> getExcludes() {
		return excludes;
	}

}
