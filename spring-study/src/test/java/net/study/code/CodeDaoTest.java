package net.study.code;

import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.List;
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
public class CodeDaoTest {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	CodeDao dao;

	/**
	 * selectListCount() test
	 * @throws Exception
	 */
	@Test
	public void selectListCount() throws Exception {
		// parameters
		Map condition = new HashMap();
		condition.put("codecategorykey", "3039A");
		
		int result = dao.selectListCount(condition);
		logger.info("getListCount() result={}", result);
		assertNotNull(result); // smoke test
	}

	/**
	 * selectListAll() test
	 * @throws Exception
	 */
	@Test
	public void selectListAll() throws Exception {
		// parameters
		Map condition = new HashMap();
		condition.put("codecategorykey", "3039A");

		List result = dao.selectListAll(condition);
		logger.info("selectListAll() result={}", result);
		assertNotNull(result); // smoke test
	}
}
