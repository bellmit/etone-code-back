package com.symbol.wp.core.dao.impl;

/* 所有BO的父类 */
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.symbol.wp.core.constants.SqlrConstants;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.dto.UserAllinfoView;
import com.symbol.wp.core.dto.VBasePermissions;
import com.symbol.wp.core.entity.TbBasePermissions;
import com.symbol.wp.core.entity.TbBaseRoles;
import com.symbol.wp.core.entity.TbBaseUserInfo;
import com.symbol.wp.core.entity.TbBaseUserRole;
import com.symbol.wp.core.util.PropertiesUtil;
import com.symbol.wp.core.util.Utils;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;
//Spring DAO Bean的标识
@Repository
public class SysdataDAO  extends HibernateDao<TbBaseUserInfo, Long> {

    private static final Log log = LogFactory.getLog(SysdataDAO.class);

    /**
     * 通过用户名或用户名和密码查找是否有该用户
     *
     * @param userid
     *            用户帐号
     * @param userPwd
     *            用户密码
     * @param checkpwd
     *            是否跳过密码检查，用于测试，正式接入portal后，需用删除相关代码
     * @return UserAllinfo用户角色权限信息，返回null为找不到该用户
     * @author wendy
     */
    public UserAllinfoView authenticatebyVmUserAllinfo(String userid,
            String userPwd, boolean checkpwd) {

        UserAllinfoView userAllinfo = null;
        String hasmd5 = PropertiesUtil.getInstance().getProperty("system.password.check.encry");//数据库密码是否已经md5加密
        String pwd = userPwd;
        if(hasmd5!=null&&hasmd5.equalsIgnoreCase("MD5")){
        	pwd=Utils.getMD5String(userPwd);
        }  
       
        try {

            List list = null;
            if (!checkpwd) {
                list = super.find(SqlrConstants.sysSql_0001,
                        new Object[]{userid});
            } else {
                list = super.find(SqlrConstants.sysSql_0002, new Object[]{
                            userid, pwd});

            }
          //  logger.info(list.size()+"**");
            if (list != null && !list.isEmpty()) {
                /**
                 * 用户视图
                 */
                userAllinfo = new UserAllinfoView();
                for (Iterator it = list.iterator(); it.hasNext();) {
                    Object result[] = (Object[]) it.next();
                    userAllinfo.userinfo = (TbBaseUserInfo) result[0];
                    userAllinfo.baseRoles = (TbBaseRoles) result[1];
                    userAllinfo.baseUserRole = (TbBaseUserRole) result[2];
                }
            }
        } catch (Exception e) {
        	logger.error(e.getMessage());
        }
       // logger.info("userAllinfo:"+userAllinfo+"--name:"+userAllinfo.getBaseRoles().getVcRolesName());
        return userAllinfo;
    }

