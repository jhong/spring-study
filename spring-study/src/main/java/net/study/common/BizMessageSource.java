package net.study.common;

import java.text.MessageFormat;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.util.WebUtils;

/**  
 * <pre>
 * 메시지 리소스 사용을 위한 MessageSource 인터페이스 및 ReloadableResourceBundleMessageSource 클래스의 구현체
 * </pre>
 
 * @version 2013.02.19
 * @author 김지홍 
 */
public class BizMessageSource extends ReloadableResourceBundleMessageSource implements MessageSource {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource;

	/**
	 * getReloadableResourceBundleMessageSource() 
	 * @param reloadableResourceBundleMessageSource - resource MessageSource
	 * @return ReloadableResourceBundleMessageSource
	 */	
	public void setReloadableResourceBundleMessageSource(ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource) {
		this.reloadableResourceBundleMessageSource = reloadableResourceBundleMessageSource;
	}
	
	/**
	 * getReloadableResourceBundleMessageSource() 
	 * @return ReloadableResourceBundleMessageSource
	 */	
	public ReloadableResourceBundleMessageSource getReloadableResourceBundleMessageSource() {
		return reloadableResourceBundleMessageSource;
	}
	
	/**
	 * 정의된 메세지 조회
	 * @param code - 메세지 코드
	 * @return String
	 */	
	public String getBizMessage(String code) {
		return getReloadableResourceBundleMessageSource().getMessage(code, null, Locale.getDefault());
	}

	/**
	 * 정의된 메세지 조회
	 * @param code - 메세지 코드
	 * @param locale
	 * @return String
	 */	
	public String getBizMessage(String code, HttpServletRequest request) throws Exception {
		return getReloadableResourceBundleMessageSource().getMessage(code, null, getLocale(request));
	}

	/**
	 * 정의된 메세지 조회
	 * @param code - 메세지 코드
	 * @param locale
	 * @return String
	 */	
	public String getBizMessage(String code, Locale locale) {
		return getReloadableResourceBundleMessageSource().getMessage(code, null, locale);
	}

	/**
	 * 정의된 메세지 조회
	 * @param code - 메세지 코드
	 * @param args
	 * @return String
	 */	
	public String getBizMessage(String code, Object[] args) {
		String str = getReloadableResourceBundleMessageSource().getMessage(code, null, Locale.getDefault());
		str = MessageFormat.format(str, args);
		return str;
	}

	/**
	 * 정의된 메세지 조회
	 * @param code - 메세지 코드
	 * @param locale
	 * @param args
	 * @return String
	 */	
	public String getBizMessage(String code, Object[] args, Locale locale) {
		String str = getReloadableResourceBundleMessageSource().getMessage(code, null, locale);
		str = MessageFormat.format(str, args);
		return str;
	}

	/**
	 * <pre>
	 * Locale 반환
	 * </pre>
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Locale getLocale(HttpServletRequest request) throws Exception {
		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		logger.info("getLocale() localeResolver={}", localeResolver);
		
		String localeAttributeName = "";
		if (localeResolver instanceof SessionLocaleResolver)
			localeAttributeName = ((SessionLocaleResolver)localeResolver).LOCALE_SESSION_ATTRIBUTE_NAME;
		else if (localeResolver instanceof CookieLocaleResolver)
			localeAttributeName = ((CookieLocaleResolver)localeResolver).DEFAULT_COOKIE_NAME;
		
		logger.info("getLocale() localeAttributeName={}", localeAttributeName);
		
		Locale locale = (Locale) WebUtils.getSessionAttribute(request, localeAttributeName);
		if (locale == null) locale = request.getLocale();
		logger.info("getLocale() locale={}", locale);
		return locale;
	}

}
