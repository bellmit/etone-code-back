package com.symbol.app.mantoeye.web.action.keywordsTactics;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.symbol.app.mantoeye.dto.keywordsTactics.DialectKeywordsDTO;
import com.symbol.app.mantoeye.dto.keywordsTactics.FtbKeyValueGetterFilter;
import com.symbol.app.mantoeye.dto.keywordsTactics.KeywordsDetail;
import com.symbol.app.mantoeye.dto.keywordsTactics.KeywordsTactics;
import com.symbol.app.mantoeye.dto.keywordsTactics.KeywordsTacticsDTO;
import com.symbol.app.mantoeye.service.keywordsTactics.KeywordsTacticsImpl;
import com.symbol.app.mantoeye.util.MantoEyeUtils;
import com.symbol.app.mantoeye.web.action.Phone;
import com.symbol.wp.core.constants.MsgConstants;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.entity.TbBaseUserInfo;
import com.symbol.wp.core.listener.LoginListener;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.core.util.ReqUtils;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.model.SortInfo;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

import examples.newsgroups;

@Component
@Namespace("/modules/keywordsTactics")
@Results( {
		@Result(name = "edit", location = "/mantoeye/keywordsTactics/keywordsTactics_edit_1.jsp"),
		@Result(name = "success", location = "/include/success.jsp"),
		@Result(name = "error", location = "/include/error.jsp")
})
public class KeywordsTacticsAction extends BaseDispatchAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4480819984949550825L;
	private KeywordsTacticsImpl keywordsTacticsImpl;
	private KeywordsTactics entity;
	private String vcFilterKeyValue1;
	private String vcFilterKeyValue2;
	private Long id;
	private String businessIds;
	private String business;
	private int readOnly;
	private String startTime;
	private String endTime;
	
	


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


	private Page<KeywordsDetail> page = new Page<KeywordsDetail>(VarConstants.PAGE_LONG_ROW_SIZE);
	public int getReadOnly() {
		return readOnly;
	}


	public void setReadOnly(int readOnly) {
		this.readOnly = readOnly;
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


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}
	


	public String getVcFilterKeyValue1() {
		return vcFilterKeyValue1;
	}

	public void setVcFilterKeyValue1(String vcFilterKeyValue1) {
		this.vcFilterKeyValue1 = vcFilterKeyValue1;
	}

	public String getVcFilterKeyValue2() {
		return vcFilterKeyValue2;
	}

	public void setVcFilterKeyValue2(String vcFilterKeyValue2) {
		this.vcFilterKeyValue2 = vcFilterKeyValue2;
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
		List<KeywordsTacticsDTO> list = keywordsTacticsImpl.queryTacticsList();
		List<JSONObject> jsonList = new ArrayList<JSONObject>();
		for (KeywordsTacticsDTO dto : list) {
			jsonList.add(JSONObject.fromObject(dto));
		}
		// JSONArray jArray = JSONArray.fromObject(list);
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
		List<DialectKeywordsDTO> list = keywordsTacticsImpl.showDialect(nmDataGetterTaskId);
		List<JSONObject> jsonList = new ArrayList<JSONObject>();
		for (DialectKeywordsDTO dto : list) {
			jsonList.add(JSONObject.fromObject(dto));
		}
		gridServerHandler.setData(jsonList);

		Struts2Utils.renderJson(gridServerHandler.getLoadResponseJSON()
				.toString());
	}


	/**
	 * 获取策略名
	 * 
	 * @throws IOException
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public void getTaskName() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		String nmDataGetterTaskId = ReqUtils.getReqString(request, "id");
		List<KeywordsTacticsDTO> lst = keywordsTacticsImpl
				.getShowMessage(nmDataGetterTaskId);
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
//		vcContents = new String(vcContents.getBytes("ISO-8859-1"),"UTF-8");
		vcContents = new String(vcContents.getBytes("ISO-8859-1"), "GBK");
		keywordsTacticsImpl.insertTactics(vcTaskName, dtOrderTime, vcTaskOrder,
				dtStartTime, dtEndTime, intStartHour, intEndHour, vcContents);

		String nmDataGetterTaskId = "";//keywordsTacticsImpl.getTaskId(dtOrderTime);
		String productArray = ReqUtils.getReqString(request, "product");
		String[] nmSoTypeIds = productArray.split(",");
		String isExactMarkValuearray = ReqUtils.getReqString(request, "iskey");
		// isExactMarkValuearray = new
		// String(isExactMarkValuearray.getBytes("ISO-8859-1"),"UTF-8");
		isExactMarkValuearray = new String(
				isExactMarkValuearray.getBytes("ISO-8859-1"), "GBK");
		if (isExactMarkValuearray != "" || isExactMarkValuearray != null) {
			String[] isExactMarkValues = isExactMarkValuearray.split(",");
			for (int i = 0; i < nmSoTypeIds.length; i++) {
				String nmSoTypeId = nmSoTypeIds[i];
				for (int j = 0; j < isExactMarkValues.length; j++) {
					String isExactMarkValue = isExactMarkValues[j];
					//keywordsTacticsImpl.insertIsExactMark(nmDataGetterTaskId,nmSoTypeId, isExactMarkValue);
				}
			}
		}
		String unExactMarkValuearray = ReqUtils.getReqString(request, "unkey");
		// unExactMarkValuearray = new
		// String(unExactMarkValuearray.getBytes("ISO-8859-1"),"UTF-8");
		unExactMarkValuearray = new String(
				unExactMarkValuearray.getBytes("ISO-8859-1"), "GBK");
		if (unExactMarkValuearray != "" || unExactMarkValuearray != null) {
			String[] unExactMarkValues = unExactMarkValuearray.split(",");
			for (int i = 0; i < nmSoTypeIds.length; i++) {
				String nmSoTypeId = nmSoTypeIds[i];
				for (int j = 0; j < unExactMarkValues.length; j++) {
					String unExactMarkValue = unExactMarkValues[j];
					//keywordsTacticsImpl.insertUnExactMark(nmDataGetterTaskId, nmSoTypeId, unExactMarkValue);
				}
			}
		}
		out.write("1");
	}

	/** 编辑策略 */
	public void updateTactics() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		String vcTaskOrder = (String) ServletActionContext.getContext()
				.getSession().get("baseUserId");
		String dtOrderTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.s")
				.format(new Date());
		String dtStartTime = ReqUtils.getReqString(request, "dtStartTime");
		String dtEndTime = ReqUtils.getReqString(request, "dtEndTime");
		String intStartHour = ReqUtils.getReqString(request, "intStartHour");
		String intEndHour = ReqUtils.getReqString(request, "intEndHour");
		String vcContents = ReqUtils.getReqString(request, "vcContents");
