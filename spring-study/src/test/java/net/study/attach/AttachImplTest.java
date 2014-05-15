package net.study.attach;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.study.common.BizCondition;
import net.study.common.BizResult;
import net.study.common.DataObject;
import net.study.common.LoginSession;

import org.junit.Before;
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
 * 첨부파일 Implementation Test
 * </pre>
 
 * @version 2014.05.13
 * @author 김지홍 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( locations = { "classpath*:test/*Context.xml" } )
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class AttachImplTest {

	/**
	* logger
	*/
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	AttachImpl impl;

	private BizCondition condition;
	
	@Before
	public void setUp() throws Exception {
		
		condition = new BizCondition();
		condition.setLoginSession(new LoginSession()); // login
	}
	
	/**
	 * viewList() test
	 * @throws Exception
	 */
	@Test
	public void viewList() throws Exception {
		
		BizResult result = impl.viewList(condition);
		assertNotNull(result); // smoke test
	}
	
	/**
	 * findList() test
	 * @throws Exception
	 */
	@Test
	public void findList() throws Exception {
		// parameters
		condition.put("searchType1", "FILEKEY");
		condition.put("searchValue1", "FILEKEY");
		
		BizResult result = impl.findList(condition);
		assertNotNull(result); // smoke test
	}

	/**
	 * findListByRefNo() test
	 * @throws Exception
	 */
	@Test
	public void findListByRefNo() throws Exception {
		// parameters
//		condition.put("searchType1", "REF_NO");
//		condition.put("searchValue1", "REF_NO");
		condition.put("ref_no", "REF_NO");
		
		BizResult result = impl.findListByRefNo(condition);
		assertNotNull(result); // smoke test
	}

	/**
	 * entry() test
	 * @throws Exception
	 */
	@Test
	public void entry() throws Exception {
		
		BizResult result = impl.entry(condition);
		assertNotNull(result.getCondition());
		assertTrue((result.getBizData()+"").indexOf("AttachVo") > 0);
	}

	/**
	 * regist() test
	 * @throws Exception
	 */
	@Test
	public void regist() throws Exception {
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
		
		BizResult result = impl.regist(vo, condition);
		assertNotNull(result.getMessageCode());
	}
	
	/**
	 * modify() test
	 * @throws Exception
	 */
	@Test
	public void modify() throws Exception {
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
		
		BizResult result = impl.modify(vo, condition);
		assertNotNull(result.getMessageCode());
	}

	/**
	 * deleteList() test
	 * @throws Exception
	 */
	@Test
	public void deleteList() throws Exception {
		// parameters
		DataObject param = new DataObject();
		param.put("filekey", "FILEKEY");
		
		List selList = new ArrayList();
		selList.add(param);
		
		BizResult result = impl.deleteList(selList, condition);
		assertNotNull(result.getMessageCode());
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
		
		BizResult result = impl.delete(param, condition);
		assertNotNull(result.getMessageCode());
	}

	/**
	 * findDetail() test
	 * @throws Exception
	 */
	@Test
	public void findDetail() throws Exception {
		// parameters
		condition.setSelectedKey("filekey"); // TODO : PK 항목 확인할 것!!

		BizResult result = impl.findDetail(condition);
		assertNotNull(result);
		if (result.getBizData() != null) assertTrue((result.getBizData()+"").indexOf("AttachVo") > 0);
	}
	
}
