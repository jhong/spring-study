package net.study.editor;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Map;

import net.study.common.BizCondition;
import net.study.common.BizConst;
import net.study.common.LoginSession;

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
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;


/**  
 * <pre>
 * 에디터 Controller Test
 * </pre>
 
 * @version 2014.05.12
 * @author 김지홍 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( locations = { "classpath*:test/*Context.xml" } )
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class EditorControllerTest {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private MockHttpServletRequest request; 
    private MockHttpServletResponse response; 

    @Autowired
    private RequestMappingHandlerAdapter handlerAdapter;

    @Autowired
    private RequestMappingHandlerMapping handlerMapping;
	
    @Autowired
    private EditorController controller;
	
	@Before
	public void setUp() throws Exception {
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		
		assertNotNull(controller);
		
		// login
		request.setAttribute("LoginSession", new LoginSession());
	}

	/**
	 * viewList() test
	 * @throws Exception
	 */
	@Test
	public void viewList() throws Exception {
		
		// parameters
		request.setRequestURI("/editor.do");
		request.setParameter("command", "viewList");
		
		ModelAndView mav = new AnnotationMethodHandlerAdapter().handle(request, response, controller);
		assertThat(mav.getViewName(), is("list/editor/editor_list.tiles"));
	}

	/**
	 * findList() test
	 * @throws Exception
	 */
	@Test
	public void findList() throws Exception {

		AnnotationMethodHandlerAdapter handlerAdapter = new AnnotationMethodHandlerAdapter();
        HttpMessageConverter[] messageConverters = {new MappingJacksonHttpMessageConverter()};
        handlerAdapter.setMessageConverters(messageConverters);
		
		// parameters
		request.setRequestURI("/editor.do");
		request.setParameter("command", "findList");
		
		handlerAdapter.handle(request, response, controller);
		logger.debug("findList() response={}", response.getContentAsString());
	}

	/**
	 * entry() test
	 * @throws Exception
	 */
	@Test
	public void entry() throws Exception {
		
		// parameters
		request.setRequestURI("/editor.do");
		request.setParameter("command", "entry");
		
		ModelAndView mav = new AnnotationMethodHandlerAdapter().handle(request, response, controller);
		assertThat(mav.getViewName(), is("edit/editor/editor_edit.tiles"));
		
		Map resultModelMap = mav.getModelMap();
		Object editorVo = resultModelMap.get("editorVo");
		logger.debug("entry() editorVo={}", editorVo);

		BizCondition condition = (BizCondition)resultModelMap.get("condition");
		assertNotNull(condition);
		assertThat(condition.get("dbmode")+"", is(BizConst.CODE_DB_INSERT));
	}

	/**
	 * regist() test
	 * @throws Exception
	 */
	@Test
	public void regist() throws Exception {
		
		// parameters
		request.setRequestURI("/editor.do");
		request.setParameter("command", "regist");
		request.setParameter("bbskey", "1");
		request.setParameter("bbstype", "N"); // 공지사항
		request.setParameter("title", "TITLE");
		request.setParameter("contents", "CONTENTS");
		request.setParameter("attachstr", 
				"[{\"filekey\":\"0485faa0-1efd-4835-af85-afa1999621f0\",\"deletedMark\":false}" +
				",{\"filekey\":\"67d8d4ce-26a5-4cb5-ae5c-131bbe2ee67f\",\"deletedMark\":false}" +
				",{\"filekey\":\"da949ae8-c533-44b0-bb86-de23624e50e4\",\"deletedMark\":false}" +
				",{\"filekey\":\"f03d318e-d67d-4244-9442-4c5d1591c283\",\"deletedMark\":false}]");

		request.setAttribute(DispatcherServlet.OUTPUT_FLASH_MAP_ATTRIBUTE, new FlashMap()); // for RedirectAttributes
		Object handler = handlerMapping.getHandler(request).getHandler();
		ModelAndView mav = handlerAdapter.handle(request, response, handler);
		assertThat(mav.getViewName().indexOf("redirect:/editor.do?command=findDetail&selected_key="), is(0));

		FlashMap resultFlashMap = (FlashMap)request.getAttribute(DispatcherServlet.OUTPUT_FLASH_MAP_ATTRIBUTE);
		Object editorVo = resultFlashMap.get("editorVo");
		assertNotNull(editorVo);
	}
	
	/**
	 * modify() test
	 * @throws Exception
	 */
	@Test
	public void modify() throws Exception {
		// parameters
		request.setRequestURI("/editor.do");
		request.setParameter("command", "modify");
		request.setParameter("bbskey", "97726025-48bd-4dbc-bb71-817323303fce");
		request.setParameter("bbstype", "N"); // 공지사항
		request.setParameter("title", "TITLE");
		request.setParameter("contents", "CONTENTS");

		ModelAndView mav = new AnnotationMethodHandlerAdapter().handle(request, response, controller);
		assertThat(mav.getViewName(), is("edit/editor/editor_edit.tiles"));

		Map resultModelMap = mav.getModelMap();
		Object editorVo = resultModelMap.get("editorVo");
		logger.debug("modify() editorVo={}", editorVo);
//		assertNotNull(editorVo);
		assertNotNull(resultModelMap.get("condition"));
	}

	/**
	 * modify() test
	 * @throws Exception
	 */
	@Test
	public void modify3() throws Exception {
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

		ModelMap model = new ModelMap();


		// 직접 controller 의 메서드 호출함
		String result = controller.modify(request, vo, null, model, null);
//		assertViewName(mav, "view/editor/EDITOR_edit.tiles");
		
//		Map resultModelMap = mav.getModelMap();
//		BindingResult bindingResult = (BindingResult)resultModelMap.get("org.springframework.validation.BindingResult.editor");
//		assertTrue(bindingResult.getErrorCount() == 0); // validation 통과!!
	}

	/**
	 * delete() test
	 * @throws Exception
	 */
	@Test
	public void delete() throws Exception {

		AnnotationMethodHandlerAdapter handlerAdapter = new AnnotationMethodHandlerAdapter();
        HttpMessageConverter[] messageConverters = {new MappingJacksonHttpMessageConverter()};
        handlerAdapter.setMessageConverters(messageConverters);

		// parameters
		request.setRequestURI("/editor.do");
		request.setParameter("command", "delete");

		String selectedData = "[{\"bbskey\":\"bbskey\"}]";
		request.setParameter("selectedData", selectedData);

		handlerAdapter.handle(request, response, controller);
		logger.debug("delete() response={}", response.getContentAsString());
	}
	
	/**
	 * findDetail() test
	 * @throws Exception
	 */
	@Test
	public void findDetail() throws Exception {
		
		// parameters
		request.setRequestURI("/editor.do");
		request.setParameter("command", "findDetail");
		request.setParameter("selected_key", "97726025-48bd-4dbc-bb71-817323303fce");
		
		ModelAndView mav = new AnnotationMethodHandlerAdapter().handle(request, response, controller);
		assertThat(mav.getViewName(), is("edit/editor/editor_edit.tiles"));
		
		Map resultModelMap = mav.getModelMap();
		Object editorVo = resultModelMap.get("editorVo");
		logger.debug("findDetail() editorVo={}", editorVo);
		assertNotNull(resultModelMap.get("condition"));
	}

	/**
	 * view() test
	 * @throws Exception
	 */
	@Test
	public void view() throws Exception {
		
		// parameters
		request.setRequestURI("/editor.do");
		request.setParameter("command", "view");
		request.setParameter("selected_key", "97726025-48bd-4dbc-bb71-817323303fce");
		
		ModelAndView mav = new AnnotationMethodHandlerAdapter().handle(request, response, controller);
		assertThat(mav.getViewName(), is("edit/editor/editor_view.tiles"));
		
		Map resultModelMap = mav.getModelMap();
		Object editorVo = resultModelMap.get("editorVo");
		logger.debug("view() editorVo={}", editorVo);
		assertNotNull(resultModelMap.get("condition"));
	}

}
