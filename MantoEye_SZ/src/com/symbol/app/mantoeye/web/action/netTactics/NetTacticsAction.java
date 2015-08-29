package com.symbol.app.mantoeye.web.action.netTactics;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.stereotype.Component;
import com.symbol.app.mantoeye.dto.keywordsTactics.KeywordsDetail;
import com.symbol.app.mantoeye.dto.keywordsTactics.KeywordsTactics;
import com.symbol.app.mantoeye.dto.netTactics.DialectNetDTO;
import com.symbol.app.mantoeye.dto.netTactics.NetTacticsDTO;
import com.symbol.app.mantoeye.service.keywordsTactics.KeywordsTacticsImpl;
import com.symbol.app.mantoeye.service.netTactics.NetTacticsImpl;
import com.symbol.app.mantoeye.util.MantoEyeUtils;
import com.symbol.app.mantoeye.web.action.Phone;
import com.symbol.wp.core.constants.MsgConstants;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.entity.TbBaseUserInfo;
import com.symbol.wp.core.listener.LoginListener;
import com.symbol.wp.core.util.ReqUtils;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.model.SortInfo;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

@Component
@Namespace("/modules/netTactics")
@Results( {
		@Result(name = "edit", location = "/mantoeye/netTactics/netTactics_edit_1.jsp"),
		@Result(name = "success", location = "/include/success.jsp"),
		@Result(name = "error", location = "/include/error.jsp")
})
public class NetTacticsAction extends BaseDispatchAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1238545243034326833L;
	private NetTacticsImpl netTacticsImpl;
	private KeywordsTacticsImpl keywordsTacticsImpl;
	private KeywordsTactics entity;
	private Long id;
	private String businessIds;
	private String business;
	private int readOnly;
	private String startTime;
	private String endTime;
	private Page<KeywordsDetail> page = new Page<KeywordsDetail>(VarConstants.PAGE_LONG_ROW_SIZE);
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBusinessIds() {
		return businessIds;
	}

	public void setBusinessIds(String businessIds) {
		this.businessIds = businessIds;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public int getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(int readOnly) {
		this.readOnly = readOnly;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public KeywordsTactics getEntity() {
		return entity;
	}

	public void setEntity(KeywordsTactics entity) {
		this.entity = entity;
	}

	public NetTacticsImpl getNetTacticsImpl() {
		return netTacticsImpl;
	}

	@Resource(name = "netTacticsImpl")
	public void setNetTacticsImpl(NetTacticsImpl netTacticsImpl) {
		this.netTacticsImpl = netTacticsImpl;
	}
	
	public KeywordsTacticsImpl getKeywordsTacticsImpl() {
		return keywordsTacticsImpl;
	}

	@Resource(name = "keywordsTacticsImpl")
	public void setKeywordsTacticsImpl(KeywordsTacticsImpl keywordsTacticsImpl) {
		this.keywordsTacticsImpl = keywordsTacticsImpl;
	}

	/** 查询策略任务表 */
	public void query() {
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		List<NetTacticsDTO> list = netTacticsImpl.queryTacticsList();
		List<JSONObject> jsonList = new ArrayList<JSONObject>();
		for (NetTacticsDTO dto : list) {
			jsonList.add(JSONObject.fromObject(dto));
		}
		gridServerHandler.setData(jsonList);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseJSON()
				.toString());
	}
	
	/** 查询详细信息 */
	public void showDialect() {
		HttpServletRequest request = ServletActionContext.getRequest();
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		String nmDataGetterTaskId = ReqUtils.getReqString(request, "id");
		List<DialectNetDTO> list = netTacticsImpl.showDialect(nmDataGetterTaskId);
		List<JSONObject> jsonList = new ArrayList<JSONObject>();
		for (DialectNetDTO dto : list) {
			jsonList.add(JSONObject.fromObject(dto));
		}
		gridServerHandler.setData(jsonList);

		Struts2Utils.renderJson(gridServerHandler.getLoadResponseJSON()
				.toString());
	}

	/**
	 * 详细信息
	 * 
	 * @throws IOException
	 */
	public void getShowMessage() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		String nmDataGetterTaskId = ReqUtils.getReqString(request, "id");
		List<NetTacticsDTO> lst = netTacticsImpl.getShowMessage(nmDataGetterTaskId);
		JSONArray array = JSONArray.fromObject(lst.get(0));
		out.write(array.toString());
	}

	/** 新建策略 */
	public void inputTactics() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		ServletActionContext.getRequest().setCharacterEncoding("UTF-8");
		String vcTaskName = ReqUtils.getReqString(request, "vcTaskName");
		// vcTaskName = new String(vcTaskName.getBytes("ISO-8859-1"),"UTF-8");
		vcTaskName = new String(vcTaskName.getBytes("ISO-8859-1"), "GBK");
		String vcTaskOrder = (String) ServletActionContext.getContext()
				.getSession().get("baseUserId");
		String dtOrderTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.s")
				.format(new Date());
		String dtStartTime = ReqUtils.getReqString(request, "dtStartTime");
		String dtEndTime = ReqUtils.getReqString(request, "dtEndTime");
		String intStartHour = ReqUtils.getReqString(request, "intStartHour");
		String intEndHour = ReqUtils.getReqString(request, "intEndHour");
		String vcContents = ReqUtils.getReqString(request, "vcContents");
		// vcContents = new String(vcContents.getBytes("ISO-8859-1"),"UTF-8");
		vcContents = new String(vcContents.getBytes("ISO-8859-1"), "GBK");
		String productArray = ReqUtils.getReqString(request, "product");
		List<NetTacticsDTO> list = netTacticsImpl.getTreeId(productArray);
		
		netTacticsImpl.insertTactics(vcTaskName, dtOrderTime, vcTaskOrder,
				dtStartTime, dtEndTime, intStartHour, intEndHour, vcContents);

		String nmDataGetterTaskId = netTacticsImpl.getTaskId(dtOrderTime);
		
		for(NetTacticsDTO n:list){
			String nmTreeId = n.getTreeId();
			//netTacticsImpl.insertNetContentGetterFilter(nmDataGetterTaskId, nmTreeId);
		}
		
		out.write("1");
	}

	/** 编辑策略 */
	public void updateTactics() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		ServletActionContext.getRequest().setCharacterEncoding("UTF-8");
		String vcTaskOrder = (String) ServletActionContext.getContext()
				.getSession().get("baseUserId");
		String dtStartTime = ReqUtils.getReqString(request, "dtStartTime");
		String dtEndTime = ReqUtils.getReqString(request, "dtEndTime");
		String intStartHour = ReqUtils.getReqString(request, "intStartHour");
		String intEndHour = ReqUtils.getReqString(request, "intEndHour");
		String vcContents = ReqUtils.getReqString(request, "vcContents");
		// vcContents = new String(vcContents.getBytes("ISO-8859-1"),"UTF-8");
		vcContents = new String(vcContents.getBytes("ISO-8859-1"), "GBK");
		String nmDataGetterTaskId = ReqUtils.getReqString(request, "id");
		String productArray = ReqUtils.getReqString(request, "product");
		List<NetTacticsDTO> list = netTacticsImpl.getTreeId(productArray);
		netTacticsImpl.deleteNetContentGetterFilter(nmDataGetterTaskId);
		netTacticsImpl.updateTactics(nmDataGetterTaskId, vcTaskOrder,
				dtStartTime, dtEndTime, intStartHour, intEndHour, vcContents);
		for(NetTacticsDTO n:list){
			String nmTreeId = n.getTreeId();
			//netTacticsImpl.insertNetContentGetterFilter(nmDataGetterTaskId, nmTreeId);
		}
		out.write("1");
	}

	/** 停止策略 */
	public void stopTactics() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String msg = "";
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		ServletActionContext.getRequest().setCharacterEncoding("UTF-8");
		String nmDataGetterTaskId = ReqUtils.getReqString(request, "ids");
		netTacticsImpl.stopTatctics(nmDataGetterTaskId);
		msg = "停止策略成功!";
		Struts2Utils.renderText(msg);
	}

	/** 删除策略 */
	public void deleteTactics() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String msg = "";
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		ServletActionContext.getRequest().setCharacterEncoding("UTF-8");
		String nmDataGetterTaskId = ReqUtils.getReqString(request, "ids");
		netTacticsImpl.deleteTactics(nmDataGetterTaskId);
		msg = "删除策略成功!";
		Struts2Utils.renderText(msg);
	}
	
	/**导出*/
	public void exportList() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
