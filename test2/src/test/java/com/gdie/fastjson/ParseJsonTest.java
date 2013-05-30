package com.gdie.fastjson;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.gdie.common.JsonUtil;

public class ParseJsonTest {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ParseJsonTest.class);

	@Test
	public void testParseJson() {
		String string = "{\"postgresql-9.1\":[{\"name\":\"account1\",\"label\":\"postgresql-9.1\",\"plan\":\"free\",\"tags\":[\"postgresql\",\"postgresql-9.1\",\"relational\",\"postgresql-9.1\",\"postgresql\"],\"credentials\":{\"name\":\"da3322cb0f5db4251b582b4c6279c9111\",\"host\":\"10.0.17.135\",\"hostname\":\"10.0.17.135\",\"port\":5432,\"user\":\"u02290e76cf984a3f99911257b53a4f93\",\"username\":\"u02290e76cf984a3f99911257b53a4f93\",\"password\":\"p4e91c24a1f3f466bbac47473318f69e4\"}}]}";

		String string2 = string.replaceFirst("postgresql-9.1", "postgresql");
		logger.debug(string);
		logger.debug(string2);
//		Vcap vcap = JSON.parseObject(string2, Vcap.class);
//		logger.debug("vcap:" + vcap.getPostgresqls()[0].getCredentials().getHostname());
	}
	
	@Test
	public void testString(){
		String[] aaa = "ge:use_date".split(":");
		System.out.println(JsonUtil.toJSONString2(aaa));
	}
}
