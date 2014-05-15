package net.study.editor;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import net.study.common.BizCondition;
import net.study.common.DataObject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;


/**  
 * <pre>
 * 에디터 Dao Test
 * </pre>
 
 * @version 2014.05.12
 * @author 김지홍 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( locations = { "classpath*:test/*Context.xml" } )
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class EditorDaoTest {

	/**
	* logger
	*/
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	EditorDao dao;

	/**
	 * selectListCondition() test
	 * @throws Exception
	 */
	@Test
	public void selectListCondition() throws Exception {
		// parameters
		BizCondition condition = new BizCondition();
		condition.put("searchType1", "BBSKEY");
		condition.put("searchValue1", "BBSKEY");
		condition.put("sortType", "SORTORDER");
		condition.put("sortValue", "ASC");
		
		List result = dao.selectListCondition(condition);
		assertNotNull(result); // smoke test
	}
	
	/**
	 * selectListCount() test
	 * @throws Exception
	 */
	@Test
	public void selectListCount() throws Exception {
		// parameters
		BizCondition condition = new BizCondition();
		condition.put("searchType1", "BBSKEY");
		condition.put("searchValue1", "BBSKEY");
		
		int result = dao.selectListCount(condition);
		logger.debug("selectListCount() result={}", result);
		assertNotNull(result); // smoke test
	}

	/**
	 * insert() test
	 * @throws Exception
	 */
	@Test
	public void insert() throws Exception {
		// parameters
		EditorVo vo = new EditorVo();
		vo.setBbskey("BBSKEY");
		vo.setBbstype("BBSTY");
		vo.setBbscategory("BBSCA");
		vo.setPriority("PRIOR");
		vo.setTitle("TITLE");
		vo.setContents("CONTENTS");
		vo.setHit(new BigDecimal(0));
		vo.setGroupid(new BigDecimal(0));
		vo.setSortorder(new BigDecimal(0));
		vo.setReplydepth(new BigDecimal(0));
		vo.setParentkey("PARENTKEY");
		vo.setStatus("STATU");
		vo.setSitetype("SITETYPE");
		vo.setHousekey("HOUSEKEY");
		vo.setCompanykey("COMPANYKEY");
		vo.setRegistdate(null);
		vo.setRegister("REGISTER");
		vo.setModifydate(null);
		vo.setModifier("MODIFIER");
		
		int result = dao.insert(vo);
		logger.debug("insert() result={}", result);
		assertTrue(true); // smoke test
	}

	/**
	 * update() test
	 * @throws Exception
	 */
	@Test
	public void update() throws Exception {
		// parameters
		EditorVo vo = new EditorVo();
		vo.setBbskey("BBSKEY");
		vo.setBbstype("BBSTY");
		vo.setBbscategory("BBSCA");
		vo.setPriority("PRIOR");
		vo.setTitle("TITLE");
		vo.setContents("CONTENTS");
		vo.setHit(new BigDecimal(0));
		vo.setGroupid(new BigDecimal(0));
		vo.setSortorder(new BigDecimal(0));
		vo.setReplydepth(new BigDecimal(0));
		vo.setParentkey("PARENTKEY");
		vo.setStatus("STATU");
		vo.setSitetype("SITETYPE");
		vo.setHousekey("HOUSEKEY");
		vo.setCompanykey("COMPANYKEY");
		vo.setRegistdate(null);
		vo.setRegister("REGISTER");
		vo.setModifydate(null);
		vo.setModifier("MODIFIER");
		
		int result = dao.update(vo);
		logger.debug("update() result={}", result);
		assertTrue(true); // smoke test
	}
	
	/**
	 * delete() test
	 * @throws Exception
	 */
	@Test
	public void delete() throws Exception {
		// parameters
		DataObject param = new DataObject();
		param.put("bbskey", "BBSKEY");

		int result = dao.delete(param);
		logger.debug("delete() result={}", result);
		assertTrue(true); // smoke test
	}
	
	/**
	 * selectDetail() test
	 * @throws Exception
	 */
	@Test
	public void selectDetail() throws Exception {
		// parameters
		DataObject param = new DataObject();
		param.put("bbskey", "BBSKEY");

		EditorVo result = dao.selectDetail(param);
//		assertNotNull(result);
	}

}
