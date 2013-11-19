package net.study.util;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrTokenizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * <pre>
 * 문자열 유틸리티
 * 현재 기존개발된 edi의 string utility를 사용
 * </pre>
 *
 * @version 2008. 09. 15
 * @author 김창교
 */
public class StringUtil extends StringUtils {

	/**
	 * <pre>
	 * 입력된 delimiter를 기준으로 source String을 분리하여 String[]로 리턴
	 * delimiter사이에 값이 없는 경우도 ""으로 분리
	 * </pre>
	 *
	 * @param source
	 * @param delimiter
	 * @return
	 */
	public static String[] splitToArray(String source, String delimiter) {
		if (StringUtils.isEmpty(source) || StringUtils.isEmpty(delimiter)) {
			String[] array = new String[0];
			return array;
		}

		StrTokenizer token = new StrTokenizer(source);
		token.setDelimiterString(delimiter);
		token.setIgnoreEmptyTokens(false);
		return token.getTokenArray();
	}

	/**
	 * <pre>
	 * 입력된 delimiter를 기준으로 source String을 분리하여 String[]로 리턴
	 * delimiter사이에 값이 없는 경우도 ""으로 분리
	 * </pre>
	 *
	 * @param source
	 * @param delimiter
	 * @return
	 */
	public static String[] splitToArray(String source, String delimiter, boolean isIncludeDelimiter) {
		if (StringUtils.isEmpty(source) || StringUtils.isEmpty(delimiter)) {
			String[] array = new String[0];
			return array;
		}

		StrTokenizer token = new StrTokenizer(source);
		token.setDelimiterString(delimiter);
		token.setIgnoreEmptyTokens(false);

		String[] array = token.getTokenArray();

		for(int i=0;!isIncludeDelimiter && i<array.length;i++) {
			replace(array[i], delimiter, "");
		}

		return array;
	}

	/**
	 * <pre>
	 * 입력된 delimiter를 기준으로 source String을 분리하여 List로 리턴
	 * delimiter사이에 값이 없는 경우도 ""으로 분리
	 * </pre>
	 *
	 * @param source
	 * @param delimiter
	 * @return
	 */
	public static List splitToList(String source, String delimiter) {
		if (StringUtils.isEmpty(source) || StringUtils.isEmpty(delimiter)) {
			List list = new ArrayList();
			return list;
		}

		StrTokenizer token = new StrTokenizer(source);
		token.setDelimiterString(delimiter);
		token.setIgnoreEmptyTokens(false);
		return token.getTokenList();
	}

	/**
	 * <pre>
	 * 입력된 delimiter를 기준으로 source String을 분리하여 List로 리턴
	 * delimiter사이에 값이 없는 경우도 ""으로 분리
	 * </pre>
	 *
	 * @param source
	 * @param delimiter
	 * @return
	 */
	public static List splitToList(String source, String delimiter, boolean isIncludeDelimiter) {
		if (StringUtils.isEmpty(source) || StringUtils.isEmpty(delimiter)) {
			List list = new ArrayList();
			return list;
		}

		StrTokenizer token = new StrTokenizer(source);
		token.setDelimiterString(delimiter);
		token.setIgnoreEmptyTokens(false);

		List list = token.getTokenList();
		String temp;
		for (int i=0;!isIncludeDelimiter && i<list.size();i++) {
			list.set(i, replace((String)list.get(i), delimiter, ""));
		}
		return list;
	}

/*************************************************************************************
 * 이전 WEB EDI에서 사용된 소스
 *************************************************************************************/

 public static int ORACLE_NEXTVAL = 100;
 public static int ORACLE_UUID = 200;
 public static String FORMAT_DATE_DEFAULT = "yyyy-MM-dd";
 public static String FORMAT_DATETIME_DEFAULT = "yyyy-MM-dd HH:mm:ss";

 static Logger logger = LoggerFactory.getLogger(StringUtil.class);

 /**
  *
  * @param szSource
  * @param szTarget
  * @return 1: True, 0: False
  */
 public static int compareStringInUpperCase(String szSource, String szTarget) {
   String szSourceUpper = szSource.toUpperCase();
   String szTargetUpper = szTarget.toUpperCase();

   return szSourceUpper.compareTo(szTargetUpper);
 }

 /**
  * Full path에서 파일이름 과 확장자 만 가져온다.
  * @param szFileName
  * @return 확장자명
  */
 public static String getFileNameOnly(String szFileName) {
   if (szFileName == null) {
     return "";
   }

   szFileName = getFileName(szFileName);

   if (szFileName == null) {
     return "";
   }

   int index = szFileName.lastIndexOf(".");

   if (index < (szFileName.length() - 1) && index != -1) {
     szFileName = szFileName.substring(0, index);
   }

   return szFileName;
 }

 /**
  * 확장자를 제외한 파일명을 가져온다.
  * @param szFileName
  * @return 파일명
  */
 public static String getFileName(String szFileName) {
   String szName = szFileName;

   if (szFileName != null) {
     int index = szFileName.lastIndexOf(File.separatorChar);

     if (index < (szFileName.length() - 1) && index != -1) {
       szName = szFileName.substring(index + 1);
     }
   }

   return szName;
 }

 /**
  * 파일의 확장자를 가져온다.
  * @param szFileName
  * @return 파일의 확장자
  */
 public static String getFileType(String szFileName) {
   if (szFileName != null) {
     int index = szFileName.lastIndexOf(".");

     if (index < (szFileName.length() - 1) && index != -1) {
       return szFileName.substring(index + 1).toUpperCase();
     }
     else {
       return "";
     }
   }

   return "";
 }

 /**
  * 주어진 String의 오른쪽 Space를 모두 없앤다.<BR>
  * <PRE>
  *      예) szSource: "  123 " -> Return: "   123"
  *          szSource: "  " -> Return: ""
  * </PRE>
  */
 public static String rightTrim(String szSource) {
   if (szSource == null) {
     return null;
   }
   if (szSource.length() == 0) {
     return szSource;
   }

   String szResult = szSource;
   int nLastIndex = szSource.length() - 1;

   while (nLastIndex >= 0 && szResult.charAt(nLastIndex) == ' ') {
     szResult = (szResult.length() == 1 ? "" :
                 szResult.substring(0, nLastIndex));
     nLastIndex--;
   }

   return szResult;
 }

 /**
  * 주어진 String이 Delimiter로 분리되어 있는 String일 때,
  * Start와 End가 주어지면 Start부터 End까지의 Token을 취해서 Return한다.<BR>
  * <PRE>
  *      예) szSource: "123|456|789", szDelimiter: "|", nStartToken: 0, nEndToken: 1
  *              -> Return: "123|456"
  * </PRE>
  */
 public static String getSubstringWithDelimiter(String szSource,
                                                String szDelimiter,
                                                int nStartToken, int nEndToken) {
   if (szSource == null || szDelimiter == null ||
       ! (nStartToken >= 0 && nEndToken >= nStartToken)) {
     throw new IllegalArgumentException();
   }

   int i = 0;
   String szResult = "";
   String szNextToken = "";
   StringTokenizer st = new StringTokenizer(szSource, szDelimiter, true);

   while (st.hasMoreTokens() && i <= nEndToken) {
     szNextToken = st.nextToken();

     if (szNextToken.equals(szDelimiter)) {
       szNextToken = "";
     }
     else if (st.hasMoreTokens()) {
       szDelimiter = st.nextToken(); // 이것이 Delimiter.
     }

     if (i == nStartToken) {
       szResult += szNextToken;
     }
     else if (i > nStartToken) {
       szResult += szDelimiter + szNextToken;
     }

     i++;
   }

   return szResult;
 }


