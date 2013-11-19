package net.study.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * Date의 많은 메소드가 사용하지 않을 것을 권장하고 있어, commons lang의 DateUtils를 상속하여
 * 날짜처리에 필요한 Utility를 추가함.
 * 해당 Util에 format에 대한 utility는 추가하지 않으며 commons lang의 DateFormatUtils를 사용해야함.
 * </pre>
 *
 * @version 2008. 08. 21
 * @author 김창교
 */
public class DateUtil extends DateUtils {
	
	private final static Logger logger = LoggerFactory.getLogger(DateUtil.class);

	/**
	* PATERN_STRING_TO_DATE
	*/
	public static String[] PATTERN_STRING_TO_DATE = {"yyyyMMdd", "yyyy.MM.dd", "yyyy-MM-dd", "yyyy/MM/dd", "yyyyMMdd HH:mm:ss.S", "yyyy.MM.dd HH:mm:ss.S", "yyyy-MM-dd HH:mm:ss.S", "yyyy/MM/dd HH:mm:ss.S",
					"EEE MMM d HH:mm:ss z yyyy" // added by jhong (2013.01.15)
		};

	/**
	* PATERN_DATE_TO_STRING
	*/
	public static String PATTERN_DATE_TO_STRING = "yyyy.MM.dd";

	/**
	* PATERN_DATE_TO_STRING_2
	*/
	public static String PATTERN_DATE_TO_STRING_2 = "yyyy-MM-dd"; // by jhong (2013.01.14)

	/**
	* PATTERN_DATETIME_TO_STRING
	*/
	public static String PATTERN_DATETIME_TO_STRING = "yyyy.MM.dd HH:mm:ss";


    private static TimeZone mySTZ = (TimeZone) TimeZone.getTimeZone("JST");
    private static Locale lc = new Locale("Locale.KOREAN", "Locale.KOREA");
	/**
	* today
	*/
	//private static Calendar today = new GregorianCalendar(mySTZ,lc);

	/**
	* startEmptyDate
	*/
	private static java.util.Date startEmptyDate;

	/**
	* endEmptyDate
	*/
	private static java.util.Date endEmptyDate;

	/**
	 * <pre>
	 * 해당하는 달의 마지막 일 구하기
	 * </pre>
	 *
	 * @return
	 * @throws Exception
	 */
	public static Date getLastDayCurrent() throws Exception {
		Calendar today = new GregorianCalendar(mySTZ,lc);
		Calendar resultDay = new GregorianCalendar (mySTZ,lc);
		resultDay.set(today.DATE, today.getActualMaximum(today.DATE));
		return new Date(resultDay.getTimeInMillis());
	}

	/**
	 * <pre>
	 * 해당하는 달의 첫날짜 구하기
	 * </pre>
	 *
	 * @return
	 * @throws Exception
	 */
	public static Date getFirstDayCurrent() throws Exception {
		Calendar today = new GregorianCalendar(mySTZ,lc);
		Calendar resultDay = new GregorianCalendar (mySTZ,lc);
		resultDay.set(today.DATE, 1);
		return new Date(resultDay.getTimeInMillis());
	}

	/**
	 * <pre>
	 * 지난달의 첫날짜 구하기
	 * </pre>
	 *
	 * @return
	 * @throws Exception
	 */
	public static Date getFirstDayPreMonth() throws Exception {
		Calendar today = new GregorianCalendar(mySTZ,lc);
		Calendar resultDay = new GregorianCalendar (mySTZ,lc);
		resultDay.add(today.MONTH, -1);
		resultDay.set(today.DATE, 1);
		return new Date(resultDay.getTimeInMillis());
	}

	/**
	 * <pre>
	 * 지난달의  오늘 구하기
	 * </pre>
	 *
	 * @return
	 * @throws Exception
	 */
	public static Date getDayPreMonth() throws Exception {
		Calendar today = new GregorianCalendar(mySTZ,lc);
		Calendar resultDay = new GregorianCalendar (mySTZ,lc);
		resultDay.add(today.MONTH, -1);
		return new Date(resultDay.getTimeInMillis());
	}

