package net.study.code;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( locations = { "classpath*:test/*Context.xml" } )
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class CodeControllerTest {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private MockHttpServletRequest request; 
    private MockHttpServletResponse response; 

    @Autowired
    private CodeController controller;
    
	@Before
	public void setUp() throws Exception {
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
	}

    /**
     * viewList() test
     * @throws Exception
     */
    @Test
    public void viewList() throws Exception {
		// parameters
		request.setRequestURI("/code.do");
		request.setParameter("command", "viewList");
		request.setParameter("codecategorykey", "3039A");
		request.setParameter("firstRowIndex", "0");		// 페이징 위한 검색조건
		request.setParameter("rowCountPerPage", "10");	// 페이징 위한 검색조건
		
		ModelAndView mav = new AnnotationMethodHandlerAdapter().handle(request, response, controller);
		assertThat(mav.getViewName(), is("list/code/code_list.tiles"));
    }

    /**
     * viewListJson() test
     * @throws Exception
     */
    @Test
    public void viewListJson() throws Exception {
		// parameters
		request.setRequestURI("/code.do");
		request.setParameter("command", "viewListJson");
		request.setParameter("codecategorykey", "3039A");
		request.setParameter("firstRowIndex", "0");		// 페이징 위한 검색조건
		request.setParameter("rowCountPerPage", "10");	// 페이징 위한 검색조건
		
		ModelAndView mav = new AnnotationMethodHandlerAdapter().handle(request, response, controller);
		assertThat(mav.getViewName(), is("list/code/code_list_json.tiles"));
		
		ModelMap modelMap = mav.getModelMap();
		JSONArray jsonArray = (JSONArray)modelMap.get("bizList");
		for (int i=0; jsonArray!=null && i<jsonArray.size(); i++) {
			JSONObject jsonObject = (JSONObject)jsonArray.get(i);
			logger.info("viewListJson() code={}", jsonObject.get("CODE")+", codename="+jsonObject.get("CODENAME")+", jsonObject="+jsonObject);
		}
    }

	/**
	 * findListJson() test
	 * @throws Exception
	 */
	@Test
	public void findListJson() throws Exception {
		
		AnnotationMethodHandlerAdapter handlerAdapter = new AnnotationMethodHandlerAdapter();

		// parameters
		request.setRequestURI("/code.do");
		request.setParameter("command", "findListJson");
		request.setParameter("codecategorykey", "3039A");
		request.setParameter("firstRowIndex", "0");		// 페이징 위한 검색조건
		request.setParameter("rowCountPerPage", "10");	// 페이징 위한 검색조건

		handlerAdapter.handle(request, response, controller);
		logger.info("findListJson() response={}", response.getContentAsString());

	}

    /**
     * viewListGrid() test
     * @throws Exception
     */
    @Test
    public void viewListGrid() throws Exception {
		// parameters
		request.setRequestURI("/code.do");
		request.setParameter("command", "viewListGrid");
		request.setParameter("codecategorykey", "3039A");
		request.setParameter("firstRowIndex", "0");		// 페이징 위한 검색조건
		request.setParameter("rowCountPerPage", "10");	// 페이징 위한 검색조건
		
		ModelAndView mav = new AnnotationMethodHandlerAdapter().handle(request, response, controller);
		assertThat(mav.getViewName(), is("list/code/code_list_grid.tiles"));
    }

	/**
	 * findListGrid() test
	 * @throws Exception
	 */
	@Test
	public void findListGrid() throws Exception {
		
		AnnotationMethodHandlerAdapter handlerAdapter = new AnnotationMethodHandlerAdapter();
        HttpMessageConverter[] messageConverters = {new MappingJacksonHttpMessageConverter()};
        handlerAdapter.setMessageConverters(messageConverters);

		// parameters
		request.setRequestURI("/code.do");
		request.setParameter("command", "findListGrid");
		request.setParameter("codecategorykey", "3039A");
		request.setParameter("firstRowIndex", "0");		// 페이징 위한 검색조건
		request.setParameter("rowCountPerPage", "10");	// 페이징 위한 검색조건

		handlerAdapter.handle(request, response, controller);
		logger.info("findListGrid() response={}", response.getContentAsString());
	}

	/**
	 * findDetail() test
	 * @throws Exception
	 */
	@Test
	public void findDetail() throws Exception {
		
		// parameters
		request.setRequestURI("/code.do");
		request.setParameter("command", "findDetail");
		request.setParameter("codecategorykey", "3039A");
		request.setParameter("code", "ANLU");
		
		ModelAndView mav = new AnnotationMethodHandlerAdapter().handle(request, response, controller);
		assertThat(mav.getViewName(), is("edit/code/code_edit.tiles"));
		
		Map resultModelMap = mav.getModelMap();
		Object codeVo = resultModelMap.get("codeVo");
		logger.info("findDetail() codeVo={}", codeVo);
	}

	/**
	 * entry() test
	 * @throws Exception
	 */
	@Test
	public void entry() throws Exception {
		
		// parameters
		request.setRequestURI("/code.do");
		request.setParameter("command", "entry");
		
		ModelAndView mav = new AnnotationMethodHandlerAdapter().handle(request, response, controller);
		assertThat(mav.getViewName(), is("edit/code/code_edit.tiles"));
		
		Map resultModelMap = mav.getModelMap();
		Object codeVo = resultModelMap.get("codeVo");
		logger.debug("entry() codeVo={}", codeVo);
	}

	/**
	 * regist() test
	 * @throws Exception
	 */
	@Test
	public void regist() throws Exception {
		
		// parameters
		request.setRequestURI("/code.do");
		request.setParameter("command", "regist");
		request.setParameter("codecategorykey", "1");
		request.setParameter("code", "1");

		ModelAndView mav = new AnnotationMethodHandlerAdapter().handle(request, response, controller);
		assertThat(mav.getViewName(), is("edit/code/code_edit.tiles"));
	}

	/**
	 * modify() test
	 * @throws Exception
	 */
	@Test
	public void modify() throws Exception {
		
		// parameters
		request.setRequestURI("/code.do");
		request.setParameter("command", "modify");
		request.setParameter("codecategorykey", "1");
		request.setParameter("code", "1");

		ModelAndView mav = new AnnotationMethodHandlerAdapter().handle(request, response, controller);
		assertThat(mav.getViewName(), is("edit/code/code_edit.tiles"));
	}

	/**
	 * delete() test
	 * @throws Exception
	 */
	@Test
	public void delete() throws Exception {
		
		// parameters
		request.setRequestURI("/code.do");
		request.setParameter("command", "delete");
		request.setParameter("codecategorykey", "1");
		request.setParameter("code", "1");

		ModelAndView mav = new AnnotationMethodHandlerAdapter().handle(request, response, controller);
		assertThat(mav.getViewName(), is("list/code/code_list.tiles"));
	}

	/**
	 * getCodeValue() test
	 * @throws Exception
	 */
	@Test
	public void getCodeValue() throws Exception {
		
		AnnotationMethodHandlerAdapter handlerAdapter = new AnnotationMethodHandlerAdapter();

		// parameters
		request.setRequestURI("/code.do");
		request.setParameter("command", "getCodeValue");
		request.setParameter("codecategorykey", "3039A");
		request.setParameter("code", "ANLU");

		handlerAdapter.handle(request, response, controller);
		logger.info("getCodeValue() response={}", response.getContentAsString());

	}

}
