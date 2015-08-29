/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.action.BaseAction.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-3 下午03:27:44 
 *   LastChange: 2013-11-3 下午03:27:44 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.action;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;

import z.z.w.project.test.common.FastjsonFilter;
import z.z.w.project.util.common.LogTools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * z.z.w.project.test.action.BaseAction.java
 * 
 * @param <T>
 */
@Action
public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {

	/**
	 * <br>
	 * Created on: 2013-11-3 下午03:27:57
	 */
	private static final long serialVersionUID = -7023150975027974773L;

	/**
	 * 将对象转换成JSON字符串，并响应回前台
	 * 
	 * @param object
	 * @param includesProperties
	 *            需要转换的属性
	 * @param excludesProperties
	 *            不需要转换的属性
	 * @throws IOException
	 */
	public void writeJsonByFilter(Object object, String[] includesProperties,
			String[] excludesProperties) throws IOException {
		FastjsonFilter filter = new FastjsonFilter();// excludes优先于includes
		if (excludesProperties != null && excludesProperties.length > 0) {
			filter.getExcludes().addAll(
					Arrays.<String> asList(excludesProperties));
		}
		if (includesProperties != null && includesProperties.length > 0) {
			filter.getIncludes().addAll(
					Arrays.<String> asList(includesProperties));
		}
		String json = null;
		String userAgent = getRequest().getHeader("User-Agent");
		if (userAgent.indexOf("MSIE") > -1
				&& (userAgent.indexOf("MSIE 6") > -1)) {
			// 使用SerializerFeature.BrowserCompatible特性会把所有的中文都会序列化为\\uXXXX这种格式，字节数会多一些，但是能兼容IE6
			json = JSON.toJSONString(object, filter,
					SerializerFeature.WriteDateUseDateFormat,
					SerializerFeature.DisableCircularReferenceDetect,
					SerializerFeature.BrowserCompatible);
		} else {
			// 使用SerializerFeature.WriteDateUseDateFormat特性来序列化日期格式的类型为yyyy-MM-dd
			// hh24:mi:ss
			// 使用SerializerFeature.DisableCircularReferenceDetect特性关闭引用检测和生成
			json = JSON.toJSONString(object, filter,
					SerializerFeature.WriteDateUseDateFormat,
					SerializerFeature.DisableCircularReferenceDetect);
		}

		getResponse().setContentType("text/html;charset=utf-8");
		getResponse().getWriter().write(json);
		getResponse().getWriter().flush();
		getResponse().getWriter().close();
	}

	/**
	 * 将对象转换成JSON字符串，并响应回前台
	 * 
	 * @param object
	 * @throws IOException
	 */
	public void writeJson(Object object) {
		try {
			writeJsonByFilter(object, null, null);
		} catch (Exception e) {
			LogTools.getLogger(getClass())
					.error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
							new Object[] { "BaseAction", "writeJson()",
									e.getMessage(), e.getCause(), e.getClass() });
		}
	}

	/**
	 * 将对象转换成JSON字符串，并响应回前台
	 * 
	 * @param object
	 * @param includesProperties
	 *            需要转换的属性
	 */
	public void writeJsonByIncludesProperties(Object object,
			String[] includesProperties) {
		try {
			writeJsonByFilter(object, includesProperties, null);
		} catch (Exception e) {
			LogTools.getLogger(getClass())
					.error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
							new Object[] { "BaseAction",
									"writeJsonByIncludesProperties()",
									e.getMessage(), e.getCause(), e.getClass() });
		}
	}

	/**
	 * 将对象转换成JSON字符串，并响应回前台
	 * 
	 * @param object
	 * @param excludesProperties
	 *            不需要转换的属性
	 */
	public void writeJsonByExcludesProperties(Object object,
			String[] excludesProperties) {
		try {
			writeJsonByFilter(object, null, excludesProperties);
		} catch (Exception e) {
			LogTools.getLogger(getClass())
					.error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}",
							new Object[] { "BaseAction",
									"writeJsonByExcludesProperties()",
									e.getMessage(), e.getCause(), e.getClass() });
		}
	}

	/**
	 * 获得request
	 * 
	 * @return
	 */
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/**
	 * 获得response
	 * 
	 * @return
	 */
	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	/**
	 * 获得session
	 * 
	 * @return
	 */
	public HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public T getModel() {
		return null;
	}

}
