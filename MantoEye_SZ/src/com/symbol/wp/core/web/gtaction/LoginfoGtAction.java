package com.symbol.wp.core.web.gtaction;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;

import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.entity.TbBaseOpLog;
import com.symbol.wp.core.service.impl.BaseOpLogManager;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.PropertyFilter;
import com.symbol.wp.modules.orm.PropertyFilter.MatchType;
import com.symbol.wp.modules.orm.hibernate.HibernateWebUtils;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.model.SortInfo;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

public class LoginfoGtAction extends  BaseDispatchAction{
	
	@Autowired
	private BaseOpLogManager baseOpLogManager;
	
	private Page<TbBaseOpLog> page = new Page<TbBaseOpLog>(
			VarConstants.PAGE_LONG_ROW_SIZE);
	
//	/**
//	 * 默认执行的 action方法
//	 */
//	public void service() throws ServletException, IOException {
//		showListView();
//	}
	
//	/**
//	 * 显示列表页面
//	 */
//	public void showListView() throws ServletException, IOException {
//		String no=Struts2Utils.getRequest().getParameter("no");
//		forward("list"+no);		
//	}
//	
	
	
	/**
	 * 取得所有日志信息(分页)
	 */
	public void getList() throws ServletException, IOException {
		List<TbBaseOpLog> list=null;
		
		// GT-Grid一个很关键的服务端处理器, 可以用来实现很多功能, 以后还会继续扩充和完善.
		GridServerHandler gridServerHandler=new GridServerHandler(Struts2Utils.getRequest(),Struts2Utils.getResponse());
		
		List<PropertyFilter> filters = HibernateWebUtils.buildPropertyFilters(
				Struts2Utils.getRequest());
		//通过用户的登陆名，查询用户昵称
		String userName = Struts2Utils.getRequest().getParameter("s_vcUserName");

		if (userName != null && !"".equals(userName)) {
			String vcLoginNames = baseOpLogManager.getVcLoginName(userName);
			String[] vln = vcLoginNames.split(",");
			filters.add(new PropertyFilter("vcLoginUser", vln,
					MatchType.IN));
		}
		if(page.getOrderBy()==null){
			page.setOrderBy("dtRecordTime");
			page.setOrder("desc");
		}
		
		page = baseOpLogManager.search(page, filters);
		
		// 取得总记录数
		int totalRowNum=gridServerHandler.getTotalRowNum();
		//如果总数不存在, 那么调用"相应的方法" 来取得总数
		if (totalRowNum<1){
			totalRowNum=page.getTotalCount();
			//将取得的总数赋给gridServerHandler
			gridServerHandler.setTotalRowNum(totalRowNum);
		}
		logger.info(totalRowNum+"--totalRowNum");
		
		//调用"相应的方法" 来取得数据.下面4个方法 通常对于进行分页查询很有帮助,根据需要使用即可.
		// gridServerHandler.getStartRowNum() 当前页起始行号
		// gridServerHandler.getEndRowNum() 当前页结束行号
		// gridServerHandler.getPageSize() 每页大小
		// gridServerHandler.setTotalRowNum() 记录总条数
		
		//list=dao.getStudentsByPage(gridServerHandler.getStartRowNum(),gridServerHandler.getPageSize());
		
		list = page.getResult();
		
		// 本例中list里的元素是 map, 
		// 如果元素是bean, 请使用 gridServerHelp.setData(list,BeanClass.class);
//		gridServerHandler.setData(list);
		gridServerHandler.setData(list,TbBaseOpLog.class);
//		gridServerHandler.setException("your exception message");
		
		logger.info(gridServerHandler.getLoadResponseText());
		//向客户端输出json字符串.
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
		
		// 你也可以 使用 gridServerHandler.getLoadResponseText() 来取得字符串.
		// 然后自行向 客户端输出, 这样做的好处是 你可以自己来决定response的contentType和编码.
	}
	
