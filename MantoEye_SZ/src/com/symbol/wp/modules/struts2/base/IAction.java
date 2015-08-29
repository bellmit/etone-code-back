package com.symbol.wp.modules.struts2.base;

public interface IAction {
	
	// CRUD函数 //

	/**
	 * Action函数,显示Entity列表.
	 * 建议return SUCCESS.
	 */
	public  String list() throws Exception;

	/**
	 * Action函数,新增Entity. 
	 * 建议return RELOAD.
	 */
	public  String save() throws Exception;
	
	/**
	 * Action函数,修改Entity. 
	 * 建议return RELOAD.
	 */
	public  String update() throws Exception;
	
	/**
	 * Action函数,删除Entity.
	 * 建议return RELOAD.
	 */
	public  String delete() throws Exception;
	
	/**
	 * Action函数,转到添加表单页面
	 * 
	 */
	public  String input() throws Exception;
	
	/**
	 * Action函数,转到编辑表单页面
	 *
	 */
	public  String edit() throws Exception;


}