 /**
  * Delimiter 로 구분된 하나의 Source Sting 을 nRowCount 에 정의된 개수로 다시 묶어
  * Array 타입의 Strings 결과를 Return한다. <br>
  * <PRE>
  * @param szSource : source string
  * @param szDelimiter : delimiter 로 사용할 string
  * @param nRowCount : 분해의 기준이 되는 줄수
  * @return String[]
  *
  * 	예) szSource : aaaa|bbbb|cccc|dddd|eeee|ffff|gggg, szDelimiter : |
  * 		, nRowCount : 2
  * 		-> Return: String[] {"aaaa|bbbb", "cccc|dddd", "eeee|ffff", "gggg"}
  * </PRE>
  */

 public static String[] getTokenizedStringsWithDelimiter(String szSource,
 		String szDelimiter, int nRowCount) {
 	String[] tmpAry = getTokenizedStringsWithDelimiter(szSource, szDelimiter, true);
 	logger.debug("getTokenizedStringsWithDelimiter tmpAry : " + String.valueOf(tmpAry == null ? 0 : tmpAry.length));
 	if (tmpAry != null && nRowCount > 1 && tmpAry.length > nRowCount){
 		Vector result = new Vector();
 		String tmpString = "";
 		int i;
 		boolean addDelimiter = false;

 		int aryCnt = tmpAry.length;
 		if ("".equals(tmpAry[aryCnt-1])) {
 			aryCnt--;
 		}

 		for (i=0;i<aryCnt;i++) {

			if (addDelimiter)
				tmpString += szDelimiter;

			tmpString += tmpAry[i];

			if (((i+1)%nRowCount)==0 || (i+1)==aryCnt) {
 				result.addElement(tmpString);
 				tmpString = "";
 				addDelimiter = false;
 			}else{
 				addDelimiter = true;
 			}
 		}

 		String[] returnString = new String[result.size()];
 		for (i=0;i<result.size();i++) {
 			returnString[i] = (String)result.get(i);
 		}

 		return returnString;
 	} else if (tmpAry != null && tmpAry.length <= nRowCount){
 		String[] returnString = {""};
 		for (int i=0;i<tmpAry.length;i++) {
 			returnString[0] += (returnString[0].equals("") ? "" : szDelimiter) + tmpAry[i];
 		}
 		return returnString;
 	} else {
 		return tmpAry;
 	}
 }

 /**
  * Delimiter 로 구분된 하나의 Source String 을 Delimiter 단위로 분해하여
  * Array 타입의 Strings 결과를 Return한다. <BR>
  * <PRE>
  * @param szSource  : source string
  * @param szDelimiter : delimiter 로 사용할 string
  *
  *      예) szSource: "123|456|789", szDelimiter: "|"
  *              -> Return: Vector {"123", "456", "789"}
  * </PRE>
  */
 public static String[] getTokenizedStringsWithDelimiter(String szSource,
     String szDelimiter) {
   if (szSource == null || szDelimiter == null) {
     throw new IllegalArgumentException();
   }

   StringTokenizer st = new StringTokenizer(szSource, szDelimiter, false);
   int nTokenNumber = st.countTokens();
   if (nTokenNumber == 0) {
     return null;
   }

   int i = 0;

   String[] arrResult = new String[nTokenNumber];
   while (st.hasMoreTokens()) {
     arrResult[i++] = st.nextToken();
   }
   return arrResult;
 }

 /**
  * Delimiter 로 구분된 하나의 Source String 을 Delimiter 단위로 분해하여
  * Array 타입의 Strings 결과를 Return한다. <BR>
  * <PRE>
  * @param szSource: source string
  * @param szDelimiter: delimiter 로 사용할 string
  * @param bIncludeNullString: Null String도 Array의 element도 잡는다.
  *
  *      예) szSource: "|123|456||789|", szDelimiter: "|"
  *              -> Return: Vector {"", "123", "456", "", "789", ""}
  * </PRE>
  */
 public static String[] getTokenizedStringsWithDelimiter(String szSource,
     String szDelimiter, boolean bIncludeNullString) {
   if (szSource == null || szDelimiter == null) {
     throw new IllegalArgumentException();
   }

   if (szSource.length() == 0) {
     return null;
   }

   StringTokenizer st = new StringTokenizer(szSource, szDelimiter, true);
   Vector vctString = new Vector();
   String szTemp = "";
   boolean bNextDelimiter = false;
   int nCount = 0;

   while (st.hasMoreTokens()) {
     szTemp = st.nextToken();

     if (szTemp.equals(szDelimiter)) {
       if (!bNextDelimiter) {
         vctString.addElement("");
       }

       bNextDelimiter = false;
     }
     else {
       vctString.addElement(szTemp);
       bNextDelimiter = true;
     }
   }

   if (!bNextDelimiter) {
     vctString.addElement("");
   }

   nCount = vctString.size();
   String[] arrString = new String[nCount];

   for (int i = 0; i < nCount; i++) {
     arrString[i] = (String) vctString.elementAt(i);
   }

   return arrString;
 }

 // [2] added by 한은상 2002.01.24
 /**
  * Delimiter 로 구분된 하나의 Source String 을 Delimiter 단위로 분해하고, Trim한후
  * Array 타입의 Strings 결과를 Return한다. <BR>
  * <PRE>
  * @param szSource  : source string
  * @param szDelimiter : delimiter 로 사용할 string
  *
  *      예) szSource: "  123 |  456  |  789", szDelimiter: "|"
  *              -> Return: Vector {"123", "456", "789"}
  * </PRE>
  */
 public static String[] getTokenizedTrimmedStringsWithDelimiter(String
     szSource, String szDelimiter) {
   if (szSource == null || szDelimiter == null) {
     throw new IllegalArgumentException();
   }
   ///System.out.println(" szSource "+szSource);
   StringTokenizer st = new StringTokenizer(szSource, szDelimiter, false);
   int nTokenNumber = st.countTokens();
   if (nTokenNumber == 0) {
     return null;
   }

   int i = 0;
   String[] arrResult = new String[nTokenNumber];

   while (st.hasMoreTokens()) {
     arrResult[i++] = st.nextToken().trim();
   }

   return arrResult;
 }

 // [3] added by 한은상 2002.0204
 public static String[] getTokenizedTrimmedStringWidthDQandSpace(String
     szSource) {
   ArrayList alTokens = new ArrayList();
   boolean bIsOpenedDQ = false;
   if (szSource == null) {
     throw new IllegalArgumentException();
   }

   char[] arrSource2Char = szSource.toCharArray();

   String szToken = "";
   for (int i = 0; i < arrSource2Char.length; i++) {

     if ( (arrSource2Char[i] == '\"') && bIsOpenedDQ) {
       bIsOpenedDQ = false;
       alTokens.add(szToken);
       szToken = "";

     }
     else if (arrSource2Char[i] == '\"' && !bIsOpenedDQ) {
       bIsOpenedDQ = true;
     }
     else if (arrSource2Char[i] == ' ' && bIsOpenedDQ) {
       szToken += arrSource2Char[i];
     }
     else if (arrSource2Char[i] == ' ' && !bIsOpenedDQ) {
       if (szToken.length() > 0) {
         alTokens.add(szToken);
       }
       szToken = "";
     }
     else {
       szToken += arrSource2Char[i];
     }
   }
   if (szToken.length() > 0) {
     alTokens.add(szToken);
     szToken = "";
   }

   return (String[]) alTokens.toArray(new String[alTokens.size()]);
 }

