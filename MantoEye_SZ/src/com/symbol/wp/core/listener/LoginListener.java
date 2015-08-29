package com.symbol.wp.core.listener;

import java.util.Locale;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

/**
 * Used to store information about a specific user. This class is used so that
 * the information is not scattered throughout the HttpSession. Only this object
 * is stored in the session for the user. This class implements the
 * HttpSessionBindingListener interface so that it can be notified of session
 * timeout and perform the proper cleanup.
 */
public class LoginListener implements HttpSessionBindingListener {

	// Data about the user that is cached
	private SessionContainer sessionContainer = null;

	/**
	 * The Locale object for the user. Although Struts stores a Locale for each
	 * user in the session, the locale is also maintained here.
	 */
	private Locale locale;

	public SessionContainer getSessionContainer() {
		return sessionContainer;
	}

	public void setSessionContainer(SessionContainer sessionContainer) {
		this.sessionContainer = sessionContainer;
	}

	/**
	 * Default Constructor
	 */
	public LoginListener() {
		super();
		initialize();
	}
	
	public void valueBound(HttpSessionBindingEvent event) {
		// Don't need to do anything, but still have to implement the
		// interface method.
		
	}

	/**
	 * The container calls this method when it is being unbound from the
	 * session.
	 */
	public void valueUnbound(HttpSessionBindingEvent event) {
		// Perform resource cleanup
		cleanUp();
	}

	/**
	 * Set the locale for the user.
	 */
	public void setLocale(Locale aLocale) {
		locale = aLocale;
	}

	/**
	 * Retrieve the locale for the user.
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * The container calls this method when it is being bound to the session.
	 */
	
	/**
	 * Initialize all of the required resources
	 */
	private void initialize() {
		
	}

	/**
	 * Clean up any open resources. The shopping cart is left intact
	 * intentionally.
	 */
	public void cleanUp() {
		this.setSessionContainer(null);
	}

}
