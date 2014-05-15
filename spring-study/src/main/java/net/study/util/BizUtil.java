package net.study.util;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import net.study.common.BizCondition;
import net.study.common.GridResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.util.WebUtils;

public class BizUtil {

	/**
	* logger
	*/
	public static final Logger logger = LoggerFactory.getLogger(BizUtil.class);

	/**
	 * <pre>
	 * Grid Response 생성
	 * </pre>
	 * @param bizResult
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public static GridResponse makeGridResponseData(List bizList, BizCondition condition) throws Exception {
        // grid response
        GridResponse response = new GridResponse();
        if (bizList != null && bizList.size() > 0)
        	response.setData(bizList);
        if (condition != null) {
	        response.setRecords(String.valueOf(condition.getTotalRow())); // total number of records
	        response.setPage(String.valueOf(condition.getPage())); // page
	        response.setTotal(String.valueOf(condition.getTotalPage())); // total pages
        }

        return response;
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
	public static Locale getLocale(HttpServletRequest request) throws Exception {
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
