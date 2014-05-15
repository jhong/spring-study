package net.study.editor;

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
 * 에디터 Implementation Test
 * </pre>
 
 * @version 2014.05.12
 * @author 김지홍 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( locations = { "classpath*:test/*Context.xml" } )
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class EditorImplTest {

	/**
	* logger
	*/
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	EditorImpl impl;

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
		condition.put("searchType1", "BBSKEY");
		condition.put("searchValue1", "BBSKEY");
		
		BizResult result = impl.findList(condition);
		assertNotNull(result); // smoke test

		// key 값이 모두 소문자인지 확인
		List list = result.getBizList();
		boolean isKeyLowerCase = true;
		if (list != null) {
			for (int i=0; i<list.size(); i++) {
				DataObject data = (DataObject)list.get(i);
				Iterator<String> keys = data.keySet().iterator();
				while (keys.hasNext()) {
					String key = keys.next();
					if (key.equals(key.toUpperCase())) {
						isKeyLowerCase = false;
						break;
					}
				}
			}
		}
		assertThat(isKeyLowerCase, is(true));
	}

	/**
	 * entry() test
	 * @throws Exception
	 */
	@Test
	public void entry() throws Exception {
		
		BizResult result = impl.entry(condition);
		assertNotNull(result.getCondition());
		assertTrue((result.getBizData()+"").indexOf("EditorVo") > 0);
	}

	/**
	 * regist() test
	 * @throws Exception
	 */
	@Test
	public void regist() throws Exception {
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
		
		BizResult result = impl.modify(vo, condition);
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
		param.put("bbskey", "BBSKEY");
		
		List selList = new ArrayList();
		selList.add(param);
		
		BizResult result = impl.delete(selList, condition);
		assertNotNull(result.getMessageCode());
	}

	/**
	 * findDetail() test
	 * @throws Exception
	 */
	@Test
	public void findDetail() throws Exception {
		// parameters
		condition.setSelectedKey("97726025-48bd-4dbc-bb71-817323303fce");

		BizResult result = impl.findDetail(condition);
		assertNotNull(result);
		if (result.getBizData() != null) assertTrue((result.getBizData()+"").indexOf("EditorVo") > 0);
	}
	
}
