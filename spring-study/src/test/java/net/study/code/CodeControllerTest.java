package net.study.code;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Map;

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
    
//    @Autowired
//    private RequestMappingHandlerAdapter handlerAdapter;
//
//    @Autowired
//    private RequestMappingHandlerMapping handlerMapping;
    
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
		
		ModelAndView mav = new AnnotationMethodHandlerAdapter().handle(request, response, controller);
		assertThat(mav.getViewName(), is("code/code_list"));
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
		assertThat(mav.getViewName(), is("code/code_edit"));
		
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
		assertThat(mav.getViewName(), is("code/code_edit"));
		
		Map resultModelMap = mav.getModelMap();
		Object codeVo = resultModelMap.get("codeVo");
		logger.debug("entry() codeVo={}", codeVo);
	}

//	/**
//	 * regist() test
//	 * @throws Exception
//	 */
//	@Test
//	public void regist() throws Exception {
//		
//		// parameters
//		request.setRequestURI("/code.do");
//		request.setParameter("command", "regist");
//		request.setParameter("codecategorykey", "1");
//		request.setParameter("code", "1");
//
//		request.setAttribute(DispatcherServlet.OUTPUT_FLASH_MAP_ATTRIBUTE, new FlashMap()); // for RedirectAttributes
//		Object handler = handlerMapping.getHandler(request).getHandler();
//		ModelAndView mav = handlerAdapter.handle(request, response, handler);
//		assertThat(mav.getViewName().indexOf("redirect:/code.do?command=findDetail&selected_key="), is(0));
//
//		FlashMap resultFlashMap = (FlashMap)request.getAttribute(DispatcherServlet.OUTPUT_FLASH_MAP_ATTRIBUTE);
//		Object codeVo = resultFlashMap.get("codeVo");
//		assertNotNull(codeVo);
//	}

}
