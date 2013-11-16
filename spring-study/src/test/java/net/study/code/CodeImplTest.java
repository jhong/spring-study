package net.study.code;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
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
//		condition.put("code", "ANLU");
		condition.put("firstRowIndex", 0);		// 페이징 위한 검색조건
		condition.put("rowCountPerPage", 10);	// 페이징 위한 검색조건
		
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

	/**
	 * entry() test
	 * @throws Exception
	 */
	@Test
	public void entry() throws Exception {
		Map condition = new HashMap();
		CodeVo result = impl.entry(condition);
	}

	/**
	 * regist() test
	 * @throws Exception
	 */
	@Test
	public void regist() throws Exception {
		// parameters
		Map condition = new HashMap();
		CodeVo vo = new CodeVo();
		vo.setCodecategorykey("CODECATEGORYKEY");
		vo.setCode("CODE");
		vo.setCodeexplain("CODEEXPLAIN");
		vo.setCodename("CODENAME");
		vo.setCodeengname("CODEENGNAME");
		vo.setStatus("STATU");
		vo.setSortorder(new BigDecimal(0));
		
		CodeVo result = impl.regist(vo, condition);
	}
	
	/**
	 * modify() test
	 * @throws Exception
	 */
	@Test
	public void modify() throws Exception {
		// parameters
		Map condition = new HashMap();
		CodeVo vo = new CodeVo();
		vo.setCodecategorykey("CODECATEGORYKEY");
		vo.setCode("CODE");
		vo.setCodeexplain("CODEEXPLAIN");
		vo.setCodename("CODENAME");
		vo.setCodeengname("CODEENGNAME");
		vo.setStatus("STATU");
		vo.setSortorder(new BigDecimal(0));
		
		CodeVo result = impl.modify(vo, condition);
	}

	/**
	 * delete() test
	 * @throws Exception
	 */
	@Test
	public void delete() throws Exception {
		// parameters
		Map param = new HashMap();
		param.put("codecategorykey", "CODECATEGORYKEY");
		param.put("code", "CODE");
		
		int result = impl.delete(param);
	}

}