	/**
	 * <pre>
	 * 다음달의  오늘 구하기
	 * </pre>
	 *
	 * @return
	 * @throws Exception
	 */
	public static Date getDayNextMonth() throws Exception {
		Calendar today = new GregorianCalendar(mySTZ,lc);
		Calendar resultDay = new GregorianCalendar (mySTZ,lc);
		resultDay.add(today.MONTH, 1);
		return new Date(resultDay.getTimeInMillis());
	}

	/**
	 * <pre>
	 * 지난해의  오늘 구하기
	 * </pre>
	 *
	 * @return
	 * @throws Exception
	 */
	public static Date getDayPreYear() throws Exception {
		Calendar today = new GregorianCalendar(mySTZ,lc);
		Calendar resultDay = new GregorianCalendar (mySTZ,lc);
		resultDay.add(today.YEAR, -1);
		return new Date(resultDay.getTimeInMillis());
	}

	/**
	 * <pre>
	 * 해당하는 해의  첫날짜 구하기
	 * </pre>
	 *
	 * @return
	 * @throws Exception
	 */
	public static Date getFirstDayCurrentYear() throws Exception {
		Calendar today = new GregorianCalendar(mySTZ,lc);
		Calendar resultDay = new GregorianCalendar (mySTZ,lc);
		resultDay.set(today.get(Calendar.YEAR), 0, 1); // Month value is 0-based. e.g., 0 for January.
		return new Date(resultDay.getTimeInMillis());
	}

	/**
	 * <pre>
	 * 현재 일시 구하기
	 * </pre>
	 *
	 * @return
	 * @throws Exception
	 */
	public static Date getDayCurrent() throws Exception {
		Calendar today = new GregorianCalendar(mySTZ,lc);
		return new Date(today.getTimeInMillis());
	}

	/**
	 * <pre>
	 * 현재 날짜에 기간 더하기
	 * </pre>
	 *
	 * @param period
	 * @return
	 * @throws Exception
	 */
	public static Date getAddDayCurrent(int period) throws Exception {
		Calendar today = new GregorianCalendar(mySTZ,lc);
		Calendar resultDay = new GregorianCalendar (mySTZ,lc);
		resultDay.add(today.DATE, period);
		return new Date(resultDay.getTimeInMillis());
	}

	  /**
	   * MMDDhhmmss형식으로 리턴한다.
	   */
	  public static String getMMDDhhmmss() {
	    Calendar today = Calendar.getInstance(mySTZ, lc);
	    int mon = today.get(Calendar.MONTH) + 1;
	    int day = today.get(Calendar.DAY_OF_MONTH);
	    int hour = today.get(Calendar.HOUR_OF_DAY);
	    int min = today.get(Calendar.MINUTE);
	    int sec = today.get(Calendar.SECOND);

	    String str = "";

	    if (mon < 10) {
	      str += "0";
	    }
	    str += mon;

	    if (day < 10) {
	      str += "0";
	    }
	    str += day;

	    if (hour < 10) {
	      str += "0";
	    }
	    str += hour;

	    if (min < 10) {
	      str += "0";
	    }
	    str += min;

	    if (sec < 10) {
	      str += "0";
	    }
	    str += sec;

	    return str;
	  }

	  /**
	   * YYYYMMDDHHMM형식으로 리턴한다.
	   */
	  public static String getYYYYMMDDHHMM() {
	    Calendar today = Calendar.getInstance(mySTZ, lc);
	    int year = today.get(Calendar.YEAR);
	    int mon = today.get(Calendar.MONTH) + 1;
	    int day = today.get(Calendar.DAY_OF_MONTH);
	    int hour = today.get(Calendar.HOUR_OF_DAY);
	    int min = today.get(Calendar.MINUTE);

	    String str = "";

	    str += year;

	    if (mon < 10) {
	      str += "0";
	    }
	    str += mon;

	    if (day < 10) {
	      str += "0";
	    }
	    str += day;

	    if (hour < 10) {
	      str += "0";
	    }
	    str += hour;

	    if (min < 10) {
	      str += "0";
	    }
	    str += min;

	    return str;
	  }

