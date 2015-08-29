package com.symbol.wp.core.tag;

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

public class SubMenuTag extends TagSupport {

	private String parentNo;
	private String className;
	
	private static final Log logger = LogFactory.getLog(SubMenuTag.class);
	
	
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
		
		logger.info(parentNo+"<--parentNo");
	        try {
	        	 List<VBasePermissions> subList  = commonService.getSubMenuList(userNo,parentNo);
	             if(subList!=null&&subList.size()>0){
	            	 for(int i=0;i<subList.size();i++){	   
		             VBasePermissions v = subList.get(i);           
	            	 contents.append("<li><a  href=\""+v.getVcRedirectUrl()+ "&permId="+v.getId()+"\"  target=\"frmmain\" id=\"submemu_a_"+i+"\"  onclick=\"changeMenuView("+i+","+subList.size()+");\"><span id=\"submemu_span_"+i+"\">"+v.getVcMenuName()+"</span></a></li>");
	             }                    
	        	}
	             out.println(contents.toString());
	        } catch (Exception e) {
	        	logger.error(e.getMessage());
	        }

		return EVAL_PAGE;
	}


	public String getParentNo() {
		return parentNo;
	}

	public void setParentNo(String parentNo) {
		this.parentNo = parentNo;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	
}