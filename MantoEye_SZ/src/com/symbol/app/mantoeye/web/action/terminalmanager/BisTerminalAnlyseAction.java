package com.symbol.app.mantoeye.web.action.terminalmanager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;

public class BisTerminalAnlyseAction extends BaseDispatchAction {

	HttpServletRequest request = ServletActionContext.getRequest();
	public String delete() throws Exception {
		return null;
	}

	public String edit() throws Exception {
		return null;
	}
	
	public String getCurrentTime(){
		Calendar c=Calendar.getInstance();
		String date =c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DATE);
		return date;
	}

	public String list() throws Exception {
		String vcname=request.getParameter("filter_LIKE_vcName");
		String vcnumber=request.getParameter("filter_LIKE_vcNumber");
		String date=request.getParameter("filter_GE|DATE_dtRecordTime");
		if(date==null||date==""){
			date=this.getCurrentTime();
		}else{
			date=date.substring(0, 10);
		}
		Page page =new Page();
		List listData=this.readFile(date);
		page.setTotalCount(listData.size());
		page.setPageNo(1);
		page.setPageSize(15);
		List list=new ArrayList();
		for(int i=0;i<listData.size();i++){
			list.add(listData.get(i));
			if(i==14){
				break;
			}
		}
		request.setAttribute("filter_LIKE_vcName", vcname);
		request.setAttribute("filter_LIKE_vcNumber", vcnumber);
		request.setAttribute("filter_GE|DATE_dtRecordTime", date);
		request.setAttribute("dataList", list);
		request.setAttribute("page", page);
		return INDEX;
	}
	
	public List<List<String>> readFile(String date){
		List<List<String>> listAllData=new ArrayList<List<String>>();
		Long i=Math.round(Math.random());
		Random random = new Random();
		
			/*String file=this.getClass().getResource("/bis.txt").getFile();
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
			String line=null;*/
			String[] brand={"诺基亚","三星","多普达","索爱"};
			String[] xinghao={"AmoiN6","SAMSUNGSGH-i718+","SAMSUNGSGH-i908E","AmoiE65+","DopodD600","COOLPAD768","DopodP800","MotoA1200e","DopodS900","SAMSUNGSGH-i718"};
			List<String> listData=null;
			for(int k=0;k<100;k++){
				int j=(int)Math.round(Math.random()*3);
				listData=new ArrayList<String>();
				if(i==0){
					if(k==0){
						listData.add("时间");
						listData.add("终端品牌");
						listData.add("终端型号");
						listData.add("QQ流量(MB)");
						listData.add("QQ流量占比(%)");
						listData.add("QQ用户数");
						listData.add("QQ用户数占比(%)");
						listData.add("QQ平均流量");
					}else{
					listData.add(date);
					listData.add(brand[j]);
					listData.add(xinghao[random.nextInt(10)]);
					listData.add(String.valueOf(random.nextInt(1023)));
					listData.add(chageValue(Math.random()));
					listData.add(String.valueOf(random.nextInt(123)));
					listData.add(chageValue(Math.random()));
					listData.add(String.valueOf(random.nextInt(12)));
					}
				}else{
					if(k==0){
						listData.add("时间");
						listData.add("终端品牌");
						listData.add("终端型号");
						listData.add("QQ流量(MB)");
						listData.add("QQ流量占比(%)");
						listData.add("QQ用户数");
						listData.add("QQ用户数占比(%)");
						listData.add("QQ平均流量");
						listData.add("飞信流量(MB)");
						listData.add("飞信流量占比(%)");
						listData.add("飞信用户数");
						listData.add("飞信用户数占比(%)");
						listData.add("飞信平均流量");
					}else{
					listData.add(date);
					listData.add(brand[j]);
					listData.add(xinghao[random.nextInt(10)]);
					listData.add(String.valueOf(random.nextInt(1023)));
					listData.add(chageValue(Math.random()));
					listData.add(String.valueOf(random.nextInt(123)));
					listData.add(chageValue(Math.random()));
					listData.add(String.valueOf(random.nextInt(12)));
					listData.add(String.valueOf(random.nextInt(890)));
					listData.add(chageValue(Math.random()));
					listData.add(String.valueOf(random.nextInt(98)));
					listData.add(chageValue(Math.random()));
					listData.add(String.valueOf(random.nextInt(10)));
					
					}
				}
				
				listAllData.add(listData);
				
			}
		return listAllData;
	}
	
	public String chageValue(Double d){
		String str=d.toString();
		String value=str.substring(0, 3);
		return value;
	}

	public String save() throws Exception {
		return null;
	}

	public String update() throws Exception {
		return null;
	}

	
}
