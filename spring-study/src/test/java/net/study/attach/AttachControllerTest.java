package net.study.attach;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.io.FileInputStream;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;


/**  
 * <pre>
 * 첨부파일 Controller Test
 * </pre>
 
 * @version 2014.05.13
 * @author 김지홍 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( locations = { "classpath*:test/*Context.xml" } )
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class AttachControllerTest {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private MockHttpServletRequest request; 
    private MockHttpServletResponse response; 

    @Autowired
    private RequestMappingHandlerAdapter handlerAdapter;

    @Autowired
    private RequestMappingHandlerMapping handlerMapping;
	
    @Autowired
    private AttachController controller;
	
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
		request.setRequestURI("/attach.do");
		request.setParameter("command", "viewList");
		
		ModelAndView mav = new AnnotationMethodHandlerAdapter().handle(request, response, controller);
		assertThat(mav.getViewName(), is("list/attach/attach_list.tiles"));
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
		request.setRequestURI("/attach.do");
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
		request.setRequestURI("/attach.do");
		request.setParameter("command", "entry");
		
		ModelAndView mav = new AnnotationMethodHandlerAdapter().handle(request, response, controller);
		assertThat(mav.getViewName(), is("edit/attach/attach_edit.tiles"));
		
		Map resultModelMap = mav.getModelMap();
		Object attachVo = resultModelMap.get("attachVo");
		logger.debug("entry() attachVo={}", attachVo);

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
		
		MockMultipartHttpServletRequest request = new MockMultipartHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		request.setAttribute("LoginSession", new LoginSession());
		
		// parameters
		request.setRequestURI("/attach.do");
		request.setParameter("command", "regist");
		request.setParameter("filekey", "1");
		request.setParameter("filename", "FILENAME");

		request.setAttribute(DispatcherServlet.OUTPUT_FLASH_MAP_ATTRIBUTE, new FlashMap()); // for RedirectAttributes
		Object handler = handlerMapping.getHandler(request).getHandler();
		ModelAndView mav = handlerAdapter.handle(request, response, handler);
		assertThat(mav.getViewName().indexOf("redirect:/attach.do?command=findDetail&selected_key="), is(0));

//		FlashMap resultFlashMap = (FlashMap)request.getAttribute(DispatcherServlet.OUTPUT_FLASH_MAP_ATTRIBUTE);
//		Object attachVo = resultFlashMap.get("attachVo");
//		assertNotNull(attachVo);
	}
	
	/**
	 * test regist() - validation 에러 발생!!
	 * @throws Exception
	 */
	@Test
	public void regist2() throws Exception {
		
		MockMultipartHttpServletRequest request = new MockMultipartHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		request.setAttribute("LoginSession", new LoginSession());
		
		// parameters
		request.setRequestURI("/attach.do");
		request.setParameter("command", "regist");
		request.setParameter("filekey", "filekey");
		request.setParameter("filename", "FILENAME");

		request.setAttribute(DispatcherServlet.OUTPUT_FLASH_MAP_ATTRIBUTE, new FlashMap()); // for RedirectAttributes
		Object handler = handlerMapping.getHandler(request).getHandler();
		ModelAndView mav = handlerAdapter.handle(request, response, handler);
		assertThat(mav.getViewName().indexOf("redirect:/attach.do?command=findDetail&selected_key="), is(0));
		
		Map resultFlashMap = (FlashMap)request.getAttribute(DispatcherServlet.OUTPUT_FLASH_MAP_ATTRIBUTE);
		BindingResult bindingResult = (BindingResult)resultFlashMap.get("errors");
	}

	/**
	 * modify() test
	 * @throws Exception
	 */
	@Test
	public void modify() throws Exception {
		
		MockMultipartHttpServletRequest request = new MockMultipartHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		request.setAttribute("LoginSession", new LoginSession());
		
		// parameters
		request.setRequestURI("/attach.do");
		request.setParameter("command", "modify");
		request.setParameter("filekey", "filekey");
		request.setParameter("filename", "FILENAME");

		ModelAndView mav = new AnnotationMethodHandlerAdapter().handle(request, response, controller);
		assertThat(mav.getViewName(), is("edit/attach/attach_edit.tiles"));

//		Map resultModelMap = mav.getModelMap();
//		Object attachVo = resultModelMap.get("attachVo");
//		logger.debug("modify() attachVo={}", attachVo);
//		assertNotNull(attachVo);
//		assertNotNull(resultModelMap.get("condition"));
	}

	/**
	 * modify() test - validation 에러 발생!!
	 * @throws Exception
	 */
	@Test
	public void modify2() throws Exception {

		MockMultipartHttpServletRequest request = new MockMultipartHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		request.setAttribute("LoginSession", new LoginSession());

		// parameters
		request.setRequestURI("/attach.do");
		request.setParameter("command", "modify");
		request.setParameter("filekey", "filekey");
		request.setParameter("filename", "FILENAME");

		ModelAndView mav = new AnnotationMethodHandlerAdapter().handle(request, response, controller);
		assertThat(mav.getViewName(), is("edit/attach/attach_edit.tiles"));
		
		Map resultModelMap = mav.getModelMap();
		BindingResult bindingResult = (BindingResult)resultModelMap.get("org.springframework.validation.BindingResult.attachVo");
		assertNotNull(bindingResult);
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
		request.setRequestURI("/attach.do");
		request.setParameter("command", "delete");

		String selectedData = "[{\"filekey\":\"filekey\"}]";
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
		request.setRequestURI("/attach.do");
		request.setParameter("command", "findDetail");
		request.setParameter("selected_key", "filekey");
		
		ModelAndView mav = new AnnotationMethodHandlerAdapter().handle(request, response, controller);
		assertThat(mav.getViewName(), is("edit/attach/attach_edit.tiles"));
		
		Map resultModelMap = mav.getModelMap();
		Object attachVo = resultModelMap.get("attachVo");
		logger.debug("findDetail() attachVo={}", attachVo);
		assertNotNull(resultModelMap.get("condition"));
	}

}
