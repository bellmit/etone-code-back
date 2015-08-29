package com.etone.mantoeye.analyse.process;

import org.junit.Test;

public class ExecExportDataProcessTest {

	@Test
	public final void testMain() {
		// vcSqlId,vcTableName,dtStatTime,taskId
		// ExecExportDataProcess.main(new
		// String[]{"query_ftbStatDayGprsContentUsers_V2","ftbStatNewBussNetSpaceHourUsers_20121230","2012-12-30 00:00:00","0"});
		// ExecExportDataProcess.main(new
		// String[]{"ftp;load_ftbStatDayGprsContentUsers_V2","ftbStatNewBussNetSpaceHourUsers_20121230","2012-12-30 00:00:00","0"});
		ExecExportDataProcess.main(new String[]{"query_ftbStatHourSaleAreaBussType_V2;ftp;load_ftbStatHourSaleAreaBussType_V2",
				"temp_ftbGnAppData_2013022811", "2013-02-28 11:00:00", "629030"});
	}

}
