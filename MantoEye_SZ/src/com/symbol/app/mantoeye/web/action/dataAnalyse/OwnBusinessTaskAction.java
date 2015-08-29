package com.symbol.app.mantoeye.web.action.dataAnalyse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.symbol.app.mantoeye.dto.flush.BussAndBussType;
import com.symbol.app.mantoeye.dto.flush.TerminalBusiness;
import com.symbol.app.mantoeye.dto.flush.TerminalTaskEntity;
import com.symbol.app.mantoeye.service.businessAnalyse.BusinessDistributeManager;
import com.symbol.app.mantoeye.service.businessAnalyse.OwnBusinessTaskManager;
import com.symbol.app.mantoeye.service.terminalmanager.TerminalBusinessAnalyseManager;
import com.symbol.app.mantoeye.service.terminalmanager.TerminalManager;
import com.symbol.wp.core.constants.MsgConstants;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.export.excel.ManageExcel;
import com.symbol.wp.tools.gtgrid.export.excel.TableBean;
import com.symbol.wp.tools.gtgrid.export.excel.TableView;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

public class OwnBusinessTaskAction extends BaseDispatchAction {

	@Autowired
	private OwnBusinessTaskManager ownBusinessTaskManager;
	HttpServletRequest request = ServletActionContext.getRequest();
	private Page<TerminalTaskEntity> page = new Page<TerminalTaskEntity>(
			VarConstants.PAGE_LONG_ROW_SIZE, true);
	@Autowired
	@Qualifier("commonManagerImpl")
	private ICommonManager commonManagerImpl;
	
	@Autowired
	private TerminalBusinessAnalyseManager terminalBusinessAnalyseManager;
	
	@Autowired
	private BusinessDistributeManager businessDistributeManager;

	@Autowired
	private TerminalManager baseTerminalManager;
	
	/**
	 * 分页查询任务
	 */
	public void query() {
		String taskName=request.getParameter("taskName");
		String beginTime=request.getParameter("beginTime");
		String endTime=request.getParameter("endTime");
		String status=request.getParameter("taskStatus");
		
		TerminalTaskEntity taskEntity=new TerminalTaskEntity();
		if(taskName!=null&&taskName.trim()!=""){
			taskEntity.setTaskName(taskName.trim());
		}
		
		if(beginTime!=null&&beginTime.trim()!=""){
			taskEntity.setBeginTime(beginTime);
		}
		
		if(endTime!=null&&endTime.trim()!=""){
			taskEntity.setEndTime(endTime);
		}
		
		if(status==null||status.trim()==""){
			status="-1";
		}
		taskEntity.setTaskStatus(status);
		
		 GridServerHandler gridServerHandler = new GridServerHandler(
			      Struts2Utils.getRequest(), Struts2Utils.getResponse());
		 this.page.setPageSize(gridServerHandler.getPageSize());
		 this.page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		 this.page=ownBusinessTaskManager.queryEntity(page, taskEntity);
		 int totalRowNum = gridServerHandler.getTotalRowNum();
		    if (totalRowNum < 1) {
		      totalRowNum = this.page.getTotalCount();
		      gridServerHandler.setTotalRowNum(totalRowNum);
		    }
		 List<TerminalTaskEntity> list = page.getResult();
		 gridServerHandler.setData(list, TerminalTaskEntity.class);
		 Struts2Utils.renderJson(gridServerHandler.getLoadResponseText(), new String[0]);
		 
	}
	

	
	
	/**
	 * 判断该任务是否存在
	 */
	public String checkUnique() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String taskName = request.getParameter("taskName");
		String oldTaskName=request.getParameter("oldTaskName");
		
			try {
				oldTaskName = java.net.URLDecoder
						.decode(oldTaskName, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage());
			}
			
		
		try {
			taskName = new String(taskName.getBytes("ISO-8859-1"),
					"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		if(oldTaskName.equals(taskName)){
			Struts2Utils.renderText("true");
			return null;
		}
			int i=ownBusinessTaskManager.isTaskName(taskName);
			if(i==-1){//-1表示不存在该任务名称
				Struts2Utils.renderText("true");
			}else{
				Struts2Utils.renderText("false");
			}
		return null;
	}
	