//		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
//		ServletActionContext.getRequest().setCharacterEncoding("UTF-8");
		String timebegin = ReqUtils.getReqString(request, "timebegin");
		String timeend = ReqUtils.getReqString(request, "timeend");
		String headerstr = request.getParameter("strateName");
		String nmDataGetterTaskId = ReqUtils.getReqString(request, "id");
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName = new String(request.getParameter("exportFileName").getBytes("ISO-8859-1"),"GBK")+"_"+format.format(new Date());

		String[] headers = new String(headerstr.getBytes("ISO-8859-1"),"UTF-8").split(",");
		String[] propertiesName = request.getParameter("field").split(",");
		
		String startDate = timebegin.substring(0, 10);
		String endDate = timeend.substring(0, 10);
		String startHour = timebegin.substring(11, 13);
		String endHour = timeend.substring(11, 13);
		List<DialectNetDTO> lst = netTacticsImpl.exportDialect(nmDataGetterTaskId, startDate, endDate, startHour, endHour);
		if(lst == null || lst.size()==0) return;
		List list = new ArrayList();
		for (DialectNetDTO bt : lst) {
			Object[] os = new Object[4];
			os[0]=bt.getNmMsisdn();
			os[1]=bt.getVcGroupName();
			os[2]=bt.getVcParentGroupName();
			os[3]=bt.getUserTime();
			list.add(os);
		}