	  /**
	   * yyyyMMddHHmmss 리턴한다.
	   */
	  public static String getYYYYMMDDHHMMSS() {
	    Calendar today = Calendar.getInstance(mySTZ, lc);
	    int year = today.get(Calendar.YEAR);
	    int mon = today.get(Calendar.MONTH) + 1;
	    int day = today.get(Calendar.DAY_OF_MONTH);
	    int hour = today.get(Calendar.HOUR_OF_DAY);
	    int min = today.get(Calendar.MINUTE);
	    int sec = today.get(Calendar.SECOND);

	    String str = "";

	    str += year;

	    if (mon < 10) {
	      str += "0";
	    }
	    str += mon;

	    if (day < 10) {
	      str += "0";
	    }
	    str += day;

	    if (hour < 10) {
	      str += "0";
	    }
	    str += hour;

	    if (min < 10) {
	      str += "0";
	    }
	    str += min;

	    if (sec < 10) {
	      str += "0";
	    }
	    str += sec;

	    return str;
	  }

	  /**
	   * YYYY.MM.DD의 형식으로 리턴한다.
	   */
	  public static String getYYYYMMDD(String sDele) {
	    Calendar today = Calendar.getInstance(mySTZ, lc);
	    int year = today.get(Calendar.YEAR);
	    int mon = today.get(Calendar.MONTH) + 1;
	    int day = today.get(Calendar.DAY_OF_MONTH);

	    String str = "";

	    str += (year + sDele);

	    if (mon < 10) {
	      str += "0";
	    }
	    str += (mon + sDele);

	    if (day < 10) {
	      str += "0";
	    }
	    str += day;

	    return str;
	  }

	  /**
	   * YYYYMMDD의 형식으로 리턴한다.
	   */
	  public static String getYYYYMMDD() {
	    Calendar today = Calendar.getInstance(mySTZ, lc);
	    int year = today.get(Calendar.YEAR);
	    int mon = today.get(Calendar.MONTH) + 1;
	    int day = today.get(Calendar.DAY_OF_MONTH);

	    String str = "";

	    str += year;

	    if (mon < 10) {
	      str += "0";
	    }
	    str += mon;

	    if (day < 10) {
	      str += "0";
	    }
	    str += day;

	    return str;
	  }

	  public static String getYYMMDD() {
	      String result = getYYYYMMDD().substring(2);
	      return result;
	  }

	  public static String getYYYYMMDD2(int add_day) {
	    Calendar today = Calendar.getInstance(mySTZ, lc);
	    today.add(Calendar.DATE, add_day);
	    int year = today.get(Calendar.YEAR);
	    int mon = today.get(Calendar.MONTH) + 1;
	    int day = today.get(Calendar.DAY_OF_MONTH);

	    String str = "";

	    str += year;

	    if (mon < 10) {
	      str += "0";
	    }
	    str += mon;

	    if (day < 10) {
	      str += "0";
	    }
	    str += day;

	    return str;
	  }

	  /**
	   * YYYYMM의 형식으로 리턴한다.
	   */
	  public static String getYYYYMM() {
	    Calendar today = Calendar.getInstance(mySTZ, lc);
	    int year = today.get(Calendar.YEAR);
	    int mon = today.get(Calendar.MONTH) + 1;

	    String str = "";

	    str += year;

	    if (mon < 10) {
	      str += "0";
	    }
	    str += mon;

	    return str;
	  }
	  /**
	   * 지난달을 YYYYMM의 형식으로 리턴한다.
	   */
	  public static String getYYYYMMPreMonth() {
	    Calendar today = Calendar.getInstance(mySTZ, lc);
		Calendar resultDay = new GregorianCalendar (mySTZ,lc);
		resultDay.add(today.MONTH, -1);
	    int year = resultDay.get(Calendar.YEAR);
	    int mon = resultDay.get(Calendar.MONTH) + 1;

	    String str = "";

	    str += year;

	    if (mon < 10) {
	      str += "0";
	    }
	    str += mon;

	    return str;
	  }

