package common.util;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * spring myBatis 测试基类
 * 
 * @author HHP
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-mybatis.xml", "classpath:spring-ehcache.xml" })
public class SpringTestBase {

	/*
	 * @Test public void test1(){ ApplicationContext ac = new
	 * ClassPathXmlApplicationContext(new String[]{"spring.xml",
	 * "spring-mybatis.xml"}); UsersService usersService = (UsersService)
	 * ac.getBean("usersService"); Users users =
	 * usersService.getUsersById("57f86b5a-e840-40a8-800d-98bc7231f952");
	 * System.out.println(users.getName()); }
	 */

}
