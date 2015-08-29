package com.symbol.wp.core.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.dto.VBasePermissions;
import com.symbol.wp.core.listener.LoginListener;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.modules.utils.SpringContextUtil;

public class TopMenuTag extends TagSupport {

	private String className;
	
	private static final Log logger = LogFactory.getLog(TopMenuTag.class);
	
	
	@Override
	public int doStartTag() throws JspTagException {
		return EVAL_BODY_INCLUDE;
	}

	@Override
	public int doEndTag() throws JspTagException {
		
		
		LoginListener loginListener = (LoginListener) pageContext.getSession().getAttribute(VarConstants.LOGIN_LISTENER_KEY);
		WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(pageContext.getServletContext());
	    ICommonManager commonService = (ICommonManager) context.getBean("commonManagerImpl");
		String userNo  =  loginListener.getSessionContainer().getUserNo();

		JspWriter out = pageContext.getOut();
		StringBuffer contents = new StringBuffer();
		 String html="";
	        try {
	        	 List<VBasePermissions> subList  = commonService.getTopMenu(userNo);
	             if(subList!=null&&subList.size()>0){
	             for(int i=0;i<subList.size();i++){	   
	            	 VBasePermissions v = subList.get(i);
	            	 contents.append("<li><a id=\"topmemu_a_"+i+"\" href=\"#\" onclick=\"topmenuClick('"+v.getId()+"');changeMenuView("+i+","+subList.size()+");\"><span id=\"topmemu_span_"+i+"\">"+v.getVcMenuName()+"</span></a></li>");
	             }                    
	        	}
	             out.println(contents.toString());
	        } catch (Exception e) {
	        	logger.error(e.getMessage());
	        }

		return EVAL_PAGE;
	}
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	
}