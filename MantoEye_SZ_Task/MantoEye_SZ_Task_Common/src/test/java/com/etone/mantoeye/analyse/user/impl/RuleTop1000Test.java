package com.etone.mantoeye.analyse.user.impl;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

public class RuleTop1000Test {

	@Test
	public final void testExecute() {
		RuleTop1000 test = new RuleTop1000();
		try {
			test.execute(null);
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

}
