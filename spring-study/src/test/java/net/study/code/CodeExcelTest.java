package net.study.code;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.study.common.DataObject;
import net.study.common.Properties;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( locations = { "classpath*:test/*Context.xml" } )
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class CodeExcelTest {

	/**
	* logger
	*/
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	CodeExcel excel;

	private MockHttpServletRequest request; 
    private MockHttpServletResponse response; 
	
	@Before
	public void setUp() throws Exception {
		
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
	}

	/**
	 * buildExcelDocument() test
	 * @throws Exception
	 */
	@Test
	public void buildExcelDocument() throws Exception {
		// parameters
		HSSFWorkbook wb = new HSSFWorkbook();

		List resultList = new ArrayList();
		DataObject param = new DataObject();
		param.put("CODECATEGORYKEY", "CODECATEGORYKEY...");
		param.put("CODE", "CODE...");
		param.put("CODEEXPLAIN", "CODEEXPLAIN...");
		param.put("CODENAME", "CODENAME...");
		param.put("CODEENGNAME", "CODEENGNAME...");
		param.put("STATUS", "Y");
		param.put("SORTORDER", new BigDecimal(0));
		
		resultList.add(param);

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("resultList", resultList);

		excel.buildExcelDocument(model, wb, request, response);

		// 엑셀 파일 저장
		FileOutputStream fo = new FileOutputStream(Properties.get("test.excel.upload.path")+"/Code.xls");
		wb.write(fo);
		fo.close();
	}
}

