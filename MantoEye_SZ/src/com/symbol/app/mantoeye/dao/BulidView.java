package com.symbol.app.mantoeye.dao;

public class BulidView {
	public static String buildHourView(String table,String field,String condition,String whereConditon,String orderString) {
		StringBuffer hourView = new StringBuffer();
		hourView.append("select ");
		hourView.append("sum(f.nmAppUpFlush) as nmAppUpFlush, ");//0
		hourView.append("sum(f.nmAppDownFlush) as nmAppDownFlush, ");//1
		hourView.append("sum(f.nmFlush) as nmFlush, ");//2
		hourView.append("sum(f.nmUsers) as nmUsers, ");//3
		hourView.append("sum(f.nmVisitCounts) as nmVisitCounts, ");//4
		hourView.append("convert(numeric(20,2),(nmFlush/nmUsers)) as nmAveFlush, ");//5
		hourView.append("f.intYear, ");//6
		hourView.append("f.intMonth, ");//7
		hourView.append("f.intDay, ");//8
		hourView.append("f.intHour, ");//9
		hourView.append("f.intRaitype, ");//10
		hourView.append(field);
		hourView.append(" from ");
		hourView.append(table+" as f inner join dtbTerminal as d");
		hourView.append(" on f.nmTerminalId=d.nmTerminalId ");

		if (condition!=null && !condition.equals("")) {
			hourView.append(condition);
		}
		hourView.append(" where 1=1 ");
		if (whereConditon!=null && !whereConditon.equals("")) {
			hourView.append(whereConditon);
		}
		
		hourView.append(" group by ");
		hourView.append("f.intYear, ");
		hourView.append("f.intMonth, ");
		hourView.append("f.intWeek, ");
		hourView.append("f.intDay, ");
		hourView.append("f.intHour, ");
		hourView.append("f.intRaitype, ");
		hourView.append(field);
		hourView.append(" order by f.intYear ");
		if (orderString!=null && !orderString.equals("")) {
			hourView.append(orderString);
		}
		return hourView.toString();
	}

	//type:1非业务表，2为业务表
	public static String buildDayView(String table,String usersTable,String field,String condition,String whereConditon,String orderString,int areaType,int type) {
		String areaId="";
		switch (areaType) {
		case 1:
			areaId = "intBscId";
			break;
		case 2:
			areaId = "intSgsnId";
			break;
		case 3:
			areaId = "intBranchId";
			break;
		case 4:
			areaId = "intSaleAreaId";
			break;
		case 5:
			areaId = "intStreetId";
			break;
		case 6:
			areaId = null;
			break;
		}
		String sFlield = " select sum(nmAppUpFlush) as nmAppUpFlush, sum(nmAppDownFlush) as nmAppDownFlush, sum(nmFlush) as nmFlush, sum(nmVisitCounts) as nmVisitCounts, intYear, intMonth, intDay, intRaitype,nmTerminalId";
		String groupBy = " group by intYear, intMonth, intDay, intRaitype,nmTerminalId"; 
		if (areaId!=null) {
			sFlield = sFlield+","+areaId;
			groupBy = groupBy+","+areaId;
		}
		if (type==2) {
			sFlield = sFlield+",nmBussinessId,nmBussinessTypeId ";
			groupBy = groupBy+",nmBussinessId,nmBussinessTypeId ";
		}
		String tableString =sFlield + " from " +table+ groupBy;
		
		StringBuffer dayView = new StringBuffer();
		dayView.append("select ");
		dayView.append("sum(f.nmAppUpFlush) as nmAppUpFlush, ");//0
		dayView.append("sum(f.nmAppDownFlush) as nmAppDownFlush, ");//1
		dayView.append("sum(f.nmFlush) as nmFlush, ");//2
		dayView.append("sum(fu.nmUsers) as nmUsers, ");//3
		dayView.append("sum(f.nmVisitCounts) as nmVisitCounts, ");//4
		dayView.append("convert(numeric(20,2),(nmFlush/nmUsers)) as nmAveFlush, ");//5
		dayView.append("f.intYear, ");//6
		dayView.append("f.intMonth, ");//7
		dayView.append("f.intDay, ");//8
		dayView.append("f.intRaitype, ");//9
		dayView.append(field);
		dayView.append(" from (");
		dayView.append(tableString+") as f inner join dtbTerminal on f.nmTerminalId = dtbTerminal.nmTerminalId inner join "+usersTable+ " as fu");
		dayView.append(" on f.intYear=fu.intYear ");
		dayView.append(" and f.intMonth=fu.intMonth ");
		//dayView.append(" and f.intWeek=fu.intWeek ");
		dayView.append(" and f.intDay=fu.intDay ");
		dayView.append(" and f.intRaitype=fu.intRaitype ");
		dayView.append(" and f.nmTerminalId=fu.nmTerminalId ");
		if (condition!=null && !condition.equals("")) {
			dayView.append(condition);
		}
		dayView.append(" where 1=1 ");
		if (whereConditon!=null && !whereConditon.equals("")) {
			dayView.append(whereConditon);
		}
		
		dayView.append(" group by ");
		//dayView.append("fu.nmUsers, ");
		dayView.append("f.intYear, ");
		dayView.append("f.intMonth, ");
		//dayView.append("f.intWeek, ");
		dayView.append("f.intDay, ");
		dayView.append("f.intRaitype, ");
		dayView.append(field);
		dayView.append(" order by f.intYear ");
		if (orderString!=null && !orderString.equals("")) {
			dayView.append(orderString);
		}
		return dayView.toString();
	}
	
