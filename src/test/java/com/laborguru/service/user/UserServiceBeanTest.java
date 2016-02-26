package com.laborguru.service.user;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.laborguru.model.User;
import com.laborguru.service.user.dao.UserDao;


public class UserServiceBeanTest {

	private UserService userService;
	private UserDao mockUserDao;

	@Before
	public void setUp(){
		userService = new UserServiceBean();
		
		mockUserDao = createMock(UserDao.class);
		userService.setUserDao(mockUserDao);
	}
	
	
	@Test (expected= IllegalArgumentException.class)
	public void getUserByUserName_userNull(){
		userService.getUserByUserName(null);		
	}	
	
	@Test
	public void getUserByUserName_happyPath(){
		User user = new User();		
		user.setUserName("username_1");
		
		User userResult = new User();		
		userResult.setUserName("username_1");
		userResult.setPassword("password_1");
		userResult.setName("name_1");
		userResult.setSurname("surname_1");
		
		expect(mockUserDao.getUserByUsername(user)).andReturn(userResult);
		replay(mockUserDao);
		
		User testResult = userService.getUserByUserName(user);
		
		assertEquals(userResult, testResult);
		verify(mockUserDao);
	}
		
	@Test (expected= IllegalArgumentException.class)
	public void getUserByUserName_userNameNull(){
		User user = new User();		
		
		userService.getUserByUserName(user);		
	}	
	
	@Test
	public void getUserByUserName_wrongName(){
		User user = new User();		
		user.setUserName("wrong_username");
		
		expect(mockUserDao.getUserByUsername(user)).andReturn(null);
		replay(mockUserDao);
		
		User testResult = userService.getUserByUserName(user);
		
		assertEquals(null, testResult);
		verify(mockUserDao);
	}
	
	/*@Test
	public void authenticateUser_happyPath(){
		User user = new User();		
		user.setUserName("username_1");
		user.setPassword("password_1");
		
		User userResult = new User();		
		userResult.setUserName("username_1");
		userResult.setPassword("password_1");
		userResult.setName("name_1");
		userResult.setSurname("surname_1");
		
		expect(mockUserDao.getUserByUsername(user)).andReturn(userResult);
		replay(mockUserDao);
		
		User testResult = userService.authenticateUser(user);
		
		assertEquals(userResult, testResult);
		verify(mockUserDao);
	}
	*/
	/*@Test
	public void authenticateUser_invalidUsername(){
		
		User user = new User();		
		user.setUserName("wrong_username");
		user.setPassword("password_1");
		
		expect(mockUserDao.getUserByUsername(user)).andReturn(null);
		replay(mockUserDao);
		
		User testResult = userService.authenticateUser(user);
		
		assertEquals(null, testResult);
		verify(mockUserDao);
	}
	*/
	/*@Test
	public void authenticateUser_invalidPassword(){
		
		User user = new User();		
		user.setUserName("username_1");
		user.setPassword("wrong_password");

		User userResult = new User();		
		userResult.setUserName("username_1");
		userResult.setPassword("password_1");
		userResult.setName("name_1");
		userResult.setSurname("surname_1");		
		
		expect(mockUserDao.getUserByUsername(user)).andReturn(userResult);
		replay(mockUserDao);
		
		User testResult = userService.authenticateUser(user);
		
		assertEquals(null, testResult);
		verify(mockUserDao);
	}
	*/
}
