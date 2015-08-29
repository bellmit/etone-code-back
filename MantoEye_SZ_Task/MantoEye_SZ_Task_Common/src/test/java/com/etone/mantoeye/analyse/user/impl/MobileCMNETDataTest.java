package com.etone.mantoeye.analyse.user.impl;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

public class MobileCMNETDataTest {

	@Test
	public final void testExecute() {
		MobileCMNETData data = new MobileCMNETData();
		try {
			data.execute(null);
		} catch (SQLException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
