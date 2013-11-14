package net.study.code;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
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
		
		Map result = impl.findList(condition);
		System.out.println("\n\nfindList() result.get(\"totalRow\")="+result.get("totalRow"));
	}
}