	public static String buildWeekView(String table,String usersTable,String field,String condition,String whereConditon,String orderString,int areaType,int type) {
		String areaId="";
		switch (areaType) {
		case 1:
			areaId = "intBscId";
			break;
		case 2:
			areaId = "intSgsnId";
			break;
		case 3:
			areaId = "intBranchId";
			break;
		case 4:
			areaId = "intSaleAreaId";
			break;
		case 5:
			areaId = "intStreetId";
			break;
		case 6:
			areaId = null;
			break;
		}
		String sFlield = " select sum(nmAppUpFlush) as nmAppUpFlush, sum(nmAppDownFlush) as nmAppDownFlush, sum(nmFlush) as nmFlush, sum(nmVisitCounts) as nmVisitCounts, intYear, intWeek, intRaitype,nmTerminalId";
		String groupBy = " group by intYear, intWeek, intRaitype,nmTerminalId"; 
		if (areaId!=null) {
			sFlield = sFlield+","+areaId;
			groupBy = groupBy+","+areaId;
		}
		if (type==2) {
			sFlield = sFlield+",nmBussinessId,nmBussinessTypeId ";
			groupBy = groupBy+",nmBussinessId,nmBussinessTypeId ";
		}
		String tableString =sFlield + " from " +table+ groupBy;
		
		StringBuffer weekView = new StringBuffer();
		weekView.append("select ");
		weekView.append("sum(f.nmAppUpFlush) as nmAppUpFlush, ");//0
		weekView.append("sum(f.nmAppDownFlush) as nmAppDownFlush, ");//1
		weekView.append("sum(f.nmFlush) as nmFlush, ");//2
		weekView.append("sum(fu.nmUsers) as nmUsers, ");//3
		weekView.append("sum(f.nmVisitCounts) as nmVisitCounts, ");//4
		weekView.append("convert(numeric(20,2),(nmFlush/nmUsers)) as nmAveFlush, ");//5
		weekView.append("f.intYear, ");//6
		weekView.append("f.intWeek, ");//7
		weekView.append("f.intRaitype, ");//8
		weekView.append(field);
		weekView.append(" from (");
		weekView.append(tableString+") as f inner join dtbTerminal on f.nmTerminalId = dtbTerminal.nmTerminalId inner join "+usersTable+ " as fu");
		weekView.append(" on f.intYear=fu.intYear ");
		weekView.append(" and f.intWeek=fu.intWeek ");
		weekView.append(" and f.intRaitype=fu.intRaitype ");
		weekView.append(" and f.nmTerminalId=fu.nmTerminalId ");
		if (condition!=null && !condition.equals("")) {
			weekView.append(condition);
		}
		weekView.append(" where 1=1 ");
		if (whereConditon!=null && !whereConditon.equals("")) {
			weekView.append(whereConditon);
		}
		weekView.append(" group by ");
		//weekView.append("f.nmUsers, ");
		weekView.append("f.intYear, ");
		weekView.append("f.intWeek, ");
		weekView.append("f.intRaitype, ");
		weekView.append(field);
		weekView.append(" order by f.intYear ");
		if (orderString!=null && !orderString.equals("")) {
			weekView.append(orderString);
		}
		
		return weekView.toString();
	}
	