//		vcContents = new String(vcContents.getBytes("ISO-8859-1"),"UTF-8");
		vcContents = new String(vcContents.getBytes("ISO-8859-1"), "GBK");
		String nmDataGetterTaskId = ReqUtils.getReqString(request, "id");
		String productArray = ReqUtils.getReqString(request, "product");
		keywordsTacticsImpl.deleteKeyValueGetterFilter(nmDataGetterTaskId);
		keywordsTacticsImpl.updateTactics(nmDataGetterTaskId, vcTaskOrder,
				dtStartTime, dtEndTime, intStartHour, intEndHour, vcContents);
		String[] nmSoTypeIds = productArray.split(",");
		String isExactMarkValuearray = ReqUtils.getReqString(request, "iskey");
		// isExactMarkValuearray = new
		// String(isExactMarkValuearray.getBytes("ISO-8859-1"),"UTF-8");
		isExactMarkValuearray = new String(
				isExactMarkValuearray.getBytes("ISO-8859-1"), "GBK");
		if (isExactMarkValuearray != "" || isExactMarkValuearray != null) {
			String[] isExactMarkValues = isExactMarkValuearray.split(",");
			for (int i = 0; i < nmSoTypeIds.length; i++) {
				String nmSoTypeId = nmSoTypeIds[i];
				for (int j = 0; j < isExactMarkValues.length; j++) {
					String isExactMarkValue = isExactMarkValues[j];
					//keywordsTacticsImpl.insertIsExactMark(nmDataGetterTaskId, nmSoTypeId, isExactMarkValue);
				}
			}
		}
		String unExactMarkValuearray = ReqUtils.getReqString(request, "unkey");
		// unExactMarkValuearray = new
		// String(unExactMarkValuearray.getBytes("ISO-8859-1"),"UTF-8");
		unExactMarkValuearray = new String(
				unExactMarkValuearray.getBytes("ISO-8859-1"), "GBK");
		if (unExactMarkValuearray != "" || unExactMarkValuearray != null) {
			String[] unExactMarkValues = unExactMarkValuearray.split(",");
			for (int i = 0; i < nmSoTypeIds.length; i++) {
				String nmSoTypeId = nmSoTypeIds[i];
				for (int j = 0; j < unExactMarkValues.length; j++) {
					String unExactMarkValue = unExactMarkValues[j];
					keywordsTacticsImpl.insertUnExactMark(nmDataGetterTaskId,
							nmSoTypeId, unExactMarkValue);
				}
			}
		}
		out.write("1");
	}

	/** 停止策略 */
	public void stopTactics() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String msg = "";
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		ServletActionContext.getRequest().setCharacterEncoding("UTF-8");
		int nmDataGetterTaskId = ReqUtils.getReqInt(request, "ids");
		keywordsTacticsImpl.stopTatctics(nmDataGetterTaskId);
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
		List dlist = keywordsTacticsImpl.findDataGetterFilterId(nmDataGetterTaskId);
		if (dlist!=null && dlist.size()>0) {
			String nmDataGetterFilterId = "";
			for (int i = 0; i < dlist.size(); i++) {
				nmDataGetterFilterId = nmDataGetterFilterId+dlist.get(i).toString()+",";
			}
			nmDataGetterFilterId = nmDataGetterFilterId.substring(0,nmDataGetterFilterId.length()-1);
			keywordsTacticsImpl.deleteKeyValueSoTypeIdFilter(nmDataGetterFilterId);
		}
		keywordsTacticsImpl.deleteTactics(nmDataGetterTaskId);
		msg = "删除策略成功!";
		Struts2Utils.renderText(msg);
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
		page = keywordsTacticsImpl.queryKey(page,id,startTime, endTime);
		
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

		List<KeywordsDetail> list = keywordsTacticsImpl.listData(id,startTime, endTime);
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		String exportmsg = "策略结果列表（"+startTime+"~"+endTime+"）";
		//commonManagerImpl.log(this.getServletRequest(), exportmsg);
		if(list!=null && list.size()>0){
			gridServerHandler.exportCSV(list, KeywordsDetail.class);
		}
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
				entity.setIntTaskType(1);
				keywordsTacticsImpl.save(entity) ;
				long nmDataGetterTaskId = keywordsTacticsImpl.getTaskId(entity.getDtOrderTime());
				if (businessIds != null && !"".equals(businessIds)) {
					String[] datas = businessIds.split(",");
					for (String ids : datas) {
						bussIds = ids.split(":");
						businessId = bussIds[1].trim();
						if (!vcFilterKeyValue1.equals("")) {
							vcFilterKeyValue1 = vcFilterKeyValue1.replace("，", ",");
							String[] vcFilterKeyValue = vcFilterKeyValue1.split(",");
							List keyList = new ArrayList<String>();
							for(int i=0;i<vcFilterKeyValue.length;i++){
								if (!keyList.contains(vcFilterKeyValue[i]) && keyList.size()<50) {
									long id = keywordsTacticsImpl.insertIsExactMark(nmDataGetterTaskId, vcFilterKeyValue[i],1);
									keywordsTacticsImpl.insertKeyValueSoTypeId(new Long(businessId),id);
									keyList.add(vcFilterKeyValue[i]);
								}
							}
						}
						if (!vcFilterKeyValue2.equals("")) {
							vcFilterKeyValue2 = vcFilterKeyValue2.replace("，", ",");
							String[] vcFilterKeyValue = vcFilterKeyValue2.split(",");
							List keyList2 = new ArrayList<String>();
							for(int i=0;i<vcFilterKeyValue.length;i++){
								if (!keyList2.contains(vcFilterKeyValue[i]) && keyList2.size()<50) {
									long id = keywordsTacticsImpl.insertIsExactMark(nmDataGetterTaskId, vcFilterKeyValue[i],0);
									keywordsTacticsImpl.insertKeyValueSoTypeId(new Long(businessId),id);
									keyList2.add(vcFilterKeyValue[i]);
								}
							}
						}
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
				List dlist = keywordsTacticsImpl.findDataGetterFilterId(entity.getNmDataGetterTaskId()+"");
				if (dlist!=null && dlist.size()>0) {
					String nmDataGetterFilterId = "";
					for (int i = 0; i < dlist.size(); i++) {
						nmDataGetterFilterId = nmDataGetterFilterId+dlist.get(i).toString()+",";
					}
					nmDataGetterFilterId = nmDataGetterFilterId.substring(0,nmDataGetterFilterId.length()-1);
					keywordsTacticsImpl.deleteKeyValueSoTypeIdFilter(nmDataGetterFilterId);
				}
				keywordsTacticsImpl.deleteKeyValueGetterFilter(entity.getNmDataGetterTaskId());
				if (businessIds != null && !"".equals(businessIds)) {
					String[] datas = businessIds.split(",");
					for (String ids : datas) {
						bussIds = ids.split(":");
						businessId = bussIds[1].trim();
						if (!vcFilterKeyValue1.equals("")) {
							vcFilterKeyValue1 = vcFilterKeyValue1.replace("，", ",");
							String[] vcFilterKeyValue = vcFilterKeyValue1.split(",");
							List keyList = new ArrayList<String>();
							for(int i=0;i<vcFilterKeyValue.length;i++){
								if (!keyList.contains(vcFilterKeyValue[i]) && keyList.size()<50) {
									long id = keywordsTacticsImpl.insertIsExactMark(entity.getNmDataGetterTaskId(), vcFilterKeyValue[i],1);
									keywordsTacticsImpl.insertKeyValueSoTypeId(new Long(businessId),id);
									keyList.add(vcFilterKeyValue[i]);
								}
							}
						}
						if (!vcFilterKeyValue2.equals("")) {
							vcFilterKeyValue2 = vcFilterKeyValue2.replace("，", ",");
							String[] vcFilterKeyValue = vcFilterKeyValue2.split(",");
							List keyList2 = new ArrayList<String>();
							for(int i=0;i<vcFilterKeyValue.length;i++){
								if (!keyList2.contains(vcFilterKeyValue[i]) && keyList2.size()<50) {
									long id = keywordsTacticsImpl.insertIsExactMark(entity.getNmDataGetterTaskId(), vcFilterKeyValue[i],0);
									keywordsTacticsImpl.insertKeyValueSoTypeId(new Long(businessId),id);
									keyList2.add(vcFilterKeyValue[i]);
								}
							}
						}
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
	
	public boolean isRepeat(String s,String value) {
		boolean flag = true;
		if (s!=null && !s.equals("")) {
			String[] values = s.split(",");
			for (int i = 0; i < values.length; i++) {
				if (values[i]!=null && !values[i].equals("") && values[i].equals(value)) {
					flag = false;
					break;
				}
			}
		}
		return flag;
	}
	
	public String edit(){
		entity = keywordsTacticsImpl.findTaskById(id);
		String dtStartTime = entity.getDtStartTime();
		String dtEndTime = entity.getDtEndTime();
		entity.setDtStartTime(dtStartTime.substring(0,10));
		entity.setDtEndTime(dtEndTime.substring(0,10));
		List<FtbKeyValueGetterFilter> kList = keywordsTacticsImpl.findKeyById(id);
		if (kList!=null && kList.size()>0) {
			vcFilterKeyValue1="";
			vcFilterKeyValue2="";
			businessIds="";
			business = "";
			for (FtbKeyValueGetterFilter key:kList) {
				if (key.isExactMark() && isRepeat(vcFilterKeyValue1,key.getVcFilterKeyValue())) {
					vcFilterKeyValue1 = vcFilterKeyValue1 + key.getVcFilterKeyValue()+",";
				}
				if(!key.isExactMark() && isRepeat(vcFilterKeyValue2,key.getVcFilterKeyValue())){
					vcFilterKeyValue2 = vcFilterKeyValue2 + key.getVcFilterKeyValue()+",";
				}
				if (isRepeat(business,key.getVcSoName())) {
					businessIds =businessIds + "0:"+key.getNmSoTypeId()+",";
					business =business + key.getVcSoName()+",";
				}
			}
			businessIds = businessIds.substring(0,businessIds.length()-1);
			business = business.substring(0,business.length()-1);
			if(!vcFilterKeyValue1.equals("")){
				vcFilterKeyValue1 = vcFilterKeyValue1.substring(0,vcFilterKeyValue1.length()-1);
			}
			if(!vcFilterKeyValue2.equals("")){
				vcFilterKeyValue2 = vcFilterKeyValue2.substring(0,vcFilterKeyValue2.length()-1);
			}
		}
		return "edit";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public KeywordsTactics getEntity() {
		return entity;
	}


	public void setEntity(KeywordsTactics entity) {
		this.entity = entity;
	}


	public void initBussnessDialogData() throws Exception {
		Struts2Utils.renderXml(packBussnessInfo());
	}
	
	/**
	 * 组装业务信息
	 */
	public String packBussnessInfo() {
		List<Phone> list = new ArrayList<Phone>();
		Map<String, Map<String, String>> map = keywordsTacticsImpl
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
	
	
	/**
	 * 判断该任务是否存在
	 * 
	 * @throws IOException
	 */
	public String checkUnique() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String vcTaskName = request.getParameter("vcTaskName").trim();
		int i = keywordsTacticsImpl.isVcTaskName(vcTaskName);
		if (i == -1) {// -1表示不存在该任务名称
			Struts2Utils.renderText("true");
		} else {
			Struts2Utils.renderText("false");
		}
		return null;
	}
}