    /**
     * 
     * 快捷页面的类型为
     * @param userid
     * @return
     */
    public List<TbBasePermissions> getFirstMenu(String userNo) {
        List<TbBasePermissions> list = new ArrayList<TbBasePermissions>();
        Long bd = new Long(VarConstants.PERM_TYPE_MEMU);
        Query query = super.createQuery(SqlrConstants.TbBasePermissions_0015,
                new Object[]{bd,userNo});
        list = query.list();
        return list;
    }

//    /**
//     * 生成左侧菜单
//     *
//     * @param permisList
//     *            当前用户权限列表
//     * @param permList
//     *            所有的菜单列表
//     * @param pid
//     *            上方一级菜单id
//     * @return
//     */
//    public String getOldSubMenu(List permisList, List permList) {
//
//        String permis_id = "";
//        String parent_id = "";
//        String menu_name = "";
//        String redirect_url = "";
//        String name = "";
//        String url = "";
//        String parent_Name = "";
//        BigInteger TreeID = new BigInteger("0");
//        Integer position = 0;
//        StringBuffer menu_data = new StringBuffer();
//
//        List<String> id_list = new ArrayList<String>();
//        List<String> parent_list = new ArrayList<String>();
//        List<String> name_list = new ArrayList<String>();
//        List<String> url_list = new ArrayList<String>();
//        List<String> root_list = new ArrayList<String>();
//        if (permList != null && !permList.isEmpty()) {
//            for (Iterator it = permList.iterator(); it.hasNext();) {
//                VBasePermissions basePermisView = (VBasePermissions) it.next();
//                
//                
//                permis_id = String.valueOf(basePermisView.getId());
//                parent_id = String.valueOf(basePermisView.getVcParentId());///////////////
//                
//                
//                parent_Name = basePermisView.getParentMenuName();
//                menu_name = basePermisView.getVcMenuName();
//                redirect_url = basePermisView.getVcRedirectUrl();
//                if (permisList != null && permisList.indexOf(permis_id) != -1) {
//                    id_list.add(permis_id);
//                    parent_list.add(parent_id);
//                    name_list.add(menu_name);
//                    // 如果链接为#时
//                    if (redirect_url == null || redirect_url.equals("") || redirect_url.equals("#")) {
//                        url_list.add(redirect_url);
//                    } else {
//                        url_list.add(redirect_url + "&permId=" + permis_id);
//                    }
//                    if(parent_id.equals("BASE")){
//                    	 root_list.add(permis_id);
//                    }                 
//                }
//            }
//        }
//        menu_data.append("<ul id=\"browser\" class=\"filetree\">");
//        for (int i = 0; i < root_list.size(); i++) {
//        	position = id_list.indexOf(root_list.get(i));
//            url = url_list.get(position); 
//            name = name_list.get(position);
//            String perm_no = root_list.get(i);
//           
//     //       logger.info(name);
//            id_list.remove(position);
//            parent_list.remove(position);
//            name_list.remove(position);
//            url_list.remove(position);
//            if (hasChild(parent_list, root_list.get(i))) {
//                menu_data.append("<li><span id=\"foldermenu_"+perm_no+"\" class=\"folder\" ><span>" + name + "</span></span><ul>");
//                menu_data.append(createJCM(id_list, parent_list, name_list,
//                        url_list, root_list.get(i)));
//                menu_data.append("</ul></li>");
//            } else {
//                menu_data.append("<li><span id=\"filemenu_"+perm_no+"\" class=\"file\"><a target=\"frmmain\" href=\"" + url + "\">" + name);
//                menu_data.append("</a></span></li>");
//            }
//        }
//        menu_data.append("</ul>");
//        name_list = null;
//        logger.info(menu_data.toString());
//        return menu_data.toString();
//    }
    /**
     * 生成左侧菜单
     *
     * @param permisList
     *            当前用户权限列表
     * @param permList
     *            所有的菜单列表
     * @param pid
     *            上方一级菜单id
     * @return
     */
    public String getOldSubMenu(List permisList, List permList) {

        String permis_id = "";
        String parent_id = "";
        String menu_name = "";
        String redirect_url = "";
        String name = "";
        String url = "";
        String parent_Name = "";
        BigInteger TreeID = new BigInteger("0");
        Integer position = 0;
        StringBuffer menu_data = new StringBuffer();

        List<String> id_list = new ArrayList<String>();
        List<String> parent_list = new ArrayList<String>();
        List<String> name_list = new ArrayList<String>();
        List<String> url_list = new ArrayList<String>();
        List<String> root_list = new ArrayList<String>();
        if (permList != null && !permList.isEmpty()) {
            for (Iterator it = permList.iterator(); it.hasNext();) {
                VBasePermissions basePermisView = (VBasePermissions) it.next();
                
                
                permis_id = String.valueOf(basePermisView.getId());
                parent_id = String.valueOf(basePermisView.getVcParentId());///////////////
                
                
                parent_Name = basePermisView.getParentMenuName();
                menu_name = basePermisView.getVcMenuName();
                redirect_url = basePermisView.getVcRedirectUrl();
                if (permisList != null && permisList.indexOf(permis_id) != -1) {
                    id_list.add(permis_id);
                    parent_list.add(parent_id);
                    name_list.add(menu_name);
                    // 如果链接为#时
                    if (redirect_url == null || redirect_url.equals("") || redirect_url.equals("#")) {
                        url_list.add(redirect_url);
                    } else {
                        url_list.add(redirect_url + "&permId=" + permis_id);
                    }
                    if(parent_id.equals("BASE")){
                    	 root_list.add(permis_id);
                    }                 
                }
            }
        }
        for (int i = 0; i < root_list.size(); i++) {
        	position = id_list.indexOf(root_list.get(i));
            url = url_list.get(position); 
            name = name_list.get(position);
            String perm_no = root_list.get(i);
           
     //       logger.info(name);
            id_list.remove(position);
            parent_list.remove(position);
            name_list.remove(position);
            url_list.remove(position);
            
            menu_data.append("<h2 onclick=\"changeView(this.id)\" id=\"submenuh2_"+(i+1)+"\">"+name);
            if(name.length()==4){
            	menu_data.append("<img src=\"/skin/Default/images/MantoEye/submenu/ico_nohave.gif\" id=\"ico_submenuh2_"+(i+1)+"\"/></h2>");               
            }else{
                menu_data.append("<img src=\"/skin/Default/images/MantoEye/submenu/ico_nohave.gif\" style=\"margin-left:96px;\" id=\"ico_submenuh2_"+(i+1)+"\"/></h2>");
            }            
            menu_data.append("<div class=\"navigation\" id=\"div_submenuh2_"+(i+1)+"\" style=\"display: block;\">");
            menu_data.append("<?IMPORT NAMESPACE = TVNS IMPLEMENTATION = \"/skin/Default/images/MantoEye/submenu/treeview.htc\" />");
            menu_data.append("<tvns:treeview systemimagespath=\"/skin/Default/images/MantoEye/submenu/TreeImages/\" selectednodeindex=\"0\">");
            
            
            if (hasChild(parent_list, root_list.get(i))) {
            	menu_data.append(createJCM(id_list, parent_list, name_list,url_list, root_list.get(i)));
            }
            menu_data.append("</tvns:treeview></div>");
        }
        name_list = null;
    //    logger.info(menu_data.toString());
        return menu_data.toString();
    }

