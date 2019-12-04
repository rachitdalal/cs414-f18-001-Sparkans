package com.sparkans.banqi.user;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

public class UserRegistrationTest {

	private UserRegistration createTestSubject() {
		return new UserRegistration();
	}
/*
	@Test
	public void testValidateEmail() throws Exception {
		UserRegistration testSubject;
		String email = "";
		boolean result;

		// default test
		testSubject = createTestSubject();
		result = testSubject.validateEmail(email);
	}

	@Test
	public void testValidateNickName() throws Exception {
		UserRegistration testSubject;
		String nickName = "";
		boolean result;

		// default test
		testSubject = createTestSubject();
		result = testSubject.validateNickName(nickName);
	}
*/
	@Test
	public void testValidatePassword() throws Exception {
		UserRegistration testSubject;
		String password = "";
		boolean result;

		// default test
		testSubject = createTestSubject();
		assertThrows(RuntimeException.class, () -> testSubject.validatePassword(password));
	}
/*
	@Test
	public void testCreateUser() throws Exception {
		UserRegistration testSubject;
		UserBean userBean = null;
		boolean result;

		// default test
		testSubject = createTestSubject();
		result = testSubject.createUser(userBean);
	}

 */
}