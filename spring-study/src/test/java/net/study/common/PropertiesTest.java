package net.study.common;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( locations = { "classpath*:test/*Context.xml" } )
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class PropertiesTest {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * get() test
	 * @throws Exception
	 */
	@Test
	public void get() throws Exception {
		String result = Properties.get("condition.firstRowIndex");
		logger.info("get() result={}", result);
		assertNotNull(result);
	}
	
	/**
	 * getInt() test
	 * @throws Exception
	 */
	@Test
	public void getInt() throws Exception {
		int result = Properties.getInt("condition.rowCountPerPage");
		logger.info("getInt() result={}", result);
		assertNotNull(result);
	}

}