	public String  saveTask() throws Exception{
		String taskName=request.getParameter("taskName");
		String beginTime=request.getParameter("beginTime");
		String endTime=request.getParameter("endTime");
		String status=request.getParameter("taskStatus");
		String business=request.getParameter("businessIds");
		
		logger.info(business+"$$$$$$$$$$$$");
		
		String taskDescribe=request.getParameter("taskDescribe");
		TerminalTaskEntity taskEntity=new TerminalTaskEntity();
		taskEntity.setBeginTime(beginTime);
		taskEntity.setEndTime(endTime);
		taskEntity.setTaskDescribe(taskDescribe);
		taskEntity.setTaskName(taskName);
		taskEntity.setListBuss(this.handBusiness(business));
		logger.info(taskEntity.getListBuss()+"************");
		String msg="";
		try{
			
			msg = "添加终端任务成功!";
			ownBusinessTaskManager.insertTask(taskEntity);
			commonManagerImpl.log(request, msg);
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00101);
			return SUCCESS;
		}catch(Exception e){
			logger.error(e.getMessage());
			msg = "添加终端任务失败!";
			commonManagerImpl.log(request, msg);
			Struts2Utils.renderText(msg);
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			return ERROR;
		}
		
	}
	

	
	/**
	 * 分离页面传来的业务ID 和业务类型 ID
	 * @param terminal
	 * @return
	 */
	public Map<String,String>  handBusiness(String business){
		Map<String,String> map=new HashMap<String,String>();
		String[] businesss=business.split(",");
		for(int i=0;i<businesss.length;i++){
			BussAndBussType bEntity=new BussAndBussType();
			String[] str=businesss[i].split(":");
			if(str.length==3){
				String businessId=str[1].toString();
				String businessTypeId=str[0].toString();
				if(map.get(businessTypeId)==null){
					map.put(businessTypeId, businessId);
				}else{
					String businessTypeIds=map.get(businessTypeId)+","+businessId;
					map.put(businessTypeId, businessTypeIds);
				}
			}
		}
		logger.info(map.toString());
		return map;
	}
	
	/**
	 * 删除任务方法
	 */
	public void deleteTask(){
		String taskId=request.getParameter("taskIds");
		String msg="";
		try{
			
			msg = "删除终端任务成功!";
			ownBusinessTaskManager.deleteTask(taskId);
			commonManagerImpl.log(request, msg);
			Struts2Utils.renderText(msg);
		}catch(Exception e){
			logger.error(e.getMessage());
			msg = "删除终端任务失败!";
			commonManagerImpl.log(request, msg);
			Struts2Utils.renderText(msg);
		}
	}
	
	/**
	 * 导出
	 */
	public void export() throws ServletException, IOException {
		String taskId=request.getParameter("taskId");
		String taskName=request.getParameter("taskName");
		taskName=new String(taskName.getBytes("ISO-8859-1"),
		"UTF-8");
		if(taskId!=null){
			List<TerminalBusiness> list=terminalBusinessAnalyseManager.query(Common.StringToInt(taskId));
			/*
			 * 导出excel
			 */
			TableView tableView = null;
			TableBean tableBean = null;
			
			ManageExcel manageExcel = new ManageExcel();
			manageExcel.excelTitle =taskName+ " 分析结果";
			
			manageExcel.beanName = "com.symbol.wp.tools.gtgrid.export.excel.TableBean";
			manageExcel.viewName = "com.symbol.wp.tools.gtgrid.export.excel.TableView";
			/*
			 * 导出excel需要多少字段就写几个'getElementX' 如果TableBean里面的不够 可以再添加
			 */
			manageExcel.beanSetAndGetMehtodName = "getElement1,getElement2,getElement3,getElement4,getElement5,getElement6,getElement7,getElement8";
				
			manageExcel.viewList.clear();
			tableView = new TableView();
			tableView.setTopic(manageExcel.excelTitle);
			tableView.getBeanList().clear();

			String titles = "业务类型,业务,终端品牌,终端型号,用户数,流量(MB),用户数占比(%),流量占比(%)";
			tableView.setTitles(titles);
			tableView.getBeanList().clear();
			try{
				for(int j=0;j<list.size();j++){				  				
					TerminalBusiness ts = list.get(j);
					
					tableBean = new TableBean(); 
//					tableBean.setElement1(ts.getBusinessTypeName());
//					tableBean.setElement2(ts.getBusinessTypeName());
//					tableBean.setElement3(ts.getBusinessTypeName());
//					tableBean.setElement4(ts.getBusinessTypeName());
//					tableBean.setElement5(ts.getBusinessTypeName());
//					tableBean.setElement6(ts.getBusinessTypeName());
//					tableBean.setElement7(ts.getBusinessTypeName());
//					tableBean.setElement8(ts.getBusinessTypeName());
					Class c = tableBean.getClass(); 
					Method method = c.getMethod("setElement1",new Class[]{String.class});			
					method.invoke(tableBean, new Object[]{ts.getBusinessTypeName()});
					
					method = c.getMethod("setElement2",new Class[]{String.class});			
					method.invoke(tableBean, new Object[]{ts.getBusinessName()});
					
					method = c.getMethod("setElement3",new Class[]{String.class});			
					method.invoke(tableBean, new Object[]{ts.getTerminalBrandName()});
					
					method = c.getMethod("setElement4",new Class[]{String.class});			
					method.invoke(tableBean, new Object[]{ts.getTerminalTypeName()});
					
					method = c.getMethod("setElement5",new Class[]{String.class});			
					method.invoke(tableBean, new Object[]{ts.getIntUsers()+""});
					
					method = c.getMethod("setElement6",new Class[]{String.class});			
					method.invoke(tableBean, new Object[]{ts.getIntFlushMB()+""});
					
					method = c.getMethod("setElement7",new Class[]{String.class});			
					method.invoke(tableBean, new Object[]{getFlushRate(Common.StringTodouble(ts.getFlUsersRate()))});
					
					method = c.getMethod("setElement8",new Class[]{String.class});			
					method.invoke(tableBean, new Object[]{getFlushRate(Common.StringTodouble(ts.getFlFlustRate()))});
					
					tableView.getBeanList().add(tableBean);
				}
				manageExcel.viewList.add(tableView);
				manageExcel.exportstatExcel(this.getServletRequest(), this.getServletResponse());			
			}catch(Exception e){
				logger.error(e.getMessage());
			}
			
		}
	}
	
	public String getFlushRate(Double d){
		d=d*100;
		DecimalFormat df = new DecimalFormat("0.00"); 
		String rate=df.format(d);
		return rate;
		
	}
	public String edit() throws Exception{
		String id=request.getParameter("id");
		TerminalTaskEntity taskEntity=ownBusinessTaskManager.queryTaskByTaskId(Common.StringToInt(id));
		String business=businessDistributeManager.queryBussIdAndBussIdTypeByTaskId(Common.StringToInt(id));
		String[] businesses=business.split("@");
		taskEntity.setBisAndBisTypeId(businesses[0]);
		taskEntity.setBusinessNames(businesses[1]);
		request.setAttribute("taskEntity", taskEntity);
		return "edit";
	}
	public String show() throws Exception{
		String id=request.getParameter("id");
		TerminalTaskEntity taskEntity=ownBusinessTaskManager.queryTaskByTaskId(Common.StringToInt(id));
		String business=businessDistributeManager.queryBussIdAndBussIdTypeByTaskId(Common.StringToInt(id));
		String[] businesses=business.split("@");
		taskEntity.setBisAndBisTypeId(businesses[0]);
		taskEntity.setBusinessNames(businesses[1]);
		request.setAttribute("taskEntity", taskEntity);
		return DETAIL;
	}
	
	/**
	 * 停止任务
	 */
	public void stopTask() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String taskId = request.getParameter("taskId");
		String msg = "";
		try {
			TerminalTaskEntity entity = ownBusinessTaskManager.get(Common.StringToInt(taskId));
			entity.setIntTaskStatus(4);// 停止中
			ownBusinessTaskManager.save(entity);
			msg = "停止任务[：" + taskId + "]" + "成功!";
			commonManagerImpl.log(request, msg);
		} catch (Exception e) { // 删除失败
			msg = "停止任务[：" + taskId + "]" + "失败!";
			commonManagerImpl.log(request, msg);
		}
		Struts2Utils.renderText(msg);
	}
	
	/**
	 * 跟新终端任务
	 * @return
	 * @throws Exception
	 */
	public String updateTask() throws Exception{
		String taskName=request.getParameter("taskName");
		String beginTime=request.getParameter("beginTime");
		String endTime=request.getParameter("endTime");
		String status=request.getParameter("taskStatus");
		String business=request.getParameter("businessIds");
		String taskDescribe=request.getParameter("taskDescribe");
		String intTaskStatus=request.getParameter("intTaskStatus");
		String id=request.getParameter("nmTerminalPolicyId");
		TerminalTaskEntity taskEntity=new TerminalTaskEntity();
		taskEntity.setBeginTime(beginTime);
		taskEntity.setEndTime(endTime);
		taskEntity.setTaskDescribe(taskDescribe);
		taskEntity.setTaskName(taskName);
		logger.info("****Busmap:"+business);
		taskEntity.setListBuss(this.handBusiness(business));
		logger.info("Termap:"+taskEntity.getListTerminal());
		logger.info("Busmap:"+taskEntity.getListBuss());
		taskEntity.setIntTaskStatus(Common.StringToInt(intTaskStatus));
		taskEntity.setNmTerminalPolicyId(Common.StringToInt(id));
		String msg="";
		try{
			
			msg = "更新终端任务"+taskName+"成功!";
			ownBusinessTaskManager.insertTask(taskEntity);
			commonManagerImpl.log(request, msg);
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00102);
			return SUCCESS;
		}catch(Exception e){
			logger.error(e.getMessage());
			msg = "更新终端任务"+taskName+"失败!";
			commonManagerImpl.log(request, msg);
			Struts2Utils.renderText(msg);
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			return ERROR;
		}
	}
	
	
}