    /**
     * @param Title
     * @param Url
     * @param Event
     * @param TargetFrame
     * @param permisList
     * @return 参数设定:() 功能描述:系统左边系统菜单生成 逻辑判断: 返回值:
     * @author:wendy
     */
    public String getLeftMenu(String Title, String Url, String Event,
            String TargetFrame, List<String> permisList, List permList, Long pid) {

        String redirect_url = "";
        String target_frame = "";
        String TreeID = "";
        StringBuffer contents = new StringBuffer();

        if (permList != null && !permList.isEmpty()) {
            for (Iterator it = permList.iterator(); it.hasNext();) {
                VBasePermissions permisView = (VBasePermissions) it.next();
                TreeID = String.valueOf(permisView.getId());
                if (permisList != null && permisList.indexOf(TreeID) != -1) {
                    if (TreeID.equals(pid + "")) {
                        log.info("XXXXXXXXXXXXXXXX");
                        contents.append(" d.add(" + TreeID + ",-1,\"" + permisView.getVcMenuName() + "\",\"#\");\n");
                        continue;
                    }

                    contents.append("	d.add(");
                    contents.append(TreeID);
                    contents.append(",");
                    contents.append(permisView.getVcParentId());
                    contents.append(",\"");
                    contents.append(permisView.getVcMenuName().replace("-", ""));
                    contents.append("\",\"");
                    if (Url != null || !Url.equals("")) {
                        redirect_url = Url.replace("[parm]", TreeID);
                        // 如果链接为#时
                        if (permisView.getVcRedirectUrl() == null || permisView.getVcRedirectUrl().equals("") || permisView.getVcRedirectUrl().equals("#")) {
                            redirect_url = Url.replace("[url]", permisView.getVcRedirectUrl());
                        } else {
                            redirect_url = Url.replace("[url]", permisView.getVcRedirectUrl() + "&permId=" + TreeID);
                        }
                        contents.append(redirect_url);
                    }
                    contents.append("\",\"");
                    if (Event != null || !Event.equals("")) {
                        contents.append(Event.replace("[parm]", TreeID));
                    }
                    target_frame = TargetFrame;
                    if (redirect_url.equals("#")) {
                        target_frame = "";
                    }
                    contents.append("\",\"\",\"" + target_frame + "\",\"\",\"\",\"\",0, 0);\n");
                }
            }
        }

        contents.append("	document.write(d);\n");
        return contents.toString();
    }

    /**
     * 该菜单是否包含子菜单
     *
     * @param parentList
     * @param parentId
     * @return
     */
    public boolean hasChild(List<String> parentList, String parentId) {
        boolean isHasChild = false;
        if (parentList.indexOf(parentId) != -1) {
            isHasChild = true;
        }
        return isHasChild;
    }

    /**
     * 生成菜单
     *
     * @param idList
     * @param parentList
     * @param nameList
     * @param urlList
     * @param parentId
     * @return
     */
    public String createJCM(List<String> idList, List<String> parentList,
            List<String> nameList, List<String> urlList, String parentId) {
        String id = "";
        String name = "";
        String url = "";
        int position = 0;
        StringBuffer contents = new StringBuffer();
        while (parentList.indexOf(parentId) != -1) {
            position = parentList.indexOf(parentId);
            id = idList.get(position);
            name = nameList.get(position);
            url = urlList.get(position);
            idList.remove(position);          
            
            parentList.remove(position);
            nameList.remove(position);
            urlList.remove(position);
            
          //  <tvns:treenode navigateurl="#" expanded="true" nodedata="2" target="">总体分析 
           
            if (hasChild(parentList, id)) {
            	contents.append(" <tvns:treenode navigateurl=\""+url+"\" expanded=\"true\" nodedata=\"2\" target=\"\">" +name);
                contents.append(createJCM(idList, parentList, nameList,
                        urlList, id));
            }else{
            	contents.append(" <tvns:treenode target=\""+url+"\"");
            	contents.append(" expanded=\"false\" nodedata=\"2\" onclick=\"newTab(false,this)\" id=\""+id+"\" name=\""+name+"\">"+name);
            }
            contents.append(" </tvns:treenode>");
        }
        return contents.toString();
    }
}
