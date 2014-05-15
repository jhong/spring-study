package net.study.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.study.common.DataObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 *
 * </pre>
 *
 * @version 2009. 03. 23
 * @author 김창교
 */
public class JSONUtil {
	/**
	* logger
	*/
	private final static Logger logger = LoggerFactory.getLogger(JSONUtil.class);

    /**
     * <pre>
     * JSONArray를 List로 변환
     * </pre>
     *
     * @param jsonArray
     * @return
     */
    public static List copyToList(JSONArray jsonArray) throws Exception {
    	List list = new ArrayList();
    	for (int i=0;jsonArray!=null && i<jsonArray.size();i++) {
    		Object object = jsonArray.get(i);
    		if (object instanceof JSONObject) {
    			list.add(copyToDataObject((JSONObject)object));
    		} else if (object instanceof JSONArray) {
    			list.add(copyToList((JSONArray)object));
    		} else {
    			list.add(String.valueOf(jsonArray.get(i)));
    		}
    	}
    	logger.debug("copyToList list size: " + list.size() + ", list: " + list);
    	return list;
    }

    /**
     * <pre>
     * JSONObject를 DataObject로 변환
     * </pre>
     *
     * @param jsonObject
     * @return
     */
    public static DataObject copyToDataObject(JSONObject jsonObject) throws Exception {
    	if (jsonObject == null) return null;

    	DataObject dataObject = new DataObject();
    	Iterator itr = jsonObject.keySet().iterator();
    	while (itr.hasNext()) {
    		String key = (String)itr.next();

    		if (jsonObject.get(key) instanceof JSONArray) {
    			dataObject.setChildList(key, copyToList(jsonObject.getJSONArray(key)));

    		} else if (jsonObject.get(key) instanceof JSONObject) {
    			List list = new ArrayList();
    			list.add(copyToDataObject(jsonObject.getJSONObject(key)));
    			dataObject.setChildList(key, list);

    		} else if (!StringUtil.isEmpty(String.valueOf(jsonObject.get(key)))) {

    			if ( isNumericByKey(key) )
					dataObject.setValue(key, digit(String.valueOf(jsonObject.get(key))));

    			else if (key.toUpperCase().indexOf("DATE") > 0)
					dataObject.setValue(key, DateUtil.toDate(String.valueOf(jsonObject.get(key))));

				else
					dataObject.setString(key, String.valueOf(jsonObject.get(key)));
    		}
    	}
    	logger.debug("copyToDataObject dataObject: " + dataObject);
    	return dataObject;
    }
    
    /**
     * <pre>
     * key 이름으로 key의 value가 숫자 타입 인지 확인
     * </pre>
     *
     * @param key
     * @return
     */
    public static boolean isNumericByKey(String key) {
    	if ("lcamt".equalsIgnoreCase(key)  ||"amt".equalsIgnoreCase(key)  || "qty".equalsIgnoreCase(key)
				|| "total_amt".equalsIgnoreCase(key) || "total_qty".equalsIgnoreCase(key)
				|| "totalamt".equalsIgnoreCase(key) || "totalqty".equalsIgnoreCase(key)
				|| "total_tax_amt".equalsIgnoreCase(key)
				|| "bill_car_amt".equalsIgnoreCase(key) || "bill_tax_amt".equalsIgnoreCase(key)
				|| "volume".equalsIgnoreCase(key) || "totalvolume".equalsIgnoreCase(key)
				|| "weight".equalsIgnoreCase(key) || "totalweight".equalsIgnoreCase(key)
				|| "grossweight".equalsIgnoreCase(key) || "totalgrossweight".equalsIgnoreCase(key)
				|| "netweight".equalsIgnoreCase(key) || "totalnetweight".equalsIgnoreCase(key)
				|| "unitprice".equalsIgnoreCase(key)
				|| "saleunitprice1".equalsIgnoreCase(key) || "saleunitprice2".equalsIgnoreCase(key) || "saleunitprice3".equalsIgnoreCase(key)
				|| "buyunitprice1".equalsIgnoreCase(key) || "buyunitprice2".equalsIgnoreCase(key) || "buyunitprice3".equalsIgnoreCase(key)
				|| "invoiceamt".equalsIgnoreCase(key)|| "packingqty".equalsIgnoreCase(key)
				|| "paymentamt".equalsIgnoreCase(key)
				|| "taxamt".equalsIgnoreCase(key) || "taxusdamt".equalsIgnoreCase(key) || "exchangerate".equalsIgnoreCase(key)
				|| "shippingamt".equalsIgnoreCase(key) || "insuranceamt".equalsIgnoreCase(key)
				|| "totalshippingcharge".equalsIgnoreCase(key)
				|| (key != null && key.toLowerCase().endsWith("amt"))
				|| (key != null && key.toLowerCase().endsWith("amt1"))
				|| (key != null && key.toLowerCase().endsWith("amt2"))
				|| (key != null && key.toLowerCase().endsWith("cost"))
				|| (key != null && key.toLowerCase().endsWith("qty"))
				|| (key != null && key.toLowerCase().endsWith("qty1"))
				|| (key != null && key.toLowerCase().endsWith("qty2"))
				|| (key != null && key.toLowerCase().endsWith("rate"))
				|| (key != null && key.toLowerCase().endsWith("unitprice"))
				|| (key != null && key.toLowerCase().endsWith("unitprice1"))
				|| (key != null && key.toLowerCase().endsWith("unitprice2"))) {
    		return true;
    	} else {
    		return false;
    	}
    }

	/**
     * <pre>
	 * String 형을 ","가 없는 숫자로 변환한다.
	 * </pre>
	 *
	 * @param String digit 금액
	 * @return Double 포맷된 금액 문자열
	 */
    public static Double digit(String digit) throws Exception {
    	Double s = null;
    	try {
    		s = Double.valueOf(StringUtil.replace(digit, ",", ""));
    		
    	} catch (NumberFormatException e) {
    		// NumberFormatException 일 경우 throw 하지 않고 로그만 남기도록 수정 by jhong (2013.06.10)
    		logger.error("fail in digit() digit="+digit+", "+e.getMessage());
    	}
        return s;
    }

    /**
     * <pre>
     * JSONArray 문자열을 DataObject List로 변환
     * </pre>
     * 
     * by jhong (2013.02.22)
     * @param str
     * @return
     * @throws Exception
     */
    public static List jsonArray2List (String str) throws Exception {
		List list = new ArrayList();
		JSONArray jsonArray = JSONArray.fromObject(str);
		for (int i=0; jsonArray!= null && i< jsonArray.size(); i++) {
			DataObject bizData = JSONUtil.copyToDataObject(jsonArray.getJSONObject(i));
			list.add(bizData);
		}
		return list;
    }

}