//		String[] title = new String[]{"终端型号","流量占比(%)","用户总流量(MB)","访问总次数(次)","用户总数(人)"};
		Map map = new  HashMap<String,String>();
		map.put("exportFileName", new String[]{fileName});
		GridServerHandler gridServerHandler = new GridServerHandler();
		gridServerHandler.setRequest(request);
		gridServerHandler.setResponse(ServletActionContext.getResponse());
		gridServerHandler.setParameterMap(map);
		gridServerHandler.setEncoding("UTF-8");
		gridServerHandler.setEncodeFileName(true);
//		gridServerHandler.exportCSV(list, title);
		gridServerHandler.exportXLS(lst, propertiesName,headers,DialectNetDTO.class);
	}
	
	
	
	
	
	
	
	
	
	
	public void initBussnessDialogData() throws Exception {
		Struts2Utils.renderXml(packBussnessInfo());
	}
	
	/**
	 * 组装业务信息
	 */
	public String packBussnessInfo() {
		List<Phone> list = new ArrayList<Phone>();
		Map<String, Map<String, String>> map = netTacticsImpl
				.queryBussAndBussType();
		List<String> brandList = new ArrayList<String>(map.keySet());
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		xml = xml + "<root>";
		if (brandList != null && !brandList.isEmpty()) {
			for (int i = 0; i < brandList.size(); i++) {
				String brandAnadId = brandList.get(i);
				Map<String, String> mapType = map.get(brandAnadId);
				String[] str = brandAnadId.split(":");// 分离品牌名称和Id

				Set setType = mapType.keySet();
				Collection con = mapType.values();
				String modle = null;
				String typeIds = null;
				if (setType != null) {
//					modle = setType.toString().replace("[", "")
//							.replace("]", "");
//					typeIds = con.toString().replace("[", "").replace("]", "");// 型号IDs
					modle = getMapKeyStr(mapType);
					typeIds = getMapValueStr(mapType);
				}

				xml = xml + "<data>" + "<brandId>" + str[1] + "</brandId>"
						+ "<brandName>" + str[0] + "</brandName>" + "<modelId>"
						+ typeIds + "</modelId>" + "<modelName>" + modle
						+ "</modelName>" + "</data>";
			}
		}
		xml = xml + "</root>";
		// logger.info("----------------------------------------------------------------");
		// logger.info(xml);
		// logger.info("----------------------------------------------------------------");
		return xml;
	}
	
	
	
	private String getMapKeyStr(Map<String, String> map) {
		Set<String> setType = map.keySet();
		String str = "";
		if (setType != null) {
			for(Iterator it=setType.iterator();it.hasNext();){
				str = str+","+it.next();
			}
			if(str.indexOf(",")!=-1){
				str = str.substring(1);
			}		
		}
		return str;
	}

	private String getMapValueStr(Map<String, String> map) {
		Set<String> setType = map.keySet();
		String str = "";
		if (setType != null) {
			for(Iterator<String> it=setType.iterator();it.hasNext();){
				str = str+","+map.get(it.next());
			}
			if(str.indexOf(",")!=-1){
				str = str.substring(1);
			}		
		}
		return str;
	}
	
	
	
	public String save() throws ServletException, IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		LoginListener loginListener = (LoginListener) request.getSession()
				.getAttribute(VarConstants.LOGIN_LISTENER_KEY);
		TbBaseUserInfo userInfo = loginListener.getSessionContainer().getUserInfo();
		String businessIds = request.getParameter("businessIds");
		String business = request.getParameter("business");
		String msg = null;
		String[] bussIds = null;
		String businessId = null;
		try {
				request.setAttribute(VarConstants.SUCC_CODE, MsgConstants.SUCC_CODE_00101);
				entity.setIntTaskStatus(0);// 新建时的状态
				entity.setDtOrderTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.s").format(new Date()));// 新建时的定制时间
				entity.setVcTaskOrder(userInfo.getVcUserName());
				entity.setBitTaskActiveFlag(1);
				entity.setIntTaskType(2);
				keywordsTacticsImpl.save(entity) ;
				long nmDataGetterTaskId = keywordsTacticsImpl.getTaskId(entity.getDtOrderTime());
				
				if (businessIds != null && !"".equals(businessIds)) {
					String[] datas = businessIds.split(",");
					String bussId = "";
					for (String ids : datas) {
						bussIds = ids.split(":");
						businessId = bussIds[1].trim();
						netTacticsImpl.insertNetContentGetterFilter(nmDataGetterTaskId, new Long(businessId));
					}
				} 
			msg = "添加策略任务[名称：" + entity.getVcTaskName() + "]成功!";
			//commonManagerImpl.log(request, msg);
			return "success";
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error(e.toString());
			logger.error(e.getMessage());
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			msg = "添加策略任务[名称：" + entity.getVcTaskName() + "]失败!";
			//commonManagerImpl.log(request, msg);
			return "error";
		}
	}
	
	
	
	
	public String update() throws ServletException, IOException {
		HttpServletRequest request = Struts2Utils.getRequest();
		LoginListener loginListener = (LoginListener) request.getSession()
				.getAttribute(VarConstants.LOGIN_LISTENER_KEY);
		TbBaseUserInfo userInfo = loginListener.getSessionContainer().getUserInfo();
		String businessIds = request.getParameter("businessIds");
		String business = request.getParameter("business");
		String msg = null;
		String[] bussIds = null;
		String businessId = null;
		try {
				request.setAttribute(VarConstants.SUCC_CODE, MsgConstants.SUCC_CODE_00102);
				entity.setIntTaskStatus(0);// 新建时的状态
				entity.setDtOrderTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.s").format(new Date()));// 新建时的定制时间
				entity.setVcTaskOrder(userInfo.getVcUserName());
				keywordsTacticsImpl.update(entity) ;
				netTacticsImpl.deleteNetContentGetterFilter(entity.getNmDataGetterTaskId());
				if (businessIds != null && !"".equals(businessIds)) {
					String[] datas = businessIds.split(",");
					String bussId = "";
					for (String ids : datas) {
						bussIds = ids.split(":");
						businessId = bussIds[1].trim();
						netTacticsImpl.insertNetContentGetterFilter(entity.getNmDataGetterTaskId(), new Long(businessId));
					}
				} 
			msg = "编辑策略任务[名称：" + entity.getVcTaskName() + "]成功!";
			//commonManagerImpl.log(request, msg);
			return "success";
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error(e.toString());
			logger.error(e.getMessage());
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			msg = "编辑策略任务[名称：" + entity.getVcTaskName() + "]失败!";
			//commonManagerImpl.log(request, msg);
			return "success";
		}
	}
	
	public String edit(){
		entity = keywordsTacticsImpl.findTaskById(id);
		String dtStartTime = entity.getDtStartTime();
		String dtEndTime = entity.getDtEndTime();
		entity.setDtStartTime(dtStartTime.substring(0,10));
		entity.setDtEndTime(dtEndTime.substring(0,10));
		List<Object> kList = netTacticsImpl.findKeyById(id);
		if (kList!=null && kList.size()>0) {
			businessIds="";
			business = "";
			for (int i=0;i<kList.size();i++) {
				Object[] obj = (Object[]) kList.get(i);
				businessIds =businessIds +obj[2]+":"+obj[0]+",";
				business =business + obj[1]+",";
			}
			businessIds = businessIds.substring(0,businessIds.length()-1);
			business = business.substring(0,business.length()-1);

		}
		return "edit";
	}
	
	
	public void queryKey() throws ServletException, IOException {
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		SortInfo si = gridServerHandler.getSingleSortInfo();
		if (si != null) {
			String order = si.getSortOrder();
			if ("defaultsort".equals(order)) {
				page.setOrder("desc");
				page.setOrderBy("fullDate");
			} else {
				page.setOrder(order);
				page.setOrderBy(MantoEyeUtils.getSortField(si.getFieldName()));
			}
			// 默认排序方式
		} else {
			page.setOrderBy("fullDate");
			page.setOrder("desc");
		}

		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		page = netTacticsImpl.queryKey(page,id,startTime, endTime);
		
		int totalRowNum = gridServerHandler.getTotalRowNum();
		if (totalRowNum < 1) {
			totalRowNum = page.getTotalCount();
			gridServerHandler.setTotalRowNum(totalRowNum);
		}
		gridServerHandler.setData(page.getResult(), KeywordsDetail.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}
	
	/**
	 * 数据导出
	 */
	public void exportKey() throws ServletException, IOException {

		List<KeywordsDetail> list = netTacticsImpl.listData(id,startTime, endTime);
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		String exportmsg = "策略结果列表（"+startTime+"~"+endTime+"）";
		//commonManagerImpl.log(this.getServletRequest(), exportmsg);
		if(list!=null && list.size()>0){
			gridServerHandler.exportCSV(list, KeywordsDetail.class);
		}
	}
}