 // Date string -------------------------------------------------------------
 public static SimpleDateFormat s_dfOracle = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 public static SimpleDateFormat DT_NOYEAR = new SimpleDateFormat("M/d");
 public static SimpleDateFormat DT_DATE = new SimpleDateFormat("yyyy-MM-dd");
 public static SimpleDateFormat DT_DATE_MINUTE = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
 public static SimpleDateFormat DT_DATE_SECOND = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
 public static SimpleDateFormat DT_TIME = new SimpleDateFormat("hh:mm:ss a");
 public static SimpleDateFormat DT_NUMBER = new SimpleDateFormat("yyyyMMdd");

 /**
  * 표준포멧에 의한 날짜 형식을 가져온다.
  * @param dt
  * @param dtFormat
  * @return 날짜 스트링
  */
 public static String getDateString(java.util.Date dt, SimpleDateFormat dtFormat) {
     String szResult = "";
     if (dt != null && dtFormat != null) {
         szResult = dtFormat.format(dt);
     }
     return szResult;
 }

 /**
  * 표준포멧에 의한 날짜 형식을 가져온다.
  * @param dt
  * @param dtFormat
  * @return 날짜 스트링
  */
 public static String getDateString(java.util.Date dt, String dtFormat) {
     String szResult = "";
     if (dt != null && dtFormat != null) {
    	 szResult = new SimpleDateFormat(dtFormat).format(dt);

     }
     return szResult;
 }

 /**
  * 표준포멧에 의한 날짜 형식을 가져온다.
  * @param dt
  * @return 날짜 스트링
  */
 public static String getDateString(Timestamp dt) {
 	if (dt == null) return "";
   String dateFormat = FORMAT_DATE_DEFAULT;
 	String szResult = "";
   szResult = new SimpleDateFormat(dateFormat).format(dt);
   return szResult;
 }

 /**
  * 표준포멧에 의한 날짜시각 형식을 가져온다.
  * @param dt
  * @return 날짜 & 시각 스트링
  */
 public static String getDateTimeString(Timestamp dt) {
 	if (dt == null) return "";
   String dateFormat = FORMAT_DATETIME_DEFAULT;
 	String szResult = "";
   szResult = new SimpleDateFormat(dateFormat).format(dt);
   return szResult;
 }

 /**
  * 표준포멧에 의한 날짜시각 형식을 가져온다.
  * @param dt
  * @param dtFormat
  * @return
  */
 public static String getDateString(Timestamp dt, String dtFormat) {
   String szResult = "";
   if (dt != null && dtFormat != null) {
       szResult = new SimpleDateFormat(dtFormat).format(dt);
   }
   return szResult;
}

 // File Size string --------------------------------------------------------
 public static final int SIZE_FULL = 0x0001;
 public static final int SIZE_KILO = 0x0002;

 /**
  * HS코드를 표시
  * @param data
  * @return 포멧된HS코드
  */
 public static String getHSString(String data) {
 	if (data == null || data.equals("")) return "";
 	return getPatternData(data,"####-##-####","-");
 }

 /**
  * 연월 표시
  * @param data ex)200907
  * @return 연월 텍스트 ex)2009년 7월
  */
 public static String getYearMonth(String data) {
	 	if (data == null || data.equals("")) return "";
	 	return data.substring(0,4)+"년 "+data.substring(4)+"월";
	 }

 /**
  * YYYY-MM-DD
  * @param data ex)20090701
  * @return 날짜포멧 텍스트  ex)2009-07-01
  */
 public static String getDateString(String data) {
	 	String result = "";
	 	if (data != null || !data.equals("")) result = data.substring(0,4)+"-"+data.substring(4,6)+"-"+data.substring(6,8);
	 	return result;
	 }

 /**
  *
  * @param lValue
  * @param nType
  * @return
  */
 public static String getFileSizeByString(long lValue, int nType) {
   String szResult = null;

   DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();

   switch (nType) {
     case SIZE_FULL:

       if (1000000 < lValue) {
         df.applyPattern("#,##0.0#");
         szResult = df.format(lValue / 1000000.0) + " MB";
       }
       else {
         df.applyPattern("#,##0");
         szResult = df.format( (lValue + 999) / 1000) + " KB";
       }

       df.applyPattern("#,##0");
       szResult = szResult + " ( " + df.format(lValue) + " Byte(s))";

       break;

     case SIZE_KILO:

       df.applyPattern("#,##0");
       szResult = df.format( (lValue + 999) / 1000) + " KB";

       break;
   }

   return szResult;
 }


 /**
 *
 * @param data
 * @return 콤머가 들어간 스트링 변환
 */
public static String getToCommaString(String data) {
	return getDecimalToCommaString(new Double(data),"#,##0.00##");
}

/**
*
* @param data
* @return 콤머가 들어간 스트링 변환
*/
public static String getToCommaString(String data, String format) {
	return getDecimalToCommaString(new Double(data), format);
}



 /**
  *
  * @param data
  * @return Double 값에 콤머가 들어간 스트링
  */
 public static String getDecimalToCommaString(Double data) {
	return getDecimalToCommaString(data,"#,##0.####");
 }

 /**
  *
  * @param data
  * @param format
  * @return
  */
 public static String getDecimalToCommaString(Double data, String format) {

	if (data.floatValue() == 0.0) return "";
	String szResult = null;
	DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
	df.applyPattern(format);
	szResult = df.format(data);
	return szResult;
 }

 /**
  *
  * @param data
  * @param format
  * @return
  */
 public static String getDecimalToCommaString(double data, String format) {
	return getDecimalToCommaString(new Double(data), format);
 }

 /**
  *
  * @param data
  * @param currency
  * @return
  */
 public static String getDecimalToCommaStringCurrency(Double data, String currency) {
	if (data.floatValue() == 0.0) return "";
	String result = getDecimalToCommaString(data);
	if (!result.equals(""))
		return currency + " " + result;
	else
		return "";
 }

 /**
  *
  * @param data
  * @param currency
  * @param format
  * @return
  */
 public static String getDecimalToCommaStringCurrency(double data, String currency, String format) {
	if (data == 0.0) return "";
	String result = getDecimalToCommaString(data, format);
	if (!result.equals(""))
		return currency + " " + result;
	else
		return "";
 }

 /**
  *
  * @param data
  * @return
  */
 public static String getDecimalToCommaString(double data) {
	if (data == 0.0) return "";
   return getDecimalToCommaString(new Double(data));
 }

 /**
  *
  * @param data
  * @param currency
  * @return
  */
 public static String getDecimalToCommaStringCurrency(double data, String currency) {
	if (data == 0.0) return "";
	String result = getDecimalToCommaString(data);
	if (!result.equals(""))
		return currency + " " + result;
	else
		return "";
 }

 /**
  *
  * @param data
  * @return
  */
 public static String getDecimalToCommaString(int data) {
   return getDecimalToCommaString(data,"#,###");
 }

 /**
  *
  * @param data
  * @param format
  * @return
  */
 public static String getDecimalToCommaString(int data, String format) {
   if (data == 0) return "";
   String szResult = "";
   DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(Locale.KOREA);
   df.applyPattern(format);
   szResult = df.format(data);
   return szResult;
 }

