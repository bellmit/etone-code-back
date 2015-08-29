/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.spending.interceptor.EncodingInterceptor.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-18 下午04:27:03 
 *   LastChange: 2013-11-18 下午04:27:03 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.spending.interceptor;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * z.z.w.project.spending.interceptor.EncodingInterceptor.java
 */
public class EncodingInterceptor extends AbstractInterceptor {

	/**
	 * <br>
	 * Created on: 2013-11-18 下午04:27:26
	 */
	private static final long serialVersionUID = -5169418691426516178L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.interceptor.AbstractInterceptor#intercept(com.opensymphony.xwork2.ActionInvocation)
	 */
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		// ActionContext actionContext = actionInvocation.getInvocationContext();
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getRequest().setCharacterEncoding("utf-8");
		return invocation.invoke();
	}

}
