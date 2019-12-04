package com.sparkans.banqi.user;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

public class UserSignInTest {

	private UserSignIn createTestSubject() {
		return new UserSignIn();
	}

	@Test
	public void testValidNickName() throws Exception {
		UserSignIn testSubject;
		String nickName = "";
		boolean result;

		// default test
		testSubject = createTestSubject();
		//result = testSubject.validUser(nickName);
	}
/*
	@Test
	public void testSignInUser() throws Exception {
		UserSignIn testSubject;
		UserBean userBean = null;
		boolean result;

		// default test
		testSubject = createTestSubject();
		result = testSubject.signInUser(userBean);
	}

 */
}