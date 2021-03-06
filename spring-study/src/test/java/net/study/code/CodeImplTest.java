package net.study.code;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

import net.study.common.BizCondition;
import net.study.common.Properties;
import net.study.util.DateUtil;
import net.study.util.StringUtil;

import org.dom4j.Document;
import org.dom4j.io.DocumentResult;
import org.dom4j.io.DocumentSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

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
//		condition.put("firstRowIndex", 0);		// 페이징 위한 검색조건
//		condition.put("rowCountPerPage", 10);	// 페이징 위한 검색조건
		
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

	/**
	 * getCodeValue() test
	 * @throws Exception
	 */
	@Test
	public void getCodeValue() throws Exception {
		// parameters
		Map condition = new HashMap();
		condition.put("codecategorykey", "3039A");
		condition.put("code", "ANLU");
	
		String result = impl.getCodeValue(condition);
		logger.info("getCodeValue() result={}", result);
	}

	/**
	 * findListExcel() test
	 * @throws Exception
	 */
	@Test
	public void findListExcel() throws Exception {
		// parameters
		BizCondition condition = new BizCondition();
		condition.put("codecategorykey", "3039A");
		
		List result = impl.findListExcel(condition);
		assertNotNull(result); // smoke test
	}

	/**
	 * findDetailXml() test
	 * @throws Exception
	 */
	@Test
	public void findDetailXml() throws Exception {
		// parameters
		BizCondition condition = new BizCondition();
		condition.put("codecategorykey", "3039A");
		condition.put("code", "ANLU");

		Document result = impl.findDetailXml(condition);
		logger.debug("selectDetailXml() result={}", result==null ? "" : result.asXML());
	}

	/**
	 * printHtml() test
	 * @throws Exception
	 */
	@Test
	public void printHtml() throws Exception {

		BizCondition condition = new BizCondition();
		condition.put("codecategorykey", "3039A");
		condition.put("code", "ANLU");

		Document document = impl.findDetailXml(condition);
		if (document == null) {
			logger.warn("document is null... stop test...");
			return;
		}
		logger.info("printHtml() document={}", document.asXML());
		
		// html 만드는 xslt 파일 준비
		String xsltFilePath = Properties.get("path.report.xsl")+"CODE.xsl";
		File file = ResourceUtils.getFile(xsltFilePath);
		StreamSource xsl = new StreamSource(file);
		
		// html 생성
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer(xsl);
		DocumentSource source = new DocumentSource(document);
        DocumentResult result = new DocumentResult();
        transformer.transform(source, result);
        String htmlSource = result.getDocument().asXML();
        logger.info("printHtml() htmlSource={}", htmlSource);
        
        if (StringUtil.isNotEmpty(htmlSource)) {
			FileOutputStream out = new FileOutputStream(Properties.get("test.excel.upload.path")+"/CODE_"+DateUtil.getNowMillTime()+".html");
			out.write(htmlSource.getBytes());
			out.close();
        }
	}

}
