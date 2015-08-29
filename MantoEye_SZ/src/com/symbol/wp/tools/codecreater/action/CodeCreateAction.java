package com.symbol.wp.tools.codecreater.action;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

import com.symbol.wp.core.entity.TbBaseDepartment;
import com.symbol.wp.modules.struts2.CRUDActionSupport;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.tools.codecreater.executer.FileManager;
import com.symbol.wp.tools.codecreater.info.CreaterInfo;
import com.symbol.wp.tools.codecreater.info.ItemType;
import com.symbol.wp.tools.codecreater.info.MemoInfo;

public class CodeCreateAction extends CRUDActionSupport<TbBaseDepartment> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FileManager fileManager = null;
	private static String SPITER = ",";
	private  CreaterInfo ctInfo = new CreaterInfo();
	private String rows  = "";
	@Override
	public String delete() throws Exception {
		return null;
	}

	@Override
	public String input() throws Exception {
		return null;
	}

	
	
	
	@Override
	public String list() throws Exception {
		return INDEX;
	}

	@Override
	protected void prepareModel() throws Exception {

	}

	@Override
	public String save() throws Exception {
		setValue();
		
		//把输入信息保存到TXT文件里，方便下次自动赋值
		fileManager = new FileManager();
//		fileManager.saveToFile(ctInfo);
		
		HashMap <String, MemoInfo>itemMemoMap = rowsToMap(ctInfo);
		
		
		//取得模板代码
		String fileString = fileManager.readModleFile(ctInfo.getCodeType());
		logger.info("*******************************************************");
		logger.info(fileString);
		logger.info("*******************************************************");
		//生成新的代码
		String newFileString =  fileManager.getNewFileString(fileString, ctInfo,itemMemoMap);
//		newFileString = new String(newFileString.getBytes("utf-8"),"GBK");
		
		
		
		//保存到指定目录
		String tmpPath = getTmpFilePath();
		logger.info("save path : ["+tmpPath+"]");
		fileManager.writeToFile(newFileString,tmpPath);
		return SUCCESS;
	}

	/**
	 * 临时文件的路径
	 * @return
	 */
	private String getTmpFilePath()
	{
		HttpServletRequest httpRequest = Struts2Utils.getRequest();
//		URL  url = this.getClass().getResource("");
//		logger.info("url *** "+url);
//		String path = new File(url.getFile()).getParent();
//		logger.info("path *** "+path);
//		return path+"\\model\\tmp.jsp";
		String default_path = httpRequest.getSession().getServletContext().getRealPath("/") + "tools\\codecreater\\tmp.jsp";
		return default_path;
	}
	/**
	 * 把格式如: private String name;名字  把项与注释放进MAP里
	 * 
	 * @param _rows
	 * @return
	 */
	private HashMap<String,MemoInfo> rowsToMap (CreaterInfo info)
	{
		String[] rows = info.getRows().trim().split("\n");
		HashMap<String,MemoInfo> map = new HashMap<String,MemoInfo>();
		//项与注释
		String [] itemAndMemo = null;
		//解释属性
		String[] items = null;
		String proItem = "";
		
		
		int type = 1;
		for(int i=0;i<rows.length;i++)
		{
			
			MemoInfo memoInfo = new MemoInfo();
			logger.info("["+rows[i]+"]");
			itemAndMemo = rows[i].trim().split(";");
			if(itemAndMemo.length>=2)
			{
			//private String item;
			items = itemAndMemo[0].trim().split(" ");
			//得到项名
			proItem = items[2].trim();
			//memo
			String memo = itemAndMemo[1].trim().replaceAll("/", "");
			
			type = getItemType(info,(i+1)+"");
			memoInfo.setItemName(proItem);
			memoInfo.setMemo(memo);
			memoInfo.setType(type);
			
			map.put(proItem, memoInfo);
			logger.info("&&&&&&&&&&&&&&&& map size &&&&&&&&&&&&&&& "+map.size());
			}
			else
			{
				logger.info("**************itemAndMemo length <2*********");
			}
		}
		return map;
	}
	
	private int getItemType(CreaterInfo info,String itmeNo)
	{
		ArrayList<String> textList = info.getTextType();
		ArrayList<String> testAreaList=info.getTextAreaType();
		ArrayList<String> selectList = info.getSelectType();
		ArrayList<String> uneditableList = info.getUnEditAbleType();
		
//		logger.info("&&&&&&&&&&&&& uneditableList :"+uneditableList);
		if(textList.contains(itmeNo))
		{
			if(uneditableList.contains(itmeNo))
			{
//			logger.info("unedit able contain ..."+itmeNo);
				return ItemType.TEXT_UNEDITABLE;
			}
				
			else
			{
//				logger.info("unedit able  not contain ..."+itmeNo);
				return ItemType.TEXT;
			}
		}
		if(testAreaList.contains(itmeNo))
		{
			if(uneditableList.contains(itmeNo))
				return ItemType.TEXTAREA_UNEDITABLE;
			else
				return ItemType.TEXTAREA;
		}
		if(selectList.contains(itmeNo))
		{
				return ItemType.SELECT;
		}
		
		return ItemType.TEXT;
	}
	
	public TbBaseDepartment getModel() {
		return null;
	}

	/**
	 * 把表单的值设入INFO
	 */
	private void setValue()
	{
		//get value from form
		HttpServletRequest request = ServletActionContext.getRequest();
		String codeType = request.getParameter("codeType").trim();
		String tableName = request.getParameter("tableName").trim();
		 rows = request.getParameter("rows").trim();
		String textTypeStr = request.getParameter("textType").trim();
		String textAreaTypeStr = request.getParameter("textAreaType").trim();
		String selectTypeStr = request.getParameter("selectType").trim();
		String unEditableTypeStr = request.getParameter("unEditableType").trim();
		
		
		//set value to info
		ctInfo.setCodeType(new Integer(codeType.trim()).intValue());
		ctInfo.setTableName(tableName.trim());
		ctInfo.setRows(rows.trim().replace("\n\r", "\n"));
		ctInfo.setSelectType(stringToList(selectTypeStr));
		ctInfo.setTextType(stringToList(textTypeStr));
		ctInfo.setTextAreaType(stringToList(textAreaTypeStr));
		ctInfo.setUnEditAbleType(stringToList(unEditableTypeStr));
	}
	/**
	 * string split by ","
	 * @param str
	 * @return
	 */
	private ArrayList <String>stringToList(String str)
	{
		String[] strs = str.split(SPITER);
		ArrayList <String>list = new ArrayList<String>();
		for(String tmp:strs)
		{
			list.add(tmp);
		}
		return list;
	}
}