	  /**
	   * 2달전의 YYYYMM의 형식으로 리턴한다.
	   */
	  public static String getYYYYMMPre2Month() {
	    Calendar today = Calendar.getInstance(mySTZ, lc);
		Calendar resultDay = new GregorianCalendar (mySTZ,lc);
		resultDay.add(today.MONTH, -2);
	    int year = resultDay.get(Calendar.YEAR);
	    int mon = resultDay.get(Calendar.MONTH) + 1;

	    String str = "";

	    str += year;

	    if (mon < 10) {
	      str += "0";
	    }
	    str += mon;

	    return str;
	  }

	  /**
	   * 다음달을 YYYYMM의 형식으로 리턴한다.
	   */
	  public static String getYYYYMMNextMonth() {
	    Calendar today = Calendar.getInstance(mySTZ, lc);
		Calendar resultDay = new GregorianCalendar (mySTZ,lc);
		resultDay.add(today.MONTH, +1);
	    int year = resultDay.get(Calendar.YEAR);
	    int mon = resultDay.get(Calendar.MONTH) + 1;

	    String str = "";

	    str += year;

	    if (mon < 10) {
	      str += "0";
	    }
	    str += mon;

	    return str;
	  }

	  /**
	   * 년도를 리턴한다.
	   */
	  public static String getYear() {
	    Calendar today = Calendar.getInstance(mySTZ, lc);
	    int year = today.get(Calendar.YEAR);

	    String str = "";
	    str += year;

	    return str;
	  }

	  /**
	   * 달을 리턴한다.
	   */
	  public static String getMonth() {
	    Calendar today = Calendar.getInstance(mySTZ, lc);
	    int mon = today.get(Calendar.MONTH) + 1;

	    String str = "";
	    if (mon < 10)  str += "0";
	    str += mon;

	    return str;
	  }

	  /**
	   * 일을 리턴한다.
	   */
	  public static String getDay() {
	    Calendar today = Calendar.getInstance(mySTZ, lc);
	    int day = today.get(Calendar.DAY_OF_MONTH);

	    String str = "";
	    if (day < 10)  str += "0";
	    str += day;

	    return str;
	  }

	  /**
	   * HH:MM:SS 의 형식으로 리턴된다. - LogWriter에서 사용
	   */
	  public static String getNowTime() {
	    Calendar today = Calendar.getInstance(mySTZ, lc);
	    int hour = today.get(Calendar.HOUR_OF_DAY);
	    int min = today.get(Calendar.MINUTE);
	    int sec = today.get(Calendar.SECOND);

	    String str = "";

	    if (hour < 10) {
	      str += "0";
	    }
	    str += hour;

	    str += ":";

	    if (min < 10) {
	      str += "0";
	    }
	    str += min;

	    str += ":";

	    if (sec < 10) {
	      str += "0";
	    }
	    str += sec;

	    return str;
	  }

	  /**
	   * HHMMSS 의 형식으로 리턴된다.
	   */
	  public static String getCurrTime() {
	    Calendar today = Calendar.getInstance(mySTZ, lc);
	    int hour = today.get(Calendar.HOUR_OF_DAY);
	    int min = today.get(Calendar.MINUTE);
	    int sec = today.get(Calendar.SECOND);

	    String str = "";

	    if (hour < 10) {
	      str += "0";
	    }
	    str += hour;

	    if (min < 10) {
	      str += "0";
	    }
	    str += min;

	    if (sec < 10) {
	      str += "0";
	    }
	    str += sec;

	    return str;
	  }

