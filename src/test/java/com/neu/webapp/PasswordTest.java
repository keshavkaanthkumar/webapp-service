package com.neu.webapp;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import com.neu.webapp.model.PasswordReq;

public class PasswordTest {

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPasswordUpdate() {
		PasswordReq passwordReq=new PasswordReq();
		passwordReq.setNewPassword("Keshav");
		passwordReq.setOldPassword("Keshav007#");
		ResponseEntity resp=null;
		try {
			resp=new UserController().passwordUpdate(passwordReq, "dsfdsfsdvcvxc");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(resp.getStatusCodeValue(), 400);
	}

}
