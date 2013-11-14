package net.study.code;

import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( locations = { "classpath*:test/*Context.xml" } )
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class CodeImplTest {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	CodeImpl impl;

	/**
	 * findList() test
	 * @throws Exception
	 */
	@Test
	public void findList() throws Exception {
		// parameters
		Map condition = new HashMap();
		condition.put("codecategorykey", "3039A");
		condition.put("code", "ANLU");
		
		Map result = impl.findList(condition);
		assertNotNull(result);
		logger.info("findList() result.get(\"totalRow\")={}", result.get("totalRow"));
		logger.info("findList() result.get(\"bizList\")={}", result.get("bizList"));
	}
	
	/**
	 * findDetail() test
	 * @throws Exception
	 */
	@Test
	public void findDetail() throws Exception {
		// parameters
		Map condition = new HashMap();
		condition.put("codecategorykey", "3039A");
		condition.put("code", "ANLU");
	
		CodeVo result = impl.findDetail(condition);
		logger.info("findDetail() result={}", result);
	}

}