 /**
  *
  * @param data
  * @return
  */
 public static String getDouble2String(Double data) {
 		if (data.floatValue() == 0) return "";
 		DecimalFormat df = (DecimalFormat)NumberFormat.getInstance(Locale.KOREA);
 		df.applyPattern("#");
 		return df.format(data);
 }

 /**
  *
  * @param data
  * @param currency
  * @return
  */
 public static String getDouble2StringCurrency(Double data, String currency) {
	if (data.floatValue() == 0) return "";
	String result = getDouble2String(data);
	if (!result.equals(""))
		return currency + " " + result;
	else
		return "";
 }

 /**
  *
  * @param data
  * @return
  */
 public static String getDouble2String(double data) {
	if (data == 0.0) return "";
   return getDouble2String(new Double(data));
 }

 /**
  *
  * @param data
  * @param currency
  * @return
  */
 public static String getDouble2StringCurrency(double data, String currency) {
	if (data == 0.0) return "";
	String result = getDouble2String(new Double(data));
	if (!result.equals(""))
		return currency + " " + result;
	else
		return "";
 }

 /**
  *
  * @param data
  * @param pattern
  * @param delimeter
  * @return
  */
 public static String getPatternData(String data, String pattern, String delimeter) {
     if (data == null || data.length() == 0) return "";
     StringBuffer result = new StringBuffer();
     StringTokenizer st = new StringTokenizer(pattern,delimeter);
     int start = 0;
     int count = st.countTokens();

     for ( int i = 0; i < count ; i++) {
         String tkdata = st.nextToken();
         String field = "";
         if (start >= data.length()) continue; // pattern 길이보다 작은 data 일때 에러나지 않도록 수정 by jhong (2013.01.28)
         if (i < (count -1)) {
        	 // pattern 길이보다 작은 data 일때 에러나지 않도록 수정 by jhong (2013.01.28)
        	 int end = start + tkdata.length();
        	 if (end >= data.length()) end = data.length();
        	 
             field = data.substring(start, end);
             
             if (end == start + tkdata.length()) field += delimeter;
         } else {
             field = data.substring(start);
         }
         start = start + (tkdata.length());
         result.append( field );
     }
     return  result.toString();
}

 /**
  * 사업자번호를 입력받아서 마스킹 한다
  * @param data
  * @return 대쉬(-)가 들어간 사업자번호
  */
 public static String getRegistNO(int data) {
   return getPatternData(""+data, "###-##-#####", "-");
 }

 /**
  *
  * @param data
  * @return 대쉬(-)가 들어간 사업자번호
  */
 public static String getRegistNO(String data) {
     return getPatternData(data, "###-##-#####", "-");
 }

 /**
  *
  * @param data
  * @return ####.##.##로 포맷된 스트링값
  */
 public static String getCTBDate(int data) {
     return getPatternData(""+data, "####.##.##", ".");
 }

 /**
  *
  * @param data
  * @return ####.##.##로 포맷된 스트링값
  */
 public static String getCTBDate(String data) {
     //getFloat2IntString(10.0f);
     return getPatternData(data, "####.##.##",  ".");
 }

 /**
  *
  */
 public final static String BLANK = "            "; // 12 칸

 /**
  *
  * @param data
  * @return Doble값을 char로 변환한 값
  */
 public static char [] getDouble2IntString( Double data ) {
     String str = getDouble2String(data);
     String result = BLANK + str;
     result = result.substring(result.length() - (result.length() - str.length()));
     return result.toCharArray();
 }

 /**
  *
  * @param szPath
  * @return 안전한 경로값
  */
 public static String makePathSafe(String szPath) {
   if (szPath == null) {
     return null;
   }

   szPath = szPath.trim();

   int index = szPath.indexOf(" ");

   if (index > 0) {
     return "\"" + szPath + "\"";
   }
   else {
     return szPath;
   }
 }

 /**
  * 첫번째 만나는 subString을 다른 subString로 대체한다.
  * @param szSource  source string
  * @param szSubString1 subString to be substitute.
  * @param szSubString2 subSting to be changed.
  * @return String that is substituted.
  * <PRE>
  *      예) szSource: "123?456789", szSubString1: "?", szSubString2: "a"
  *              -> Return: "123a456789"
  * </PRE>
  */

 public static String substituteString(String szSource, String szSubString1,
                                       String szSubString2) {
   if (szSource == null || szSubString1 == null || szSubString2 == null) {
     throw new IllegalArgumentException();
   }

   StringTokenizer st = new StringTokenizer(szSource, szSubString2, false);
   String szNextToken = null;
   String szReturn = null;

   if (st.hasMoreTokens()) {
     szReturn = st.nextToken();
     szReturn = szReturn + szSubString1;

     if (st.hasMoreTokens()) {
       szReturn = szReturn + st.nextToken("\n");
     }
   }

   return szReturn;
 }

 /**
  * szOriginal에서 szOld를 모두 szNew로 바꾼다.
  *
  * @param szOriginal 원래의 문자열.
  * @param szOld 바꾸고자하는 문자열.
  * @param szNew 새로운 문자열.
  * @return szOriginal 문자열에서 모든 szOld 문자열을 szNew 문자열로 대치한 문자열을 넘긴다.
  */
 public static String replace(String szOriginal, String szOld, String szNew) {
   return replace(szOriginal, szOld, szNew, 0);
 }

 /**
  * sszOriginal에서 처음부터 nReplaceCount개만큼 szOld를 szNew로 바꾼다.
  * 만약 nReplaceCount가 0이면 szOld를 모두 szNew로 바꾼다.
  *
  * @param szOriginal 원래의 문자열.
  * @param szOld 바꾸고자하는 문자열.
  * @param szNew 새로운 문자열.
  * @param nReplaceCount szOriginal의 처음부터 몇개까지의 szOld를 szNew로 바꿀지를 결정한다.
  *                      1이면 처음 나타나는 szOld 문자열만을 szNew로 바꾼다.
  *                      0이면 모든 szOld 문자열을 szNew로 바꾼다.
  * @return szOriginal 문자열에서 szOld 문자열을 찾아 nReplaceCount 갯수만큼 szNew 문자열로 대치한 문자열을 넘긴다.
  */
 public static String replace(String szOriginal, String szOld, String szNew,
                              int nReplaceCount) {
   if (szOriginal == null || szOld == null || szNew == null) {
     throw new IllegalArgumentException();
   }

   StringBuffer sbResult = new StringBuffer();
   int nFromIndex = 0, nToIndex = 0;
   int nOldLength = szOld.length();
   int i = 0;

   while ( (nToIndex = szOriginal.indexOf(szOld, nFromIndex)) >= 0) {
     sbResult.append(szOriginal.substring(nFromIndex, nToIndex)).append(szNew);
     nFromIndex = nToIndex + nOldLength;

     if (nReplaceCount != 0 && ++i == nReplaceCount) {
       return sbResult.append(szOriginal.substring(nFromIndex)).toString();
     }
   }

   return sbResult.append(szOriginal.substring(nFromIndex)).toString();
 }

 /**
  *  DB 검색에서, '*'를 포함하는 검색을 지원하기 위한 함수이다.
  *   '*' 문자를 '%' 문자로 바꾼다.  뒤에 오는 '*'는 없애 버린다.
  */
 public static String replaceWildCharToLikeChar(String szQuery) {
   // white space를 잘라낸다.
   szQuery = szQuery.trim();

   // 앞에 문자가 "*"이면 %로 바꾼다.
   if (szQuery.startsWith("*")) {
     szQuery = "%" + szQuery.substring(1);
   }

   // 뒤에 문자가 "*"이면 없애버린다.
   if (szQuery.endsWith("*")) {
     szQuery = szQuery.substring(0, szQuery.length() - 1);
   }

   return szQuery;
 }

