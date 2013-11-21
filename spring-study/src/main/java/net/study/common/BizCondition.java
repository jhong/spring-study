package net.study.common;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.WeakHashMap;

import javax.servlet.http.HttpServletRequest;

import net.study.util.DateUtil;
import net.study.util.StringUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * <pre>
 * 검색조건
 * </pre>
 *
 * @version 2008. 09. 15
 * @author 김창교
 */
public class BizCondition extends WeakHashMap {

	/**
	* logger
	*/
	private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	* NAME_DATEFIELD
	*/
	public static final String NAME_DATEFIELD = "searchDateType";
	/**
	* NAME_STARTDATE
	*/
	public static final String NAME_STARTDATE = "searchStartDate";
	/**
	* NAME_ENDDATE
	*/
	public static final String NAME_ENDDATE = "searchEndDate";

	/**
	 * NAME_DEFAULT_SEARCH_PERIOD
	 */
	public static final String NAME_DEFAULT_DATE_CONDITION = "defaultDateCondition";
	
	/**
	* NAME_EMPTYTYPE
	*/
	public static final String NAME_EMPTYTYPE = "emptyType";

	/**
	* NAME_PAGE
	*/
	public static final String NAME_PAGE = "page";

	/**
	* NAME_ITEM_PAGE
	*/
	public static final String NAME_ITEM_PAGE = "itemPage";

	/**
	* NAME_TOTAL_PAGE
	*/
	public static final String NAME_TOTAL_PAGE = "totalPage";

	/**
	* NAME_TOTAL_ROW
	*/
	public static final String NAME_TOTAL_ROW = "totalRow";

	/**
	 * NAME_FIRST_RIW_INDEX
	 * by jhong (2012.12.28)
	 */
	public static final String NAME_FIRST_ROW_INDEX = "firstRowIndex";
	
	/**
	 * NAME_ROW_COUNT_PER_PAGE
	 * by jhong (2012.12.28)
	 */
	public static final String NAME_ROW_COUNT_PER_PAGE = "rowCountPerPage";
	
	
	/**
	* NAME_STATUS
	*/
	public static final String NAME_STATUS = "searchStatus";
	
	/**
	 * NAME_STATUS_ARR
	 * added by jhong (2013.05.02)
	 */
	public static final String NAME_STATUS_ARR = "searchStatusArr";

	/**
	* NAMEMAP_CONDITION_FIELD
	*/
	public static final String NAMEMAP_CONDITION_FIELD = "searchDateType, searchStartDate, searchEndDate, searchDate"
		+ ", searchType1, searchValue1, searchText1"
		+ ", searchType2, searchValue2, searchText2"
		+ ", searchType3, searchValue3, searchText3"
		+ ", searchRadio, selected_key"
		+ ", sortType, sortValue, searchStatus, page, itemPage, objName"
		+ ", firstRowIndex, rowCountPerPage" // by jhong (2012.12.28)
		+ ", menuId" // by jhong (2013.02.06)
		+ ", dbmode" // by jhong (2013.06.26)
		+ ", newDocumentNo, command";

	/**
	* NAME_NEW_DOCUMENT_NO
	*/
	public static final String NAME_NEW_DOCUMENT_NO = "newDocumentNo";

	/**
	* NAME_SELECTED_KEY
	*/
	public static final String NAME_SELECTED_KEY = "selected_key";
	/**
	* DELIMITER_KEY_FIELD
	*/
	public static final String DELIMITER_KEY_FIELD = "^|^";

	/**
	 * NAME_MENU_ID
	 */
	public static final String NAME_MENU_ID = "menuId";

	/**
	* loginSession
	*/
	private LoginSession loginSession;
	

	/**
	 * 생성자
	 *
	 */
	public BizCondition() {
		this.put(NAME_PAGE, 1);
		this.put(NAME_ROW_COUNT_PER_PAGE, 10); // by jhong (2012.12.28)
		this.put(NAME_FIRST_ROW_INDEX, 0); // by jhong (2013.01.16)
	}

