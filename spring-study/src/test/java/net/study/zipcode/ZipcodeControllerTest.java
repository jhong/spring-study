package net.study.zipcode;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Before;
import org.junit.Ignore;
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
public class ZipcodeControllerTest {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private MockHttpServletRequest request; 
    private MockHttpServletResponse response; 

    @Autowired
    private ZipcodeController controller;
    
	@Before
	public void setUp() throws Exception {
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
	}

    /**
     * searchPopup() test
     * @throws Exception
     */
    @Test
    public void searchPopup() throws Exception {
		// parameters
		request.setRequestURI("/zipcode.do");
		request.setParameter("command", "searchPopup");
		
		ModelAndView mav = new AnnotationMethodHandlerAdapter().handle(request, response, controller);
		assertThat(mav.getViewName(), is("zipcode/zipcode_popup"));
    }

    /**
     * searchPopup() test
     * @throws Exception
     */
    @Test
    public void searchPopup2() throws Exception {
		// parameters
		request.setRequestURI("/zipcode.do");
		request.setParameter("command", "searchPopup");
		request.setParameter("rtnCallBack", "shipperZipcodeCallBack");
		request.setParameter("inputYn", "");
		request.setParameter("zipNo", "123456");
		request.setParameter("jibunAddr", "jibunAddr");
		
		ModelAndView mav = new AnnotationMethodHandlerAdapter().handle(request, response, controller);
		assertThat(mav.getViewName(), is("zipcode/zipcode_popup"));
    }

    /*
     * www.juso.go.kr 로의 연계 테스트
     */
    @Ignore
    public void addrLinkUrl() throws Exception {
    	
		String url = "http://www.juso.go.kr/addrlink/addrLinkUrl.do";
		String query = "?confmKey=" + // TODO: 발급받은 승인키 사용하기
				"&returnUrl=http://localhost:8080/spring-study/zipcode.do?command=searchPopup";
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet requestURL = new HttpGet(url + query);
		logger.info("selectZipList() requestURL="+requestURL.getURI());

		HttpResponse res = httpClient.execute(requestURL);
		BufferedReader br = new BufferedReader(new InputStreamReader((res.getEntity().getContent()), "UTF-8")); // DB character set : UTF-8 !!
		String str;
		String resultStr = "";
		while ((str = br.readLine()) != null) {
			resultStr += str;
		}
		httpClient.getConnectionManager().shutdown();
		
		logger.info("addrLinkUrl() resultStr={}", resultStr); 

    }
}