 /**
  *
  * @param szName
  * @return true|flase
  */
 public static boolean checkValidName(String szName) {
   if (szName == null || szName.length() <= 0) {
     return false;
   }

   if (szName.indexOf('\\') >= 0 ||
       szName.indexOf('/') >= 0 ||
       szName.indexOf(':') >= 0 ||
       szName.indexOf('*') >= 0 ||
       szName.indexOf('?') >= 0 ||
       szName.indexOf('"') >= 0 ||
       szName.indexOf('<') >= 0 ||
       szName.indexOf('>') >= 0 ||
       szName.indexOf('|') >= 0) {
     return false;
   }

   return true;
 }

 /**
  *  문자열의 Array를 합친다.
  *
  *  @param szTokens 합할 문자열 Array
  *  @param szSept Separator 문자열
  *  @return 반복된 문자열
  */
 public static String concatenate(String[] szTokens, String szSept) {
   StringBuffer sb = new StringBuffer();

   if (szTokens == null) {
     return null;
   }

   if (szTokens.length > 0) {
     sb.append(szTokens[0]);

   }
   for (int i = 1; i < szTokens.length; i++) {
     sb.append(szSept).append(szTokens[i]);
   }
   return sb.toString();
 }

 /**
  *  문자열의 Array를 합친다.
  *
  *  @param tokens 합할 C
  *  @param szSept Separator 문자열
  *  @return 반복된 문자열
  */
 public static String concatenate(Collection tokens, String szSept) {
   StringBuffer sb = new StringBuffer();

   if (tokens == null) {
     return null;
   }

   Iterator iter = tokens.iterator();

   if (iter.hasNext()) {
     sb.append(iter.next());

   }
   while (iter.hasNext()) {
     sb.append(szSept).append(iter.next());
   }

   return sb.toString();
 }

 /**
  *
  * @param data
  * @return euck-kr 로 변환된 문자열값
  */
 public static String ascii2korean(String data) {
     String rtn = null;
     try {
         rtn = (data == null) ? "" :
             new String(data.getBytes("8859_1"), "euc-kr");
     }
     catch (java.io.UnsupportedEncodingException e) {
    	 logger.error("fail in ascii2korean() data="+data+", e");
     }
     return rtn;
 }

 /**
  *
  * @param data
  * @return 8859_1로 변환된 문자열값.
  */
 public static String korean2ascii(String data) {
     String rtn = null;
     try {
         rtn = (data == null) ? "" :
             new String(data.getBytes("euc-kr"), "8859_1");
     }
     catch (java.io.UnsupportedEncodingException e) {}
     return rtn;
 }

 /**
  *
  * @param data
  * @param type1
  * @param type2
  * @return type1에서 type2 로 변환된 문자열 값.
  */
 public static String charSetConvert(String data, String type1, String type2) {
     String rtn = null;
     try {
         rtn = (data == null) ? "" : new String(data.getBytes(type1), type2);
     }
     catch (java.io.UnsupportedEncodingException e) {}
     return rtn;
 }

 /**
  * Method that joins an array of strings into a string
  * with delimited fields
  *
  * @param items an array of strings.
  * @param delim String that represents the delimiter.
  *
  * @return the String that contains the delimited list.
  */
 public static String join(String[] items,String delim) {

     StringBuffer sbuf=new StringBuffer();
     for(int i=0;i<items.length;i++){
         sbuf.append(items[i]);
         if(i<items.length-1) {
             sbuf.append(delim);
         }
     }
     return sbuf.toString();
 }

 /**
  * Method that joins an array of strings into a string
  * with delimited fields
  * 배열의 값이 공백이 아닐때에만 delimiter 추가 하도록
  *
  * @param items an array of strings.
  * @param delim String that represents the delimiter.
  *
  * @return the String that contains the delimited list.
  */
 public static String getArrayString(String[] items,String delim) {

     StringBuffer sbuf=new StringBuffer();
     for(int i=0;i<items.length;i++){
     	if(i == 0){
     		sbuf.append(items[i]);
     	}
     	else if(!items[i].equals("")){
     		sbuf.append(delim);
     		sbuf.append(items[i]);
     	}
     }
     return sbuf.toString();
 }

 /**
  * Delimiter 로 구분된 하나의 Source Sting 을 nArrayCount 의
  * Array 타입의 Strings 결과를 Return한다. <br>
  * <PRE>
  * @param szSource : source string
  * @param szDelimiter : delimiter 로 사용할 string
  * @param nArrayCount : 반환할 Arrary 개수
  * @return String[]
  *
  * 	예) szSource : aaaa|bbbb|cccc|dddd|eeee|ffff|gggg, szDelimiter : |
  * 		, nArrayCount : 3
  * 		-> Return: String[] {"aaaa", "bbbb", "cccc"}
  * 	예) szSource : aaaa|bbbb|, szDelimiter : |
  * 		, nArrayCount : 3
  * 		-> Return: String[] {"aaaa", "bbbb", ""}
  * </PRE>
  */
 public static String[] getTokenizedStringsWithDelimiterCount(String szSource,String szDelimiter, int nArrayCount){
     if (szSource == null || szDelimiter == null ||nArrayCount <= 0){
         throw new IllegalArgumentException();
     }

     StringTokenizer st = new StringTokenizer(szSource, szDelimiter, false);
     int nTokenNumber = st.countTokens();
     /*
     if (nTokenNumber == 0) {
       return null;
     }
     */

     String[] arrResult = new String[nArrayCount];
     for ( int i = 0 ; i < nArrayCount; i++ ){
         if(i<nTokenNumber){
             arrResult[i] = st.nextToken();
         }
         else{
             arrResult[i] = "";
         }
     }
     return arrResult;
 }


 /**
  * 문자열 기준 왼쪽 검색 왼쪽 값 반환.
  * 왼쪽부터 검색을 하여 최초로 나오는 구분자를 찾아, 그전까지 문자열을 반환한다.
  * 기준문자열이 없으면 자르려고 하는 문자열 전체 반환.
  * @param   src String      자르려고 하는 문장
  * @param   str String      기준 문자열.구분자
  * @return the translated string.
  */
 public static String getLeftString (String src, String str)
     throws StringIndexOutOfBoundsException {
	 String result = src;
	 if(src.indexOf(str) > 0) result = src.substring(0,src.indexOf(str));
     return result;
 }

 /**
  * 문자열 기준 왼쪽 검색 오른쪽 값 반환.str 문자 제외 반환.
  * 왼쪽부터 검색을 하여 최초로 나오는 구분자를 찾아, 그 이후의 문자열을 반환한다. 구분자는 포함하지 않는다.
  * 기준문자열이 없으면 공백 반환.
  * @param   src String      자르려고 하는 문장
  * @param   str String      기준 문자열.구분자
  * @return the translated string.
  */
 public static String getRightString (String src, String str)
     throws StringIndexOutOfBoundsException {
	 String result = "";
	 if(src.indexOf(str) > 0) result = src.substring(src.indexOf(str) + str.length(), src.length());
     return result;
 }

	public static String nullToSpace(String str) {
		if (str == null || str.trim().length() == 0) {
			return "";
		}
		return str;
	}