	  /**
	   * HHMMSSMile 의 형식으로 리턴된다.
	   */
	  public static String getNowMillTime() {
	    Calendar today = Calendar.getInstance(mySTZ, lc);
	    int year = today.get(Calendar.YEAR);
	    int mon = today.get(Calendar.MONTH) + 1;
	    int day = today.get(Calendar.DAY_OF_MONTH);
	    int hour = today.get(Calendar.HOUR_OF_DAY);
	    int min = today.get(Calendar.MINUTE);
	    int sec = today.get(Calendar.SECOND);
	    int mis = today.get(Calendar.MILLISECOND);

	    String str = "";

	    str += year;
	    if (mon < 10) {
	      str += "0";
	    }
	    str += mon;
	    if (day < 10) {
	      str += "0";
	    }
	    str += day;
	    if (hour < 10) {
	      str += "0";
	    }
	    str += hour;
	    if (min < 10) {
	      str += "0";
	    }
	    str += min;
	    if (sec < 10) {
	      str += "0";
	    }
	    str += sec;
	    if (mis < 10) {
	      str += "00";
	    }
	    else if (mis >= 10 && mis < 100) {
	      str += "0";
	    }
	    str += mis;

	    return str;
	  }

	/**
	 * 현재 시간을 포매팅하여 반환한다.
	 * by jhong (2013.06.10)
	 */
	public static String getNowString(String dtFormat) {
		Calendar today = Calendar.getInstance(mySTZ, lc);
		String szResult = "";
		if (dtFormat != null) {
			szResult = new SimpleDateFormat(dtFormat).format(today.getTime());
		}
		return szResult;
	}
	  
	  /**
	   * YYYYMMDD의 형식으로 리턴한다.
	   */
	  public static String getMinShipDay(int add_day) {
	    Calendar today = Calendar.getInstance(mySTZ, lc);
	    today.add(Calendar.DATE, add_day);

	    int year = today.get(Calendar.YEAR);
	    int mon = today.get(Calendar.MONTH) + 1;
	    int day = today.get(Calendar.DAY_OF_MONTH);

	    String str = "";

	    str += year;

	    if (mon < 10) {
	      str += "0";
	    }
	    str += mon;

	    if (day < 10) {
	      str += "0";
	    }
	    str += day;

	    return str;
	  }

	  /**
	   * add_day 후에 날짜가 주어진 년도와 맞는지 check
	   */
	  public static boolean checkYear(int yyyy, int add_day) {
	    Calendar today = Calendar.getInstance(mySTZ, lc);
	    today.add(Calendar.DATE, add_day);
	    int year = today.get(Calendar.YEAR);

	    if (year == yyyy) {
	      return true;
	    }

	    return false;
	  }

	  /**
	   * add_day 후에 날짜가 주어진 달과 맞는지 check
	   */
	  public static boolean checkMonth(int mm, int add_day) {
	    Calendar today = Calendar.getInstance(mySTZ, lc);
	    today.add(Calendar.DATE, add_day);
	    int mon = today.get(Calendar.MONTH) + 1;

	    if (mon == mm) {
	      return true;
	    }

	    return false;
	  }

	  /**
	   * add_day 후에 날짜가 주어진 날짜와 맞는지 check
	   */
	  public static boolean checkDay(int dd, int add_day) {
	    Calendar today = Calendar.getInstance(mySTZ, lc);
	    today.add(Calendar.DATE, add_day);
	    int day = today.get(Calendar.DAY_OF_MONTH);

	    if (day == dd) {
	      return true;
	    }

	    return false;
	  }

	  /**
	   * add_day 후에 요일을 얻는다
	   */
	  public static int getWeekDay(int add_day) {
	    Calendar today = Calendar.getInstance(mySTZ, lc);
	    today.add(Calendar.DATE, add_day);
	    int day = today.get(Calendar.DAY_OF_WEEK);

	    return day;
	  }

