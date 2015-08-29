package com.symbol.app.mantoeye.web.action.note;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.symbol.app.mantoeye.dto.alarm.AlarmBean;
import com.symbol.app.mantoeye.dto.mms.SpPortControlBean;
import com.symbol.app.mantoeye.dto.note.IndexNote;
import com.symbol.app.mantoeye.service.note.IndexNoteManager;
import com.symbol.wp.core.constants.MsgConstants;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseAction;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

@Controller
@Scope("prototype")
public class IndexNoteAction extends BaseAction {

	private static final long serialVersionUID = 6628104754669846107L;

	private IndexNoteManager indexNoteManager;

	public IndexNoteManager getIndexNoteManager() {
		return indexNoteManager;
	}

	@Resource
	public void setIndexNoteManager(IndexNoteManager indexNoteManager) {
		this.indexNoteManager = indexNoteManager;
	}

	public ICommonManager commonManagerImpl;

	private IndexNote indexNote;
	private String msg;

	public String add() {
		HttpServletRequest request = Struts2Utils.getRequest();
		try {
			indexNoteManager.add(indexNote);
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00101);
			msg = "添加首页公告栏信息成功!";
			commonManagerImpl.log(request, msg);
			return SUCCESS;
		} catch (RuntimeException e) {
			logger.error(e.getMessage());
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			msg = "添加首页公告栏信息失败!";
			commonManagerImpl.log(request, msg);
			return ERROR;
		}
	}

	public IndexNote getIndexNote() {
		return indexNote;
	}

	public void setIndexNote(IndexNote indexNote) {
		this.indexNote = indexNote;
	}

	public ICommonManager getCommonManagerImpl() {
		return commonManagerImpl;
	}

	@Resource(name = "commonManagerImpl")
	public void setCommonManagerImpl(ICommonManager commonManagerImpl) {
		this.commonManagerImpl = commonManagerImpl;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String edit() {
		HttpServletRequest request = Struts2Utils.getRequest();
		try {
			indexNoteManager.edit(indexNote);
			msg = "更新首页公告栏信息成功！";
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00102);
			commonManagerImpl.log(request, msg);
			return SUCCESS;
		} catch (RuntimeException e) {
			logger.error(e.getMessage());
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			msg = "更新首页公告栏信息失败！";
			commonManagerImpl.log(request, msg);
			return ERROR;
		}
	}

	private String ids;

	public void delete() {
		HttpServletRequest request = ServletActionContext.getRequest();

		try {
			indexNoteManager.delete(ids);
			msg = "删除首页公告栏信息成功！";
			// msg = "删除首页公告栏[" + ids + "]信息成功！";
		} catch (RuntimeException e) {
			logger.error(e.getMessage());
			msg = "删除首页公告栏[" + ids + "]信息失败！";
		}
		commonManagerImpl.log(request, msg);
		Struts2Utils.renderText(msg);
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public void query() {
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());

		Page<IndexNote> page = indexNoteManager.query(gridServerHandler);

		gridServerHandler.setTotalRowNum(page.getTotalCount());
		gridServerHandler.setData(page.getResult(), IndexNote.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	public void queryJson() {

	}

	public String findById() {
		try {
			indexNote = indexNoteManager.findById(ids);
			return "editPrePare";
		} catch (RuntimeException e) {
			logger.error(e.getMessage());
			msg = "加载数据失败！";
			return ERROR;
		}
	}

	public void export() throws IOException {
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());

		List<IndexNote> list = indexNoteManager.export();
		String emsg = "导出首页公告栏信息数据！";
		commonManagerImpl.log(Struts2Utils.getRequest(), emsg);
		gridServerHandler.exportXLS(list, IndexNote.class);
	}

}