	public static String nullToZero(String str) {
		if (str == null || str.equals("")) {
			return "0";
		}
		return str;
	}

	public static String nullTo(String str, String returnChar) {
		if (str == null || str.equals("")) {
			return returnChar;
		}
		return str;
	}

	// 9999999.99 까지만 ...
	public static String getAmtString(String AmtData){
		Hashtable StrNumberDb1 = new Hashtable();
		StrNumberDb1.put ( "0", "");
		StrNumberDb1.put ( "1", "ONE");
		StrNumberDb1.put ( "2", "TWO");
		StrNumberDb1.put ( "3", "THREE");
		StrNumberDb1.put ( "4", "FOUR");
		StrNumberDb1.put ( "5", "FIVE");
		StrNumberDb1.put ( "6", "SIX");
		StrNumberDb1.put ( "7", "SEVEN");
		StrNumberDb1.put ( "8", "EIGHT");
		StrNumberDb1.put ( "9", "NINE");
		StrNumberDb1.put ( "10", "TEN");

		Hashtable StrNumberDb2 = new Hashtable();
		StrNumberDb2.put ( "0", "");
		StrNumberDb2.put ( "1", "ONE");
		StrNumberDb2.put ( "2", "TWO");
		StrNumberDb2.put ( "3", "THREE");
		StrNumberDb2.put ( "4", "FOUR");
		StrNumberDb2.put ( "5", "FIVE");
		StrNumberDb2.put ( "6", "SIX");
		StrNumberDb2.put ( "7", "SEVEN");
		StrNumberDb2.put ( "8", "EIGHT");
		StrNumberDb2.put ( "9", "NINE");
		StrNumberDb2.put ( "10", "TEN");
		StrNumberDb2.put ( "11", "ELEVEN");
		StrNumberDb2.put ( "12", "TWELVE");
		StrNumberDb2.put ( "13", "THIRTEEN");
		StrNumberDb2.put ( "14", "FOURTEEN");
		StrNumberDb2.put ( "15", "FIFTEEN");
		StrNumberDb2.put ( "16", "SIXTEEN");
		StrNumberDb2.put ( "17", "SEVENTEEN");
		StrNumberDb2.put ( "18", "EIGHTEEN");
		StrNumberDb2.put ( "19", "NINETEEN");

		Hashtable StrNumberDb3 = new Hashtable();
		StrNumberDb3.put ( "0", "");
		StrNumberDb3.put ( "2", "TWENTY");
		StrNumberDb3.put ( "3", "THIRTY");
		StrNumberDb3.put ( "4", "FORTY");
		StrNumberDb3.put ( "5", "FIFTY");
		StrNumberDb3.put ( "6", "SIXTY");
		StrNumberDb3.put ( "7", "SEVENTY");
		StrNumberDb3.put ( "8", "EIGHTY");
		StrNumberDb3.put ( "9", "NINETY");

		String str = "";
		String str0 = "";
		String str1 = "";
		String str2 = "";
		String str3 = "";
		String str4 = "";
		String str5 = "";
		String[] strAmt = getTokenizedStringsWithDelimiterCount(AmtData,".",2);
		String strTmp1 = "";
		String strTmp2 = "";
		String strTmp3 = "";

		// 소수점 이상 체크
		if(strAmt[0].length() >1){
			strTmp1 = strAmt[0].substring(strAmt[0].length()-2,strAmt[0].length());
			strTmp2 = strAmt[0].substring(strAmt[0].length()-2,strAmt[0].length()-1);
			strTmp3 = strAmt[0].substring(strAmt[0].length()-1,strAmt[0].length());
			if(Integer.parseInt(strTmp1) < 20 && Integer.parseInt(strTmp1) != 0){
				str1 = (String)StrNumberDb2.get(String.valueOf(Integer.parseInt(strTmp1)));
			}
			else if(Integer.parseInt(strTmp1) > 19 && Integer.parseInt(strTmp1) < 100){
				str1 = (String)StrNumberDb3.get(strTmp2) + " " +(String)StrNumberDb1.get(strTmp3);
			}
		}
		else if(strAmt[0].length() == 1){
			strTmp1 = strAmt[0].substring(strAmt[0].length()-1,strAmt[0].length());
			str1 = (String)StrNumberDb1.get(strTmp1);
		}

		if(strAmt[0].length() >2){
			strTmp1 = strAmt[0].substring(strAmt[0].length()-3,strAmt[0].length()-2);
			if(Integer.parseInt(strTmp1) != 0){
				str2 = (String)StrNumberDb1.get(strTmp1) + " HUNDRED ";
			}
		}

		if(strAmt[0].length() >3){
			if(strAmt[0].length() > 4){
				strTmp1 = strAmt[0].substring(strAmt[0].length()-5,strAmt[0].length()-3);
				strTmp2 = strAmt[0].substring(strAmt[0].length()-5,strAmt[0].length()-4);
				strTmp3 = strAmt[0].substring(strAmt[0].length()-4,strAmt[0].length()-3);
				if(Integer.parseInt(strTmp1) < 20 && Integer.parseInt(strTmp1) != 0){
					str3 = (String)StrNumberDb2.get(String.valueOf(Integer.parseInt(strTmp1))) + " THOUSAND ";
				}
				else if(Integer.parseInt(strTmp1) > 19 && Integer.parseInt(strTmp1) < 100){
					str3 = (String)StrNumberDb3.get(strTmp2) + " " + (String)StrNumberDb1.get(strTmp3) + " THOUSAND ";
				}
			}
			else{
				strTmp1 = strAmt[0].substring(strAmt[0].length()-4,strAmt[0].length()-3);
				if(Integer.parseInt(strTmp1) != 0){
					str3 = (String)StrNumberDb1.get(strTmp1) + " THOUSAND ";
				}
			}
		}

		if(strAmt[0].length() > 5){
			strTmp1 = strAmt[0].substring(strAmt[0].length()-6,strAmt[0].length()-5);
			if(Integer.parseInt(strTmp1) != 0){
				str4 = (String)StrNumberDb1.get(strTmp1) + " HUNDRED ";
			}
		}

		if(strAmt[0].length() > 6){
			strTmp1 = strAmt[0].substring(strAmt[0].length()-7,strAmt[0].length()-6);
			if(Integer.parseInt(strTmp1) != 0){
				str5 = (String)StrNumberDb1.get(strTmp1) + " MILLION ";
			}
		}

		// 소수점 이하 체크
		if(strAmt[1].length()>1){
			strTmp1 = strAmt[1].substring(0,2);
			strTmp2 = strAmt[1].substring(0,1);
			strTmp3 = strAmt[1].substring(1,2);
			if(Integer.parseInt(strTmp1) < 20 && Integer.parseInt(strTmp1) != 0){
				str0 = " AND CENTS " + (String)StrNumberDb2.get(String.valueOf(Integer.parseInt(strTmp1)));
			}
			else if(Integer.parseInt(strTmp1) > 19 && Integer.parseInt(strTmp1) < 100){
				str0 = " AND CENTS " + (String)StrNumberDb3.get(strTmp2) + " " + (String)StrNumberDb1.get(strTmp3);
			}
		}

		return str = str5 + str4 + str3 + str2 + str1 + str0 ;
	}