	  /**
	   * 요일을 얻는다.
	   */
	  public static String getweekstr(int day) {
	    String str = "";

	    if (day == 1) {
	      str = "일";
	    }
	    else if (day == 2) {
	      str = "월";
	    }
	    else if (day == 3) {
	      str = "화";
	    }
	    else if (day == 4) {
	      str = "수";
	    }
	    else if (day == 5) {
	      str = "목";
	    }
	    else if (day == 6) {
	      str = "금";
	    }
	    else if (day == 7) {
	      str = "토";

	    }
	    return str;
	  }


	  /**
	   * 현재의 시각을 반환한다.
	   * return
	   */
	  public static Timestamp getNowTimestamp() {
	    Locale lc = new Locale("Locale.KOREAN", "Locale.KOREA");
	    Calendar today = Calendar.getInstance((TimeZone)TimeZone.getTimeZone("JST"), lc);
	    Timestamp result = new Timestamp(today.getTimeInMillis());
	    //Timestamp result = new Timestamp(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DATE), today.get(Calendar.HOUR), today.get(Calendar.MINUTE), today.get(Calendar.SECOND), today.get(Calendar.MILLISECOND));

	    return result;
	  }

	  public static String getListDate(Timestamp dt) {
	   	return (dt != null ? StringUtil.getDateString(dt) : "" );
	  }

	  public static java.util.Date toDate(String strDate, String fmt) throws ParseException {
		  if (StringUtils.isEmpty(strDate)) return null; // by jhong (2013.04.22)
		  
		  SimpleDateFormat sdfmt = new SimpleDateFormat( fmt );
		  java.util.Date date = sdfmt.parse( strDate );
		  return date;
	  }

	  public static java.util.Date toDate(String strDate) throws Exception {
		  if (StringUtil.isEmpty(strDate)) return null;

		  return parseDate(strDate, PATTERN_STRING_TO_DATE);
	  }
	  
	  /**
	   * <pre>
	   * Date를 문자열로 변환
	   * </pre>
	   * 
	   * @param date
	   * @param format
	   * @return
	   * @throws Exception
	   */
	  public static String date2str(Date date, String format) throws Exception {
		  if (date == null) return null;
		  return new SimpleDateFormat(format).format(date);
	  }

	  /**
	 * <pre>
	 * 날짜 설정이 없는 경우 시작일자를 설정된 시작일자로 리턴
	 * </pre>
	 *
	 * @return
	 */
	public static java.util.Date getStartEmptyDate() {
		if (startEmptyDate == null) {
			startEmptyDate = addYears(new java.util.Date(), -2);
		}
		return startEmptyDate;
	}

	/**
	 * <pre>
	 * 날짜 설정이 없는 경우 종료일자를 설정된 종료일자로 리턴
	 * </pre>
	 *
	 * @return
	 */
	public static java.util.Date getEndEmptyDate() {
		if (endEmptyDate == null) {
			endEmptyDate = addYears(new java.util.Date(), 2);
		}
		return endEmptyDate;
	}

	/**
	 * <pre>
	 * Month 더하기한 날짜 구하기
	 * </pre>
	 * @param month
	 * @return
	 * @throws Exception
	 */
	public static Date getDayAddMonth(int month) throws Exception {
		Calendar today = new GregorianCalendar(mySTZ,lc);
		Calendar resultDay = new GregorianCalendar (mySTZ,lc);
		resultDay.add(today.MONTH, month);
		return new Date(resultDay.getTimeInMillis());
	}

	/**
	 * <pre>
	 * Day 더하기한 날짜 구하기
	 * </pre>
	 * @param month
	 * @return
	 * @throws Exception
	 */
	public static Date getDayAddDay(int day) throws Exception {
		Calendar today = new GregorianCalendar(mySTZ,lc);
		Calendar resultDay = new GregorianCalendar (mySTZ,lc);
		resultDay.add(today.DATE, day);
		return new Date(resultDay.getTimeInMillis());
	}
	
