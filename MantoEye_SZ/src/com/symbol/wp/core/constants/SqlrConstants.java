package com.symbol.wp.core.constants;

/**
 * SQL语句
 * @Title: SqlrConstants.java
 * @Description: <br>
 *               <br>
 * @Company: etonetech
 * @Created Mar 20, 2009 3:16:32 PM
 * @author ranhualin
 * @version 1.0
 * @since 1.0
 */
public class SqlrConstants {
	
	public static final String TbBasePermissions_0001 = "from TbBasePermissions a where a.btShow = '1' and a.btDeleted='0'  order by a.nmOrderNum,a.vcMenuName";////////
	public static final String TbBasePermissions_0003 = "from TbBasePermissions a where a.tiPermType=1 and a.btShow = '1' and a.btDeleted='0'  order by a.nmOrderNum,a.vcMenuName";////////
	public static final String TbBasePermissions_0002 = "select a.vcMenuName from TbBasePermissions a where a.id=? and a.btShow = '1' and a.btDeleted='0'  order by a.nmOrderNum,a.vcMenuName";//////
	public static final String TbBasePermissions_0004 = "from TbBasePermissions a where a.tiPermType=1 and a.vcParentId = 'BASE'  and a.btShow = '1' and a.btDeleted='0'  and a.id in (select aa.vcPermId from TbBaseRolePermis aa where aa.vcRoleId in (select c.vcRoleId from TbBaseUserRole c where c.vcUserId=?)) order by a.nmOrderNum,a.vcMenuName";////////
	public static final String TbBasePermissions_0006 = "from TbBasePermissions a where a.tiPermType=1 and a.vcParentId = ?  and a.btShow = '1' and a.btDeleted='0'  and a.id in (select aa.vcPermId from TbBaseRolePermis aa where aa.vcRoleId in (select c.vcRoleId from TbBaseUserRole c where c.vcUserId=?)) order by a.nmOrderNum,a.vcMenuName";////////

	
	public static final String TbBasePermissions_0005 = "from TbBasePermissions a where a.vcParentId=? and a.id in (select c.vcPermId from TbBaseRolePermis c where c.vcRoleId in (select b.vcRoleId from TbBaseUserRole b where b.vcUserId=?)) and a.btDeleted='0' and a.tiPermType=2  order by a.nmOrderNum";//按钮
	public static final String TbBasePermissions_0010 = "from TbBasePermissions a where a.vcParentId=? and a.id in (select c.vcPermId from TbBaseRolePermis c where c.vcRoleId in (select b.vcRoleId from TbBaseUserRole b where b.vcUserId=?)) and a.btDeleted='0'  order by a.nmOrderNum";
	public static final String TbBasePermissions_0015 = "from TbBasePermissions a where a.tiPermType=? and a.id in (select c.vcPermId from TbBaseRolePermis c where c.vcRoleId in (select b.vcRoleId from TbBaseUserRole b where b.vcUserId=?)) and a.btDeleted='0' and a.btShow='1' order by a.nmOrderNum";//顶部快捷页面
	public static final String TbBasePermissions_0020 = "from TbBasePermissions a where a.id in (select c.vcPermId from TbBaseRolePermis c where c.vcRoleId in (select b.vcRoleId from TbBaseUserRole b where b.vcUserId=?)) and a.btDeleted='0' and a.tiPermType=2  order by a.nmOrderNum";//按钮
	public static final String TbBasePermissions_0021 = "from TbBasePermissions a where a.id in (select c.vcPermId from TbBaseRolePermis c where c.vcRoleId in (select b.vcRoleId from TbBaseUserRole b where b.vcUserId=?)) and a.btDeleted='0' and a.tiPermType=9  order by a.nmOrderNum";//其他

	
	public static final String TbBaseRolePermis_0001 = "from TbBaseRolePermis a where a.vcPermId = (select b.id from TbBasePermissions b where b.id=? and b.btDeleted='0') and a.vcRoleId in (select c.id from TbBaseUserRole c where c.vcUserId=?)";
	public static final String TbBaseRolePermis_0002 = "from TbBaseRolePermis a where a.vcRoleId in (select c.vcRoleId from TbBaseUserRole c where c.vcUserId=?)";
	public static final String TbBaseRolePermis_0003 = "from TbBaseRolePermis a where a.vcRoleId in (?)";
	
	public static final String TbBaseUserRole_0001 = "from TbBaseUserRole a where a.vcUserId=?";
	
	public static final String sysSql_0001  = "from TbBaseUserInfo a,TbBaseRoles b,TbBaseUserRole c where a.vcLoginName=?  and a.id=c.vcUserId and c.vcRoleId=b.id";
	public static final String sysSql_0002	= "from TbBaseUserInfo a,TbBaseRoles b,TbBaseUserRole c, TbBaseUserInfo e where a.vcLoginName=? and e.vcPassword=? and a.id=c.vcUserId and a.id=e.id and c.vcRoleId=b.id ";
	
}