    /**
     * <pre>
     * housekey를 추가하여 현재시간(밀리세컨드)로 file key를 생성
     * </pre>
     *
     * @param session
     * @return
     * @throws Exception
     */
    public static String getNewFileKey(String housekey) throws Exception {
    	StringBuffer fileKey = new StringBuffer();

    	fileKey.append(housekey);
    	fileKey.append("-");
    	fileKey.append(DateUtil.getNowMillTime());

    	return fileKey.toString();
    }

    /**
     * <pre>
     * delimiter로 구분된 문자열을 받아 row count 단위로 나누어 List로 리턴한다.
     * EDI에서 textarea에 입력된 free text를 세그먼트 단위로 나누기 위해 사용한다.
     *
     * </pre>
     *
     * @param text 구분하고자하는 text
     * @param delimiter 각 row를 구분하는 구분자
     * @param count 하나의 String으로 생성할 row 수
     * @return String의 list 리턴. 만약 입력된 text값이 null이거나 ""인 경우 빈 list 리턴.
     * @throws Exception
     */
    public static List splitRows (String text, String delimiter, int count) throws Exception {
    	List rowList = new ArrayList();
    	if (isEmpty(text)) {
    		return rowList;
    	}
    	String[] strRows = StringUtil.splitToArray(text, delimiter);

    	if (strRows == null || strRows.length < count) {
    		rowList.add(text);
    	} else {
    		int index = 0;
    		for (int i=0;i<strRows.length;i++) {
    			index = (int)Math.floor(i+1/count);
    			if (rowList.size() > index) {
    				StringBuffer tmpBuf = (StringBuffer)rowList.get(index);
					tmpBuf.append(delimiter);
    				tmpBuf.append(strRows[i]);
    			} else {
    				StringBuffer tmpBuf = new StringBuffer();
    				tmpBuf.append(strRows[i]);
    				rowList.add(tmpBuf);
    			}
    		}
    	}

    	for (int i=0;i<rowList.size();i++) {
    		rowList.set(i, rowList.get(i).toString());
    	}

    	return rowList;
    }
    
    /**
     * 입력된 길이로 문자열을 분리하고 \n 로 각 문자열을 구분하여 하나의 문자열로 리턴
     * @param orgString
     * @param length
     * @return
     */
    public static String rebuildEDIByLength (String orgString, int length) {
    	return rebuildEDIByLength (orgString, length, "\n");
    }
    
    /**
     * 입력된 길이로 문자열을 분리하고 입력된 구분자로 각 문자열을 구분하여 하나의 문자열로 리턴
     * @param orgString
     * @param length
     * @param delimeter
     * @return
     */
    public static String rebuildEDIByLength (String orgString, int length, String delimeter) {
    	if (orgString == null)
    		return null;
    	
    	if (orgString.length() <= length)
    		return orgString;
    	
    	List list = splitByLength(orgString, length);
    	StringBuffer newStr = new StringBuffer();
    	for (int i=0;i<list.size();i++) {
    		if (i<list.size()-1) {
    			newStr.append(delimeter);
    		}
    		newStr.append((String)list.get(i));
    	}
    	
    	return newStr.toString();
    }
    
    /**
     * 문자열을 입력받은 길이로 split하여 list 로 리턴
     * @param orgString
     * @param length
     * @return
     */
    public static List splitByLength (String orgString, int length) {
    	List list = new ArrayList();
    	String working = orgString;
    	while (!isEmpty(working)) {
    		String temp = getShortString( working, length);
    		list.add(temp);
    		working = working.substring(temp.length());
    	}
    	return list;
    }
    
    /**
     * 스트링 자르기
     * @param orig
     * @param length
     * @return
     */
    public static String getShortString( String orig, int length) {
        byte[] byteString = orig.getBytes();

        if (byteString.length <= length) {
            return orig;
        } else {
            int minusByteCount = 0;
            for (int i = 0; i < length; i++) {
                minusByteCount += (byteString[i] < 0) ? 1 : 0;
            }

            if (minusByteCount % 2 != 0) {
                length--;
            }

            return new String(byteString, 0, length);
        }
    }
    
    
    /**
	 * append할 문자열 반환
	 */
	public static String getAppendStr(String str) {
		return getAppendStr(str, " ");
	}

	public static String getAppendStr(String str, String delimiter) {
		if (isEmpty(str)) 
			return "";
		else
			return delimiter + str;
	}
	
	/**
	 * 문자열 자르기
	 * http://blog.naver.com/PostView.nhn?blogId=bak35u&logNo=10030674501
	 * 
	 * @param szText
	 * @param szKey
	 * @param nLength
	 * @param nPrev
	 * @param isNotag
	 * @param isAdddot
	 * @return
	 */
	public static String strCut(String szText, String szKey, int nLength, int nPrev, boolean isNotag, boolean isAdddot){
	
		String r_val = szText;
		int oF = 0, oL = 0, rF = 0, rL = 0; 
		int nLengthPrev = 0;
		Pattern p = Pattern.compile("<(/?)([^<>]*)?>", Pattern.CASE_INSENSITIVE);  // 태그제거 패턴
   
		if(isNotag) {r_val = p.matcher(r_val).replaceAll("");}  // 태그 제거
		r_val = r_val.replaceAll("&amp;", "&");
		r_val = r_val.replaceAll("(!/|\r|\n|&nbsp;)", "");  // 공백제거
 
		try {
			byte[] bytes = r_val.getBytes("UTF-8");     // 바이트로 보관
			if(szKey != null && !szKey.equals("")) {
				nLengthPrev = (r_val.indexOf(szKey) == -1)? 0: r_val.indexOf(szKey);  // 일단 위치찾고
				nLengthPrev = r_val.substring(0, nLengthPrev).getBytes("MS949").length;  // 위치까지길이를 byte로 다시 구한다
				nLengthPrev = (nLengthPrev-nPrev >= 0)? nLengthPrev-nPrev:0;    // 좀 앞부분부터 가져오도록한다.
			}

			// x부터 y길이만큼 잘라낸다. 한글안깨지게.
			int j = 0;
			if(nLengthPrev > 0) while(j < bytes.length) {
				if((bytes[j] & 0x80) != 0) {
					oF+=2; rF+=3; if(oF+2 > nLengthPrev) {break;} j+=3;
				} else {if(oF+1 > nLengthPrev) {break;} ++oF; ++rF; ++j;}
			}
  
			j = rF;
			while(j < bytes.length) {
				if((bytes[j] & 0x80) != 0) {
					if(oL+2 > nLength) {break;} oL+=2; rL+=3; j+=3;
					} else {if(oL+1 > nLength) {break;} ++oL; ++rL; ++j;}
				}
				r_val = new String(bytes, rF, rL, "UTF-8");  // charset 옵션
				if(isAdddot && rF+rL+3 <= bytes.length) {r_val+="...";}  // ...을 붙일지말지 옵션 
		} catch(UnsupportedEncodingException e){ e.printStackTrace(); }   

		return r_val;
 	}
	