	/**
	 * 생성자
	 *
	 * @param request
	 * @throws Exception
	 */
	public BizCondition(HttpServletRequest request) throws Exception {
//		this.condition = new HashMap();
		this.put(NAME_PAGE, 1);
		this.put(NAME_ROW_COUNT_PER_PAGE, 10); // by jhong (2012.12.28)
		this.put(NAME_FIRST_ROW_INDEX, 0); // by jhong (2013.01.16)
		this.setCondition(request);
	}

	/**
	 * 생성자
	 *
	 * @param request
	 * @throws Exception
	 */
	public BizCondition(HttpServletRequest request, String defaultDateConditionKey) throws Exception {
//		this.condition = new HashMap();
		this.put(NAME_PAGE, 1);
		this.put(NAME_ROW_COUNT_PER_PAGE, 10); // by jhong (2012.12.28)
		this.put(NAME_FIRST_ROW_INDEX, 0); // by jhong (2013.01.16)
		this.put(NAME_DEFAULT_DATE_CONDITION, defaultDateConditionKey); // by jhong (2013.02.06)
		this.setCondition(request);
	}

	/**
	 * <pre>
	 * 해당 파라미터가 검색조건인지 확인
	 * </pre>
	 *
	 * @param name
	 * @return
	 */
	public static boolean isConditionField(String name) {
		if (NAMEMAP_CONDITION_FIELD.indexOf(name) >= 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @return Returns the loginSession.
	 */
	public LoginSession getLoginSession() {
		return this.loginSession;
	}

	/**
	 * @param loginSession The loginSession to set.
	 */
	public void setLoginSession(LoginSession loginSession) {
		this.loginSession = loginSession;
	}

	/**
	 * <pre>
	 * 조건을 Map으로 리턴
	 * </pre>
	 *
	 * @return
	 */
	public Map getConditionMap() {
		return this;
	}

	/**
	 * <pre>
	 * 조건 Map setter
	 * </pre>
	 *
	 * @param condition
	 */
	public void setConditionMap(Map condition) throws Exception {
		this.putAll(condition);
		this.reviseCondition();
	}

	/**
	 * <pre>
	 * condition 정보 설정
	 * 검색시작일자와 검색종료 일자는 String type를 java.util.Date 타입으로 변환함.
	 * </pre>
	 *
	 * @param request
	 * @throws Exception
	 */
	public void setCondition(HttpServletRequest request) throws Exception {
		logger.info("setCondition() start...");
		
		// redirect 하는 경우는 request "INPUT_FLASH_MAP" 에 "condition" 객체가 존재함!! by jhong (2013.05.15)
    	Map inputFlashMap = (Map)request.getAttribute("org.springframework.web.servlet.DispatcherServlet.INPUT_FLASH_MAP");
		if (inputFlashMap != null) {
			BizCondition tmp = (BizCondition)inputFlashMap.get("condition");
			if (tmp != null) {
				logger.info("setCondition() tmp={}", tmp);
				Map param = tmp.getConditionMap();
				if (param != null) {
					Iterator iter = param.keySet().iterator();
					while (iter.hasNext()) {
						String name = (String)iter.next();
						if (isConditionField(name)) {
							this.put(name, tmp.get(name));
						}
					}
				}
			}
		}
			
		// 일반적인 경우 : request 에서 parameters 꺼내어 세팅
		Enumeration names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String name = (String)names.nextElement();
			if (isConditionField(name)) {
				this.put(name, request.getParameter(name));
			}
		}
		
		if (request.getSession().getAttribute("loginSession") != null) {
			this.loginSession = (LoginSession)request.getSession().getAttribute("loginSession");
		}
		
		this.reviseCondition();
	}

//	/**
//	 * <pre>
//	 * json을 통해 받은 조건정보 처리
//	 * </pre>
//	 *
//	 * @param request
//	 * @param jsonObj
//	 * @throws Exception
//	 */
//	public void setCondition(HttpServletRequest request, JSONObject jsonObj) throws Exception {
//		if (jsonObj == null) {
//			return;
//		}
//		Iterator itr = jsonObj.keys();
//		while(itr.hasNext()) {
//			String name = (String)itr.next();
//			if (isConditionField(name)) {
//				this.put(name, jsonObj.getString(name));
//			}
//		}
//		if (request.getSession().getAttribute("loginSession") != null) {
//			this.loginSession = (LoginSession)request.getSession().getAttribute("loginSession");
//		}
//		this.reviseCondition();
//	}

	/**
	 * <pre>
	 * 설정된 조건정보중 string데이터를 date 및 number type으로 변환하거나 초기값을 설정한다.
	 * </pre>
	 *
	 * @throws Exception
	 */
	private void reviseCondition() throws Exception {
		logger.info("reviseCondition() start... condition={}", this+", "+this.isEmpty(this.NAME_STARTDATE)+", "+this.isEmpty(this.NAME_ENDDATE));
		if (this.isEmpty(this.NAME_STARTDATE) && this.isEmpty(this.NAME_ENDDATE)) {
			this.defaultDateCondition();
			this.put(this.NAME_EMPTYTYPE, "none");
		} else if (this.isEmpty(this.NAME_STARTDATE) && !this.isEmpty(this.NAME_ENDDATE)) {
			this.put(this.NAME_EMPTYTYPE, this.NAME_STARTDATE);
			String tmpDate = (String)this.get(this.NAME_ENDDATE);
			tmpDate += " 23:59:59.0";
			this.put(this.NAME_ENDDATE,
					DateUtil.parseDate(tmpDate, DateUtil.PATTERN_STRING_TO_DATE));
		} else if (!this.isEmpty(this.NAME_STARTDATE) && this.isEmpty(this.NAME_ENDDATE)) {
			this.put(this.NAME_EMPTYTYPE, this.NAME_ENDDATE);
			this.put(this.NAME_STARTDATE,
					DateUtil.parseDate((String)this.get(this.NAME_STARTDATE), DateUtil.PATTERN_STRING_TO_DATE));
		} else {
			this.put(this.NAME_EMPTYTYPE, "none");
			if (!this.isEmpty(this.NAME_STARTDATE)) {
				logger.info("reviseCondition() this.get(this.NAME_STARTDATE)={}", this.get(this.NAME_STARTDATE));
				
				// Date 객체 vs. String 객체 처리 방법 분리 by jhong (2013.05.15)
				Date startDate = null;
				if (this.get(this.NAME_STARTDATE) instanceof Date) {
					startDate = (Date)this.get(this.NAME_STARTDATE);

				} else {
					// yyyy-MMdd 등의 값에 대비하여 전처리 수행 by jhong (2013.06.24)
					String tmp = (String)this.get(this.NAME_STARTDATE);
					tmp = tmp.replaceAll("\\-", "").replaceAll("\\s", ""); // -, 공백 제거
					if (tmp.length() == 8) this.put(this.NAME_STARTDATE, tmp); // 8자리인 경우 사용함
					
					startDate = DateUtil.parseDate((String)this.get(this.NAME_STARTDATE), DateUtil.PATTERN_STRING_TO_DATE);
				}
				this.put(this.NAME_STARTDATE, startDate);
			}
			if (!this.isEmpty(this.NAME_ENDDATE)) {
				
				// Date 객체 vs. String 객체 처리 방법 분리 by jhong (2013.05.15)
				Date endDate = null;
				if (this.get(this.NAME_ENDDATE) instanceof Date) {
					endDate = (Date)this.get(this.NAME_ENDDATE);
					
					Calendar cal = Calendar.getInstance();
					cal.setTime(endDate);
					cal.set(Calendar.HOUR_OF_DAY, 23);
					cal.set(Calendar.MINUTE, 23);
					cal.set(Calendar.SECOND, 59);
					
					endDate = cal.getTime();
					
				} else {
					// yyyy-MMdd 등의 값에 대비하여 전처리 수행 by jhong (2013.06.24)
					String tmp = (String)this.get(this.NAME_ENDDATE);
					tmp = tmp.replaceAll("\\-", "").replaceAll("\\s", ""); // -, 공백 제거
					if (tmp.length() == 8) this.put(this.NAME_ENDDATE, tmp); // 8자리인 경우 사용함

					String tmpDate = (String)this.get(this.NAME_ENDDATE);
					tmpDate += " 23:59:59.0";
					endDate = DateUtil.parseDate(tmpDate, DateUtil.PATTERN_STRING_TO_DATE);
				}
				this.put(this.NAME_ENDDATE, endDate);
			}
		}

		if (!this.isEmpty(this.NAME_PAGE) && this.get(this.NAME_PAGE) instanceof String) {
			//this.put(this.NAME_PAGE, new Integer((String)this.get(this.NAME_PAGE)));

			// page 에 string 값이 들어간 경우 (NumberFormatException 발생하여 수정함 by jhong (2013.10.25)
			// StringUtil.getInteger() 에서 exception 발생시 default 값 null 반환됨
			this.put(this.NAME_PAGE, StringUtil.getInteger(this, this.NAME_PAGE));
		}

		if (!this.isEmpty(this.NAME_ITEM_PAGE) && this.get(this.NAME_ITEM_PAGE) instanceof String) {
			this.put(this.NAME_ITEM_PAGE, new Integer((String)this.get(this.NAME_ITEM_PAGE)));
		}

		if (!this.isEmpty(this.NAME_SELECTED_KEY)) {
			List keyList = StringUtil.splitToList(
					this.getString(this.NAME_SELECTED_KEY), this.DELIMITER_KEY_FIELD);
			this.put(this.NAME_SELECTED_KEY, keyList);
		}

		if (!this.isEmpty(this.NAME_NEW_DOCUMENT_NO)) {
			this.put(this.NAME_NEW_DOCUMENT_NO, (String)this.get(this.NAME_NEW_DOCUMENT_NO));
		}

	}

	/**
	 * <pre>
	 * 초기 날짜 정보를 설정
	 * </pre>
	 *
	 * @throws Exception
	 */
	public void defaultDateCondition() throws Exception {
//		String conditionDateDefault = Properties.get("condition.date.default");
		String conditionDateDefault = "monthperiod1week";
		if (!this.isEmpty(NAME_DEFAULT_DATE_CONDITION)) conditionDateDefault = this.getString(NAME_DEFAULT_DATE_CONDITION); // 기본 검색 기간 설정시 세팅됨 by jhong (2013.02.06)
		logger.info("defaultDateCondition() conditionDateDefault={}", conditionDateDefault);
	
		if ("month".equals(conditionDateDefault)) {
			this.put(this.NAME_STARTDATE, DateUtil.getFirstDayCurrent());
			this.put(this.NAME_ENDDATE, DateUtil.getLastDayCurrent());
			
		} else if ("day".equals(conditionDateDefault)) {
			this.put(this.NAME_STARTDATE, DateUtil.getDayCurrent());
//			this.put(this.NAME_ENDDATE, DateUtil.getAddDayCurrent(Properties.getInt("condition.date.period")));
			this.put(this.NAME_ENDDATE, DateUtil.getAddDayCurrent(5));
			
		} else if ("monthperiod".equals(conditionDateDefault)) {
			this.put(this.NAME_STARTDATE, DateUtil.getDayPreMonth());
			this.put(this.NAME_ENDDATE, DateUtil.getDayCurrent());
			
		} else if("year".equals(conditionDateDefault)) {
			this.put(this.NAME_STARTDATE, DateUtil.getFirstDayCurrentYear());
			this.put(this.NAME_ENDDATE, DateUtil.getDayCurrent());
			
		} else if("yearperiod".equals(conditionDateDefault)) {
			this.put(this.NAME_STARTDATE, DateUtil.getDayPreYear());
			this.put(this.NAME_ENDDATE, DateUtil.getDayCurrent());
			
		} else if ("monthperiod2".equals(conditionDateDefault)) {
			this.put(this.NAME_STARTDATE, DateUtil.getDayPreMonth());
			this.put(this.NAME_ENDDATE, DateUtil.getDayNextMonth());

		} else if ("monthperiod3".equals(conditionDateDefault)) { // 전자문서 수신문서에 사용
			this.put(this.NAME_STARTDATE, DateUtil.getDayAddMonth(-3));
			this.put(this.NAME_ENDDATE, DateUtil.getDayCurrent());
			
		} else if ("monthperiod3to1".equals(conditionDateDefault)) {
			this.put(this.NAME_STARTDATE, DateUtil.getDayAddMonth(-3));
			this.put(this.NAME_ENDDATE, DateUtil.getDayAddMonth(1));
			
		}else if("monthperiod1week".equals(conditionDateDefault)) {
			this.put(this.NAME_STARTDATE, DateUtil.getDayAddDay(-7));
			this.put(this.NAME_ENDDATE, DateUtil.getDayAddMonth(0));
		}
		logger.info("defaultDateCondition() conditionDateDefault end...");
	}

	/**
	 * <pre>
	 * 조건 설정
	 * </pre>
	 *
	 * @param key
	 * @param value
	 */
	public void setString (String key, String value) {
		this.put(key, value);
	}

	/**
	 * <pre>
	 * 조건 조회
	 * </pre>
	 *
	 * @param key
	 * @return
	 */
	public String getString (String key) {
		if (this.containsKey(key)) {
			if (this.NAME_STARTDATE.equals(key) || this.NAME_ENDDATE.equals(key)){
				if (this.get(key) != null) {
					//return DateFormatUtils.format((Date)this.get(key), DateUtil.PATTERN_DATE_TO_STRING);
					return DateFormatUtils.format((Date)this.get(key), DateUtil.PATTERN_DATE_TO_STRING_2); // by jhong (2013.01.14)
				} else {
					return "";
				}
			} else {
				return String.valueOf(this.get(key));
			}
		} else if (this.get(key) instanceof String) {
			return (String)this.get(key);
		} else {
			return "";
		}
	}

	/**
	 * <pre>
	 * Date를 format에 맞추어 문자열로 변환하여 리턴
	 * </pre>
	 *
	 * @param key
	 * @return
	 */
	public String getFormatedDate (String key) {
		if (this.get(key) == null) return "";

		return DateFormatUtils.format(
				(Date)this.get(key), DateUtil.PATTERN_DATE_TO_STRING);
	}

	/**
	 * <pre>
	 * condition에 값 유무 리턴
	 * </pre>
	 *
	 * @param key
	 * @return
	 */
	public boolean isEmpty (String key) {
		if (this.containsKey(key)) {
			Object value = this.get(key);
			if (value instanceof String) {
				return StringUtils.isEmpty((String)value);
			} else if (value == null) {
				return true;
			}
		} else {
			return true;
		}

		return false;
	}

	/**
	 * <pre>
	 * 사용자가 목록에서 선택한 key 값을 List로 리턴
	 * </pre>
	 *
	 * @return
	 */
	public List getSelectedKeyList() {
		if (!this.containsKey(this.NAME_SELECTED_KEY) || this.get(this.NAME_SELECTED_KEY) == null
				|| StringUtil.isEmpty(this.getString(this.NAME_SELECTED_KEY))) { // empty string 조건 추가  by jhong (2013.06.03)
			return new ArrayList();
		} else {
			return (List)this.get(this.NAME_SELECTED_KEY);
		}
	}

	/**
	 * <pre>
	 * 사용자가 목록에서 선택한 key 값을  입력된 index을 기준으로 조회하여 리턴
	 * </pre>
	 *
	 * @param index
	 * @return
	 */
	public String getSelectedKey(int index) {
		List keyList = this.getSelectedKeyList();
		if (keyList.size() > index) {
			return (String)keyList.get(index);
		} else {
			return "";
		}
	}

	/**
	 * <pre>
	 * 사용자가 목록에서 선택한 key에서 key field가 한개라고 가정하고
	 * key list에서 첫번째 값을 리턴
	 * </pre>
	 *
	 * @return
	 */
	public String getSelectedKey() {
		return this.getSelectedKey(0);
	}

	/**
	 * <pre>
	 * 사용자가 목록에서 선택한 key 값을 String으로 리턴
	 * by jhong (2013.07.22)
	 * </pre>
	 *
	 * @return
	 */
	public String getSelectedKeyStr() {
		String str = "";
		if (!this.containsKey(this.NAME_SELECTED_KEY) || this.get(this.NAME_SELECTED_KEY) == null
				|| StringUtil.isEmpty(this.getString(this.NAME_SELECTED_KEY))) { // empty string 조건 추가  by jhong (2013.06.03)
			return str;
		} else {
			List list = (List)this.get(this.NAME_SELECTED_KEY);
			for (int i=0; i<list.size(); i++) {
				if (StringUtil.isNotEmpty(str)) str += this.DELIMITER_KEY_FIELD;
				str += list.get(i);
			}
			return str;
		}
	}

	/**
	 * <pre>
	 * 검색시작일자를 빈 일자로 설정
	 * </pre>
	 *
	 */
	public void setEmptyStartDate() {
		this.put(this.NAME_EMPTYTYPE, this.NAME_STARTDATE);
		this.put(NAME_STARTDATE, null);
	}

	/**
	 * <pre>
	 * 검색종료일자를 빈 일자로 설정
	 * </pre>
	 *
	 */
	public void setEmptyEndDate() {
		this.put(this.NAME_EMPTYTYPE, this.NAME_ENDDATE);
		this.put(NAME_ENDDATE, null);
	}

	/**
	 * <pre>
	 * key를 index의 순서에 추가
	 * </pre>
	 *
	 * @param index
	 * @param key
	 */
	public void setSelectedKey(int index, String key) {
		List keylist = null;
		if (!this.containsKey(this.NAME_SELECTED_KEY) || this.get(this.NAME_SELECTED_KEY) == null) {
			keylist = new ArrayList();
			this.put(this.NAME_SELECTED_KEY, keylist);
		} else {
			keylist = (List)this.get(this.NAME_SELECTED_KEY);
		}

		keylist.add(index, key);
	}

	/**
	 * <pre>
	 * key를 0번 인덱스로 추가
	 * </pre>
	 *
	 * @param key
	 */
	public void setSelectedKey(String key) {
		this.setSelectedKey(0, key);
	}

	/**
	 * <pre>
	 * page getter
	 * </pre>
	 *
	 * @return
	 */
	public int getPage() {
		Integer page = (Integer)this.get(NAME_PAGE);
		if (page == null)
			return 1;
		else
			return page.intValue();
	}

	/**
	 * <pre>
	 * page setter
	 * </pre>
	 *
	 * @param page
	 */
	public void setPage(int page) {
		this.put(NAME_PAGE, new Integer(page));
	}

	/**
	 * <pre>
	 * item page getter
	 * </pre>
	 *
	 * @return
	 */
	public int getItemPage() {
		Integer page = (Integer)this.get(NAME_ITEM_PAGE);
		if (page == null)
			return 1;
		else
			return page.intValue();
	}

	/**
	 * <pre>
	 * item page setter
	 * </pre>
	 *
	 * @param page
	 */
	public void setItemPage(int page) {
		this.put(NAME_ITEM_PAGE, new Integer(page));
	}

	/**
	 * <pre>
	 * Total page getter
	 * </pre>
	 *
	 * @return
	 */
	public int getTotalPage() {
		Integer page = (Integer)this.get(NAME_TOTAL_PAGE);
		if (page == null)
			return 1;
		else
			return page.intValue();
	}

	/**
	 * <pre>
	 * Total page setter
	 * </pre>
	 *
	 * @param page
	 */
	public void setTotalPage(int page) {
		this.put(NAME_TOTAL_PAGE, new Integer(page));
	}

	/**
	 * <pre>
	 * Total page setter
	 * </pre>
	 *
	 * @param page
	 */
	public void setTotalPage() {
		int totalRow = this.getTotalRow();
		int rowCountPerPage = this.getRowCountPerPage();
		
		int totalPage = (int)(totalRow / rowCountPerPage);
		if (totalRow % rowCountPerPage > 0) totalPage += 1;
		
		this.put(NAME_TOTAL_PAGE, new Integer(totalPage));
	}

	/**
	 * <pre>
	 * Total row getter
	 * </pre>
	 *
	 * @return
	 */
	public int getTotalRow() {
		Integer page = (Integer)this.get(NAME_TOTAL_ROW);
		if (page == null)
			return 1;
		else
			return page.intValue();
	}

	/**
	 * <pre>
	 * Total row setter
	 * </pre>
	 *
	 * @param page
	 */
	public void setTotalRow(int page) {
		//this.put(NAME_TOTAL_ROW, new Integer(page));
		this.setTotalRow(page, true);
	}

	/**
	 * <pre>
	 * Total row setter
	 * </pre>
	 *
	 * @param page
	 */
	public void setTotalRow(int page, boolean setPageInfo) {
		this.put(NAME_TOTAL_ROW, new Integer(page));
		if (setPageInfo) {
			this.setTotalPage();
			this.setFirstRowIndex((this.getPage()-1) * this.getRowCountPerPage());
		}
	}

	/**
	 * <pre>
	 * status getter
	 * </pre>
	 *
	 * @return
	 */
	public String getStatus() {
		return (String)this.get(NAME_STATUS);
	}

	/**
	 * <pre>
	 * status setter
	 * </pre>
	 *
	 * @param status
	 */
	public void setStatus(String status) { // setString을 setStatus로 변경 by jhong (2012.12.28)
		this.put(NAME_STATUS, status);
	}
	
	/**
	 * <pre>
	 * firstRowIndex getter
	 * </pre>
	 *
	 * @return
	 */
	public int getFirstRowIndex() {
		Integer idx = (Integer)this.get(NAME_FIRST_ROW_INDEX);
		if (idx == null)
			return 0;
		else
			return idx.intValue();
	}

	/**
	 * <pre>
	 * firstRowIndex setter
	 * </pre>
	 *
	 * @param page
	 */
	public void setFirstRowIndex(int idx) {
		this.put(NAME_FIRST_ROW_INDEX, new Integer(idx));
	}

	/**
	 * <pre>
	 * rowCountPerPage getter
	 * </pre>
	 *
	 * @return
	 */
	public int getRowCountPerPage() {
		Object obj = this.get(NAME_ROW_COUNT_PER_PAGE);
		if (obj == null || StringUtil.isEmpty(obj+"")) {
			this.put(NAME_ROW_COUNT_PER_PAGE, "10"); // default value = 10
			obj = 10;
		}
		
		Integer cnt = new Integer(obj+""); // TODO : 형변환 시 에러처리
		if (cnt == null)
			return 10;
		else
			return cnt.intValue();
	}

	/**
	 * <pre>
	 * rowCountPerPage setter
	 * </pre>
	 *
	 * @param page
	 */
	public void setRowCountPerPage(int cnt) {
		this.put(NAME_ROW_COUNT_PER_PAGE, new Integer(cnt));
	}

}