	/**
	 * <pre>
	 * 해당하는 월의  첫날짜 구하기
	 * </pre>
	 * @param strMonth (200910)
	 * @return
	 * @throws Exception
	 */
	public static Date getFirstDayThisMonth(String strMonth) throws Exception {
		Calendar resultDay = new GregorianCalendar (mySTZ,lc);

		int intYear = Integer.parseInt(strMonth.substring(0,4));
		int intMonth = Integer.parseInt(strMonth.substring(4,6))-1;	// -1을 해야 현재월
		int intDay = 1;

		resultDay.set(intYear, intMonth, intDay);
		return new Date(resultDay.getTimeInMillis());
	}

	/**
	 * <pre>
	 * 해당하는 월의  마지막날짜 구하기
	 * </pre>
	 * @param strMonth (200910)
	 * @return
	 * @throws Exception
	 */
	public static Date getLastDayThisMonth(String strMonth) throws Exception {
		Calendar resultDay = new GregorianCalendar (mySTZ,lc);

		int intYear = Integer.parseInt(strMonth.substring(0,4));
		int intMonth = Integer.parseInt(strMonth.substring(4,6))-1;	// -1을 해야 현재월
		int intDay = 1;

		resultDay.set(intYear, intMonth, intDay);
		resultDay.add(resultDay.MONTH, +1);	// 다음달
		resultDay.add(resultDay.DATE, -1);	// 하루빼
		return new Date(resultDay.getTimeInMillis());
	}

	  /**
	   * 해당하는 월의 다음달을 YYYYMM의 형식으로 리턴한다.
	   */
	  public static String getYYYYMMNextMonth(String strMonth) {
		Calendar resultDay = new GregorianCalendar (mySTZ,lc);

		int intYear = Integer.parseInt(strMonth.substring(0,4));
		int intMonth = Integer.parseInt(strMonth.substring(4,6))-1;	// -1을 해야 현재월
		int intDay = 1;

		resultDay.set(intYear, intMonth, intDay);
		resultDay.add(resultDay.MONTH, +1);	// 다음달


	    int year = resultDay.get(Calendar.YEAR);
	    int mon = resultDay.get(Calendar.MONTH) + 1;

	    String str = "";

	    str += year;

	    if (mon < 10) {
	      str += "0";
	    }
	    str += mon;

	    return str;
	  }

	/**
	 * String 형식의 날짜를 Calendar 형식으로 변환
	 * by jhong (2013.01.02)
	 */
	public static Calendar str2cal(String str, String format) throws Exception {
		DateFormat formatter = new SimpleDateFormat(format); 
		Date date = (Date)formatter.parse(str); 
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}
	
	/**
	 * 이전달 구하기
	 * by jhong (2013.01.02)
	 */
	public static String getPrevMonthStr(String currMonthStr) {
		return getPrevMonthStr(currMonthStr, "yyyyMM"); // 기본날짜포맷 : yyyyMM
	}

	/**
	 * 이전달 구하기
	 * by jhong (2013.01.02)
	 */
	public static String getPrevMonthStr(String currMonthStr, String dateStrFormat) {
		String prevMonthStr = "";
	    DateFormat formatter = new SimpleDateFormat(dateStrFormat);
	    try {
		    Calendar cal = str2cal(currMonthStr, dateStrFormat);
		    cal.add(Calendar.MONTH, -1);
			prevMonthStr = formatter.format(cal.getTime());
		
	    } catch (Exception e) {
	    	logger.error("fail in getPrevMonthStr() currMonthStr="+currMonthStr+", dateStrFormat="+dateStrFormat, e);
	    }
		
		logger.info("getPrevMonthStr() currMonthStr="+currMonthStr+", prevMonthStr="+prevMonthStr);
		return prevMonthStr;
	}

}
