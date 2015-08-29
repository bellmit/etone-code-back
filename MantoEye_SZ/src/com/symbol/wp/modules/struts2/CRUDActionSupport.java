package com.symbol.wp.modules.struts2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

/**
 * Struts2中典型CRUD Action的规范基类.
 * 
 * 声明对Preparable,ModelDriven接口的使用,并规范了CRUD函数和返回值的命名.
 *
 * @param <T> CRUD所管理的对象类型.
 * 
 */
@SuppressWarnings("serial")
public abstract class CRUDActionSupport<T> extends ActionSupport implements ModelDriven<T>, Preparable {

	/**
	 * 进行增删改操作后,以redirect方式重新打开action默认页的result名.
	 */
	public static final String RELOAD = "reload";
	
	public static final String SUCCESS = "success";
	
	public static final String ERROR = "error";
	
	public static final String INDEX = "index";
	
	public static final String INPUT = "input";
	
	public static final String DETAIL = "detail";
	
	public static final String PERMIS = "permis";
	
	private String permId;
	
	public String getPermId() {
		return permId;
	}

	public void setPermId(String permId) {
		this.permId = permId;
	}

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * Action函数,默认action函数，默认指向list函数.
	 */
	@Override
	public String execute() throws Exception {
		return list();
	}

	// CRUD函数 //

	/**
	 * Action函数,显示Entity列表.
	 * 建议return SUCCESS.
	 */
	public abstract String list() throws Exception;

	/**
	 * Action函数,新增/修改Entity. 
	 * 建议return RELOAD.
	 */
	public abstract String save() throws Exception;
	
	/**
	 * Action函数,删除Entity.
	 * 建议return RELOAD.
	 */
	public abstract String delete() throws Exception;
	
	/**
	 * Action函数,转到表单页面
	 * 建议return INPUT.
	 */
	@Override
	public abstract String input() throws Exception;

	// Preparable函数 //

	/**
	 * 实现空的prepare()函数,屏蔽所有Action函数公共的二次绑定.
	 */
	public void prepare() throws Exception {
		logger.debug("-------prepare-------------");
	}

	/**
	 * 在save()前执行二次绑定.
	 */
	public void prepareSave() throws Exception {
		logger.debug("-------prepareSave---------");
		prepareModel();
	}

	/**
	 * 在input()前执行二次绑定.
	 */
	public void prepareInput() throws Exception {
		logger.debug("-------prepareInput---------");
		prepareModel();
	}

	/**
	 * 等同于prepare()的内部函数,供prepardMethodName()函数调用. 
	 */
	protected abstract void prepareModel() throws Exception;
		
}

