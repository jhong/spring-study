package net.study.attach;

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
 * 첨부파일 Dao Test
 * </pre>
 
 * @version 2014.05.13
 * @author 김지홍 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( locations = { "classpath*:test/*Context.xml" } )
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class AttachDaoTest {

	/**
	* logger
	*/
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	AttachDao dao;

	/**
	 * selectListCondition() test
	 * @throws Exception
	 */
	@Test
	public void selectListCondition() throws Exception {
		// parameters
		BizCondition condition = new BizCondition();
		condition.put("searchType1", "FILEKEY");
		condition.put("searchValue1", "FILEKEY");
		condition.put("sortType", "FILEKEY");
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
		condition.put("searchType1", "FILEKEY");
		condition.put("searchValue1", "FILEKEY");
		
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
		AttachVo vo = new AttachVo();
		vo.setFilekey("FILEKEY");
		vo.setHousekey("HOUSEKEY");
		vo.setCompanykey("COMPANYKEY");
		vo.setFilename("FILENAME");
		vo.setMimetype("MIMETYPE");
		vo.setCharset("CHARSET");
		vo.setFilesize(new BigDecimal(0));
		vo.setUploaddate(null);
		vo.setFiledesc("FILEDESC");
		vo.setRefNo("REF_NO");
		vo.setRefNo2("REF_NO2");
		vo.setRefNoSeq(new BigDecimal(0));
		vo.setUserid("USERID");
		
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
		AttachVo vo = new AttachVo();
		vo.setFilekey("FILEKEY");
		vo.setHousekey("HOUSEKEY");
		vo.setCompanykey("COMPANYKEY");
		vo.setFilename("FILENAME");
		vo.setMimetype("MIMETYPE");
		vo.setCharset("CHARSET");
		vo.setFilesize(new BigDecimal(0));
		vo.setUploaddate(null);
		vo.setFiledesc("FILEDESC");
		vo.setRefNo("REF_NO");
		vo.setRefNo2("REF_NO2");
		vo.setRefNoSeq(new BigDecimal(0));
		vo.setUserid("USERID");
		
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
		param.put("filekey", "FILEKEY");

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
		param.put("filekey", "FILEKEY");

		AttachVo result = dao.selectDetail(param);
//		assertNotNull(result);
	}

	/**
	 * selectListAll() test
	 * @throws Exception
	 */
	@Test
	public void selectListAll() throws Exception {
		// parameters
		BizCondition condition = new BizCondition();
		condition.put("searchType1", "FILEKEY");
		condition.put("searchValue1", "FILEKEY");
		condition.put("sortType", "FILEKEY");
		condition.put("sortValue", "ASC");

		List<AttachVo> result = dao.selectListAll(condition);
		logger.debug("selectListAll() result={}", result);
	}
	
}