	/**
	 * 取得所有学生信息(分页 & 排序)
	 */
	public void getSortedList() throws ServletException, IOException {
		List<TbBaseOpLog> list=null;
		List<PropertyFilter> filters = HibernateWebUtils.buildPropertyFilters(
				Struts2Utils.getRequest());
		//通过用户的登陆名，查询用户昵称
		String userName = Struts2Utils.getRequest().getParameter("s_vcUserName");

		if (userName != null && !"".equals(userName)) {
			String vcLoginNames = baseOpLogManager.getVcLoginName(userName);
			String[] vln = vcLoginNames.split(",");
			filters.add(new PropertyFilter("vcLoginUser", vln,
					MatchType.IN));
		}
		GridServerHandler gridServerHandler=new GridServerHandler(Struts2Utils.getRequest(),Struts2Utils.getResponse());
		logger.info(gridServerHandler+"**s****");
		SortInfo si = gridServerHandler.getSingleSortInfo();
		if(si!=null){
			page.setOrderBy(si.getFieldName());
			page.setOrder(si.getSortOrder());
		}else{
			page.setOrderBy("dtRecordTime");
			page.setOrder("desc");
		}		
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		
		logger.info(page.getOrderBy()+"--"+page.getOrder()+"--"+page.getPageNo()+"--"+page.getPageSize());
		
		page = baseOpLogManager.search(page, filters);
		
		
		int totalRowNum=gridServerHandler.getTotalRowNum();
		if (totalRowNum<1){
			totalRowNum=page.getTotalCount();
			gridServerHandler.setTotalRowNum(totalRowNum);			
		}
		//list=baseOpLogManager.getSortedPageDate(gridServerHandler.getSingleSortInfo(),gridServerHandler.getStartRowNum(),gridServerHandler.getPageSize());
		list=page.getResult();
		
		gridServerHandler.setData(list,TbBaseOpLog.class);

		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
		
	}
	
	/**
	 * 取得所有学生信息(不分页)
	 */
	public void getAllList() throws ServletException, IOException {
		List<TbBaseOpLog> list=null;
		GridServerHandler gridServerHandler=new GridServerHandler(Struts2Utils.getRequest(),Struts2Utils.getResponse());

		list=baseOpLogManager.getAll();
		
		gridServerHandler.setData(list,TbBaseOpLog.class);
		logger.info(gridServerHandler.getLoadResponseText());
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());

	}
	
//	/**
//	 * 导出所有学生信息
//	 */
//	public void exportAllList() throws ServletException, IOException {
//		StudentsDAO dao=new StudentsDAO();
//		List list=null;
//		GridServerHandler gridServerHandler=new GridServerHandler(request,response);
//
//		list=dao.getAllStudents();
//
//		// 如果记录放在bean中, 那么调用方法  
//		//   gridServerHandler.exportXLS(list,beanClass);
//		// 此例处依然以map做例子
//		gridServerHandler.exportXLSfromMaps(list);
//		
//		
//		/* 开发人员也 可以自己实现生成xls的方法
//		 
//		try {
//		
//			gridServerHandler.downloadFile("abc.xls");
//			OutputStream out=getResponse().getOutputStream();
//			
//			此处写自定义的生成xls文件的方法,但是一定要利用到 out流.
//			gridServerHandler.getColumnInfo/getDisplayColumnInfo ();
//			这两个方法可以用来来取得导出时需要的一些必要的列信息.
//			
//			out.close();
//			
//		}cache(Exception e) {
//		 	// 此处处理一下异常... 
//		 }
//			
//		 */
//
//	}
//
	/**
	 * 导出所有日志信息
	 */
	public void exportList() throws ServletException, IOException {

		List<TbBaseOpLog> list=null;
		GridServerHandler gridServerHandler=new GridServerHandler(Struts2Utils.getRequest(),Struts2Utils.getResponse());

		list=baseOpLogManager.getAll();

		gridServerHandler.exportXLS(list , TbBaseOpLog.class);


	}
	
//	/**
//	 * 增删改对应的action 方法
//	 */
//	public void doSave() throws ServletException, IOException {
//		StudentsDAO dao=new StudentsDAO();
//		boolean success=true;
//		GridServerHandler gridServerHandler=new GridServerHandler(request,response);
//		
//		//取得新增的数据集合, 每条数据记录在 map 里
//		List insertedRecords = gridServerHandler.getInsertedRecords();
//		//取得修改的数据集合, 每条数据记录在 map 里
//		List updatedList = gridServerHandler.getUpdatedRecords();
//		//取得删除的数据集合, 每条数据记录在 map 里
//		List deletedRecords = gridServerHandler.getDeletedRecords();
//
//		// 如果希望取得bean的集合 那么请使用有参方法: xxx.getXXXXXXRecords(Class beanClass);
//		//例如: List updateList = gridServerHandler.getUpdatedRecords(StudentVO.class);
//		
//		//调用"相应的方法" 来update delete insert数据
//		success = dao.saveStudents(insertedRecords , updatedList,  deletedRecords );
//
//		
//		//设置该次操作是否成功.
//		gridServerHandler.setSuccess(success);
//		
//		//如果操作不成功 你也可以自定义一些异常信息发送给客户端.
////		gridServerHandler.setSuccess(false);
////		gridServerHandler.setException("... exception info ...");
//
//		//向客户端输出json字符串.
//		print(gridServerHandler.getSaveResponseText());
//	}

}