	/**
	 * 문자열 자르기
	 * http://nuninaya.tistory.com/48
	 * 
	 * @param szText
	 * @param nLength
	 * @return
	 */
	public static String strCut(String szText, int nLength) {
		String r_val = szText;
		if (isEmpty(r_val)) return r_val;
		
		int oF = 0, oL = 0, rF = 0, rL = 0;
		int nLengthPrev = 0;
		try {
			byte[] bytes = r_val.getBytes("UTF-8"); // 바이트로 보관
			// x부터 y길이만큼 잘라낸다. 한글안깨지게.
			int j = 0;
			if (nLengthPrev > 0)
				while (j < bytes.length) {
					if ((bytes[j] & 0x80) != 0) {
						oF += 2;
						rF += 3;
						if (oF + 2 > nLengthPrev) {
							break;
						}
						j += 3;
					}
					else {
						if (oF + 1 > nLengthPrev) {
							break;
						}
						++oF;
						++rF;
						++j;
					}
				}
				j = rF;
				while (j < bytes.length) {
					if ((bytes[j] & 0x80) != 0) {
						if (oL + 2 > nLength) {
							break;
						}
						oL += 2;
						rL += 3;
						j += 3;
					}
					else {
						if (oL + 1 > nLength) {
							break;
						}
						++oL;
						++rL;
						++j;
					}
				}
				r_val = new String(bytes, rF, rL, "UTF-8"); // charset 옵션
		
		} catch (UnsupportedEncodingException e) {
			logger.error("fail in strCut...["+szText+"]", e);
		}
		return r_val;
	}
	

	/**
	 * 문자열을 지정된 길이로 잘라 list로 반환
	 * @param szText
	 * @param nLength
	 * @return
	 */
	public static List<String> splitByLength2(String text, int nLength) {
		List<String> strList = new ArrayList<String>();
		if (isEmpty(text)) return strList;
		
		String textCopy = text;
		while (true) {
			String tmp = strCut(textCopy, nLength);
			strList.add(tmp);
			textCopy = textCopy.replace(tmp, "");
			if (isEmpty(textCopy)) break;
		}
		
		logger.info("splitByLength2() text="+text+", nLength="+nLength+", strList="+strList);
		return strList;
	}
	
	/**
	 * map 의 value 값 존재여부 확인
	 * @param map
	 * @param key
	 * @return
	 */
	public static boolean isEmpty(Map map, String key) {
		boolean isEmpty = true;
		if (map == null) return isEmpty;

		Object obj = map.get(key);
		if ( obj != null 
				&& !StringUtils.isEmpty(obj.toString())
				&& !(obj+"").equals("null")) {
			
			isEmpty = false;
		}
		
		return isEmpty;
	}
	
	/**
	 * map 의 String Value 얻기
	 * @param map
	 * @param key
	 * @return
	 */
	public static String getStr(Map map, String key) {
		String str = "";
		if (isEmpty(map, key)) return str;
		
		str = map.get(key)+"";
		return str;
	}
	
	/**
	 * <pre>
	 * map 의 Integer Value 얻기
	 * (2013.10.25)
	 * </pre>
	 * @param map
	 * @param key
	 * @return
	 */
	public static Integer getInteger(Map map, String key) {
		Integer value = null;
		if (isEmpty(map, key)) return value;
		
		try {
			value = new Integer((String)map.get(key));
		} catch (Exception e) {
			logger.error("fail in getInteger() key="+key+", map="+map, e); 
		}
		return value;
	}


	/**
	 * replace Carriage Return character
	 * @param str
	 * @return
	 */
	public static String replaceCR(String str) {
		String tmp = str.replaceAll("\\r\\n", "\n");
		tmp = tmp.replaceAll("\\r", "\n");
		
		return tmp;
	}

	/**
	 * replace Carriage Return character
	 * @param str
	 * @return
	 */
	public static String replaceCRLF(String str) {
		String tmp = replaceCR(str);
		tmp = tmp.replaceAll("\\n", "");
		
		return tmp;
	}

	/**
	 * 문자열을 camel case 로 변환
	 * (첫글자는 소문자)
	 * by jhong (2013.02.06)
	 * @param str
	 * @return
	 */
	public static String convert2CamelCase(String str) {
		String result = "";
		if (StringUtils.isEmpty(str)) return result;

		if (str.indexOf("_") > 0) {
			String[] parts = str.split("_");
			for (String part : parts){
				result = result + toProperCase(part);
			}
			result = result.substring(0, 1).toLowerCase() + result.substring(1); // 첫글자는 소문자로
			
		} else {
			if (str.equals(str.toUpperCase())) // 대문자일 경우 소문자로
				result = str.toLowerCase();
			else
				result = str.substring(0, 1).toLowerCase() + str.substring(1); // 첫글자는 소문자로
		}
		return result;
	}
	
	public static String toProperCase(String str) {
	    return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
	}
	
//	/**
//	 * <pre>
//	 * List 를 json string 으로 변환 후 반환
//	 * </pre>
//	 * 
//	 * by jhong (2013.04.17)
//	 * @param list
//	 * @return
//	 * @throws Exception
//	 */
//	public static String getJsonStringFromList(List list) throws Exception {
//		ObjectMapper mapper = new ObjectMapper();
//		String str = mapper.writeValueAsString(list);
//		return str;
//	}

	/**
	 * <pre>
	 * 개행문자를 <br/> 태그로 변경
	 * </pre>
	 * 
	 * by jhong (2013.04.17)
	 * @param text
	 * @return
	 * @throws Exception
	 */
	public static String escapeNewlineToBr(String text) throws Exception {
		String str = text;
		if (StringUtils.isEmpty(str)) return str;
		
		if (str.indexOf("\r\n") >= 0 || str.indexOf("\n") >= 0) {
			str = str.replaceAll("\\r\\n", "<br/>");
			str = str.replaceAll("\\r", "<br/>");
			str = str.replaceAll("\\n", "<br/>");
		}
		return str;
	}

	/**
	 * <pre>
	 * 개행문자 escape
	 * </pre>
	 * 
	 * by jhong (2013.05.27)
	 * @param text
	 * @return
	 * @throws Exception
	 */
	public static String escapeNewline(String text) {
		String str = text;
		if (StringUtils.isEmpty(str)) return str;
		
		if (str.indexOf("\r\n") >= 0 || str.indexOf("\n") >= 0) {
			str = str.replaceAll("\\r", "\\\\r");
			str = str.replaceAll("\\n", "\\\\n");
		}
		return str;
	}

	/**
	 * <pre>
	 * 개행문자 unescape (EDI 문서 **TEXT 처리시 사용)
	 * </pre>
	 * 
	 * by jhong (2013.06.25)
	 * @param text
	 * @return
	 * @throws Exception
	 */
	public static String unescapeTextNewline(String text) {
		String str = text;
		if (StringUtils.isEmpty(str)) return str;
		
		str = StringUtil.replace(str, "\\n", "\n");
		str = StringUtil.replace(str, "\\r", "\r");
		str = StringUtil.replace(str, "\r\n", "\n"); // "\r" 제거

		return str;
	}

	/**
	 * <pre>
	 * 특정 pattern 존재여부 체크
	 * by jhong (2013.05.14)
	 * </pre>
	 * @param str
	 * @param pattern (정규표현식)
	 * @return
	 * @throws Exception
	 */
	public static boolean containsPattern(String str, String regex) throws Exception {
		boolean contains = false;
		
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		if (matcher.find()) contains = true;
		
		return contains;
	}
	
	/**
	 * <pre>
	 * json string 생성시 오류나는 문자  escape 수행
	 * </pre>
	 * 
	 * by jhong (2013.05.27)
	 * @param text
	 * @return
	 * @throws Exception
	 */
	public static String escape4JsonStr(String text) {
		String str = text;
		if (StringUtils.isEmpty(str)) return str;
		
		// 1. newline
		str = escapeNewline(str);
		
		// 2. 쌍따옴표
		if (str.indexOf("\"") >= 0) {
			str = str.replaceAll("\"", "\\\"");
		}
		return str;
	}

}