	public static String buildMonthView(String table,String usersTable,String field,String condition,String whereConditon,String orderString,int areaType,int type) {
		String areaId="";
		switch (areaType) {
		case 1:
			areaId = "intBscId";
			break;
		case 2:
			areaId = "intSgsnId";
			break;
		case 3:
			areaId = "intBranchId";
			break;
		case 4:
			areaId = "intSaleAreaId";
			break;
		case 5:
			areaId = "intStreetId";
			break;
		case 6:
			areaId = null;
			break;
		}
		String sFlield = " select sum(nmAppUpFlush) as nmAppUpFlush, sum(nmAppDownFlush) as nmAppDownFlush, sum(nmFlush) as nmFlush, sum(nmVisitCounts) as nmVisitCounts, intYear, intMonth, intRaitype,nmTerminalId";
		String groupBy = " group by intYear, intMonth, intRaitype,nmTerminalId"; 
		if (areaId!=null) {
			sFlield = sFlield+","+areaId;
			groupBy = groupBy+","+areaId;
		}
		if (type==2) {
			sFlield = sFlield+",nmBussinessId,nmBussinessTypeId ";
			groupBy = groupBy+",nmBussinessId,nmBussinessTypeId ";
		}
		String tableString =sFlield + " from " +table+ groupBy;
		
		StringBuffer monthView = new StringBuffer();
		monthView.append("select ");
		monthView.append("sum(f.nmAppUpFlush) as nmAppUpFlush, ");//0
		monthView.append("sum(f.nmAppDownFlush) as nmAppDownFlush, ");//1
		monthView.append("sum(f.nmFlush) as nmFlush, ");//2
		monthView.append("sum(fu.nmUsers) as nmUsers, ");//3
		monthView.append("sum(f.nmVisitCounts) as nmVisitCounts, ");//4
		monthView.append("convert(numeric(20,2),(nmFlush/nmUsers)) as nmAveFlush, ");//5
		monthView.append("f.intYear, ");//6
		monthView.append("f.intMonth, ");//7
		monthView.append("f.intRaitype, ");//8
		monthView.append(field);
		monthView.append(" from (");
		monthView.append(tableString+") as f inner join dtbTerminal on f.nmTerminalId = dtbTerminal.nmTerminalId inner join "+usersTable+ " as fu");
		monthView.append(" on f.intYear=fu.intYear ");
		monthView.append(" and f.intMonth=fu.intMonth ");
		monthView.append(" and f.intRaitype=fu.intRaitype ");
		monthView.append(" and f.nmTerminalId=fu.nmTerminalId ");
		if (condition!=null && !condition.equals("")) {
			monthView.append(condition);
		}
		monthView.append(" where 1=1 ");
		if (whereConditon!=null && !whereConditon.equals("")) {
			monthView.append(whereConditon);
		}
		monthView.append(" group by ");
		//monthView.append("f.nmUsers, ");
		monthView.append("f.intYear, ");
		monthView.append("f.intMonth, ");
		monthView.append("f.intRaitype, ");
		monthView.append(field);
		monthView.append(" order by f.intYear ");
		if (orderString!=null && !orderString.equals("")) {
			monthView.append(orderString);
		}
		return monthView.toString();
	}
}
