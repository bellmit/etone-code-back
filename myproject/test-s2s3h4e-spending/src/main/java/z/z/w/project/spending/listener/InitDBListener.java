/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.spending.listener.InitDBListener.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-12 下午02:19:59 
 *   LastChange: 2013-11-12 下午02:19:59 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.spending.listener;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import z.z.w.project.spending.service.ResourceRoteService;
import z.z.w.project.spending.service.SpendingService;
import z.z.w.project.util.common.LogTools;

/**
 * z.z.w.project.spending.listener.InitDBListener.java
 */
public class InitDBListener implements ServletContextListener, ServletContextAttributeListener, HttpSessionListener, HttpSessionAttributeListener, HttpSessionActivationListener, HttpSessionBindingListener, ServletRequestListener, ServletRequestAttributeListener {

	private static ApplicationContext ctx = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletRequestAttributeListener#attributeAdded(javax.servlet .ServletRequestAttributeEvent)
	 */
	public void attributeAdded(ServletRequestAttributeEvent arg0) {
		// ServletRequestAttributeListener.attributeAdded()

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletRequestAttributeListener#attributeRemoved(javax. servlet.ServletRequestAttributeEvent)
	 */
	public void attributeRemoved(ServletRequestAttributeEvent arg0) {
		// ServletRequestAttributeListener.attributeRemoved()

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletRequestAttributeListener#attributeReplaced(javax .servlet.ServletRequestAttributeEvent)
	 */
	public void attributeReplaced(ServletRequestAttributeEvent arg0) {
		// ServletRequestAttributeListener.attributeReplaced()

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletRequestListener#requestDestroyed(javax.servlet. ServletRequestEvent)
	 */
	public void requestDestroyed(ServletRequestEvent arg0) {
		// ServletRequestListener.requestDestroyed()

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletRequestListener#requestInitialized(javax.servlet .ServletRequestEvent)
	 */
	public void requestInitialized(ServletRequestEvent arg0) {
		// ServletRequestListener.requestInitialized()

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSessionBindingListener#valueBound(javax.servlet .http.HttpSessionBindingEvent)
	 */
	public void valueBound(HttpSessionBindingEvent arg0) {
		// HttpSessionBindingListener.valueBound()

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSessionBindingListener#valueUnbound(javax.servlet .http.HttpSessionBindingEvent)
	 */
	public void valueUnbound(HttpSessionBindingEvent arg0) {
		// HttpSessionBindingListener.valueUnbound()

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSessionActivationListener#sessionDidActivate(javax .servlet.http.HttpSessionEvent)
	 */
	public void sessionDidActivate(HttpSessionEvent arg0) {
		// HttpSessionActivationListener.sessionDidActivate()

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSessionActivationListener#sessionWillPassivate (javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionWillPassivate(HttpSessionEvent arg0) {
		// HttpSessionActivationListener.sessionWillPassivate()

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSessionAttributeListener#attributeAdded(javax. servlet.http.HttpSessionBindingEvent)
	 */
	public void attributeAdded(HttpSessionBindingEvent arg0) {
		// HttpSessionAttributeListener.attributeAdded()

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSessionAttributeListener#attributeRemoved(javax .servlet.http.HttpSessionBindingEvent)
	 */
	public void attributeRemoved(HttpSessionBindingEvent arg0) {
		// HttpSessionAttributeListener.attributeRemoved()

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSessionAttributeListener#attributeReplaced(javax .servlet.http.HttpSessionBindingEvent)
	 */
	public void attributeReplaced(HttpSessionBindingEvent arg0) {
		// HttpSessionAttributeListener.attributeReplaced()

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http .HttpSessionEvent)
	 */
	public void sessionCreated(HttpSessionEvent arg0) {
		// HttpSessionListener.sessionCreated()

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet .http.HttpSessionEvent)
	 */
	public void sessionDestroyed(HttpSessionEvent arg0) {
		// HttpSessionListener.sessionDestroyed()

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextAttributeListener#attributeAdded(javax.servlet .ServletContextAttributeEvent)
	 */
	public void attributeAdded(ServletContextAttributeEvent arg0) {
		// ServletContextAttributeListener.attributeAdded()

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextAttributeListener#attributeRemoved(javax. servlet.ServletContextAttributeEvent)
	 */
	public void attributeRemoved(ServletContextAttributeEvent arg0) {
		// ServletContextAttributeListener.attributeRemoved()

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextAttributeListener#attributeReplaced(javax .servlet.ServletContextAttributeEvent)
	 */
	public void attributeReplaced(ServletContextAttributeEvent arg0) {
		// ServletContextAttributeListener.attributeReplaced()

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet. ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		// ServletContextListener.contextDestroyed()

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet .ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent arg0) {
		// ServletContextListener.contextInitialized()
		LogTools.getLogger(getClass()).info("Init check db data ...");
		ctx = WebApplicationContextUtils.getWebApplicationContext(arg0.getServletContext());

		ResourceRoteService resourceRoteService = (ResourceRoteService) ctx.getBean("resourceRoteService");
		resourceRoteService.initResourceRote();

		SpendingService spendingService = (SpendingService) ctx.getBean("spendingService");
		spendingService.initReveExpen();

	}

}
