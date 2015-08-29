package com.etone.mantoeye.analyse.user.impl;

import java.sql.SQLException;

import org.junit.Test;

public class TempUsersDataTest {

	@Test
	public final void testExecute() {
		TempUsersData temp = new TempUsersData();
		try {
			temp.execute(null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
