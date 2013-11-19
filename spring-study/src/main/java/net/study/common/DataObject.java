package net.study.common;

import java.io.Reader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.study.util.DateUtil;
import net.study.util.StringUtil;
import oracle.sql.CLOB;

import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 데이터
 *
 * @author 김창교
 * @since 2006. 6. 15.
 * @version 1.0
 *
 */
public class DataObject extends LinkedHashMap
{
	private static final long serialVersionUID = 1L;

	/**
	* logger
	*/
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	/**
    * 하위 문서 정보, value type : List
    */
    private LinkedHashMap childObject = new LinkedHashMap();

    /**
    * 이미지와 같은 정보 저장, value type : byte[]
    */
    private LinkedHashMap binaryData = null;

    /**
    * binary data의 mime type
    */
    private LinkedHashMap binaryMimeCode = null;

//    /**
//    * 현재 문서의 항목별 정보 저장, value type : input type
//    */
//    private LinkedHashMap attribute = null;

    /**
    * 현재 문서명(Table 명)
    */
    private String objName = null;

    /**
     * 생성자
     *
     */
    public DataObject() {
    }

	/**
	 * 생성자
	 *
	 */
	public DataObject(String objName) throws Exception
	{
		this.init();
		this.objName = objName;
    }

	/**
	 * 생성자
	 *
	 * @param map
	 */
	public DataObject(LinkedHashMap map) {
		super(map);
	}

    /**
     * key 에 대하여 Camel Case 변환하여 super.put을 호출한다.
     * @param key
     *        - '_' 가 포함된 변수명
     * @param value
     * @return 
     */
    @Override
    public Object put(Object key, Object value) {
    	
    	String keyStr = StringUtil.convert2CamelCase((String) key);
//    	logger.info("put() key={}", keyStr+", value="+value+", this="+this);
    	return super.put(keyStr, value);
    }

    /**
     * Class 초기화
     *
     * @throws Exception
     */
    private void init() throws Exception
    {
    	this.binaryData = new LinkedHashMap();
    	this.binaryMimeCode = new LinkedHashMap();
//    	this.attribute = new LinkedHashMap();
    	this.objName = "";
    }

	/**
	 * 데이터 Object 의 이름 set
	 *
	 * @param objName
	 * @throws Exception
	 */
    public void setObjName(String objName) throws Exception
    {
    	this.objName = objName;
    }

	/**
	 * 데이터 Object 의 이름 get
	 *
	 * @return : 현 개체의 Object Name 리턴
	 * @throws Exception
	 * @see
	 */
    public String getObjName() throws Exception
    {
    	return this.objName;
    }

    public void createChildList(String objName, Map[] list) throws Exception
    {
    	if (objName == null || "".equals(objName))
    		throw new Exception("개체 식별자가 비었습니다.");

    	if (list == null)
    		throw new Exception("Map 개체가 NULL 입니다.");

		for(int i=0;i<list.length;i++)
		{
			DataObject child = createChildVO(objName);
			child.putAll(new LinkedHashMap(list[i]));
		}
    }

    /**
     * <pre>
     * child 객체로 list를 등록
     * </pre>
     *
     * @param childName
     * @param list
     * @throws Exception
     */
    public void setChildList(String childName, List list) throws Exception {
    	if (childName == null || "".equals(childName))
    		throw new Exception("개체 식별자가 비었습니다.");

    	if (list == null)
    		throw new Exception("Map 개체가 NULL 입니다.");

    	if (this.childObject == null) {
    		this.childObject = new LinkedHashMap();
    	}

    	this.childObject.put(childName, list);
    }

	/**
	 * 입력된 경로에 해당하는 Child Object를 생성해서 childObject에 등록하고, 해당 child Object를 return 한다.
	 *
	 * @param objName : Child Object의 objName
	 * @return : 생성된 자식 VO 인스턴스 리턴
	 * @throws Exception
	 */
    public DataObject createChildVO(String objName) throws Exception
    {
    	String methodName = "createChildVO";
    	try{
            //1. 입력값이 null인경우
        	if (objName == null || "".equals(objName))
            {
            	throw new Exception("The objName parameter is null.");
            }

            //2. childObject에서 해당 child의 list 추출
        	DataObject child = null;
            List childList = null;

            if (this.childObject.containsKey(objName))
        	{
        		//2.1. 기존 child list 가 있는 경우
            	childList = (List)this.childObject.get(objName);
        	}
            else
            {
        		//2.2. 기존 child list 가 없는  경우
            	childList = new ArrayList();
            	this.childObject.put(objName, childList);
            }

            //3. child Object 생성
            child = new DataObject(objName);

            //4. child list에 추가
            childList.add(child);

            return child;
    	}
    	catch(Exception e)
    	{
//    		throw new Exception("CMME0011", e, this.CLASS_NAME, methodName);
			throw e;
    	}
    }

    /**
     * 입력된 이름에 해당하는 모든 자식 VO 개체를 리스트로 리턴
     *
     * @param objName : 자식 Object Name
     * @return : 자식 VO 리스트
     * @throws Exception
     */
    public ArrayList getChildObjectList(String objName) throws Exception
    {
    	//1. 입력값이 null인 경우
    	if (objName == null || "".equals(objName))
    	{
    		throw new Exception("[VO.getChildObjectList] The objName parameter is null..");
    	}

    	ArrayList objList = new ArrayList();

    	//2. 해당 objName의 child object가 있는 경우
    	if (this.childObject.containsKey(objName))
    	{
    		//2.1. 해당 child List 추출
    		List childList = (List)this.childObject.get(objName);
    		objList.addAll(childList);
    	}

    	return objList;
    }

    /**
     * 입력된 이름에 해당하는 모든 자식 VO 개체를 리스트로 리턴
     *
     * @param objName : 자식 Object Name
     * @return : 자식 VO 리스트
     * @throws Exception
     */
    public List getChildObjectList(String objName, boolean isCopy) throws Exception
    {
    	if (isCopy) {
    		return getChildObjectList(objName);
    	}

    	//1. 입력값이 null인 경우
    	if (objName == null || "".equals(objName))
    	{
    		throw new Exception("[VO.getChildObjectList] The objName parameter is null..");
    	}

    	List objList = null;

    	//2. 해당 objName의 child object가 있는 경우
    	if (this.childObject.containsKey(objName))
    	{
    		//2.1. 해당 child List 추출
    		objList = (List)this.childObject.get(objName);

    	} else {
        	objList = new ArrayList();
        	this.childObject.put(objName, objList);
    	}

    	return objList;
    }

    /**
     * <pre>
     * Child List를 json string으로 변환하여 반환
     * </pre>
     *
     * @param objName
     * @return
     * @throws Exception
     */
    public String getChildListAsJSON(String objName) throws Exception {
    	String jsonString = "";
    	if (this.childObject.containsKey(objName)) {
    		for (int i=0;i<this.getChildObjectCount(objName);i++) {
    			this.getChildObject(objName, i).toLowerCaseKeyValue();
    		}

    		JSONArray jsonArray = JSONArray.fromObject((List)this.childObject.get(objName));
    		jsonString = jsonArray.toString();
    	}

    	return jsonString;
    }

	/**
	 * 입력된 이름에 해당하는 child Object의 list를 반환한다.
	 *
	 * @param objName : 현재 Object의 objName
	 * @return : 검색된 VO 배열 리턴. 검색된 결과가 없는 경우 0 배열 리턴
	 * @throws Exception
	 * @see
	 */
    public DataObject[] getChildObjectArray(String objName) throws Exception
    {
    	//1. 입력값이 null인 경우
    	if (objName == null || "".equals(objName))
    	{
    		throw new Exception("[VO.getChildObjectList] The objName parameter is null..");
    	}

    	//2. 해당 objName의 child object가 있는 경우
		System.out.println("[VO.getChildObjectList] 2. 해당 objName의 child object존재 여부 확인, objName: " + objName);
    	if (this.childObject.containsKey(objName))
    	{
    		//2.1. 해당 child List 추출
    		System.out.println("[VO.getChildObjectList] 2.1. 해당 child List 추출");
    		List childList = (List)this.childObject.get(objName);
    		System.out.println("[VO.getChildObjectList] childList: " + childList);

    		//2.2. List의 size가 1인 경우
    		if (childList.size() > 0)
    		{
    			DataObject[] tmpObj = (DataObject[])childList.toArray(new DataObject[childList.size()]);
    			return tmpObj;
    		}
    		//2.3. List의 size가 0인 경우
    		else
    		{
    			DataObject[] tmpObj = new DataObject[0];
    			return tmpObj;
    		}
    	}
    	//3. 해당 objName의 child object가 없는 경우
    	else
    	{
    		//3.1. 빈 object 배열 return
    		System.out.println("[VO.getChildObjectList] 3.1. 빈 object 배열 return");
    		DataObject[] tmpObj = new DataObject[0];
			return tmpObj;
    	}
    }

//    /**
//     * 현재 Object에 등록된 Attribute중 유효하지 않은 value를 삭제
//     *
//     * @param trLog : 송수신로그정보
//     * @throws Exception
//     */
//    public void deleteNullAttribute(TrLogVO trLog) throws Exception {
//    	this.deleteNullAttribute(1, trLog);
//    }

//	/**
//	 * 현재 Object에 등록된 Attribute중 유효하지 않은 value를 삭제
//	 *
//	 * @param chkType : 0 = null인 경우만 삭제, 1 = String 은 null/"", number인 경우 0, 기타 type = null
//	 * @param trLog : 송수신로그정보
//	 * @throws Exception
//	 */
//	public void deleteNullAttribute(int chkType, TrLogVO trLog) throws Exception
//	{
//		String methodName = "deleteNullAttribute";
//
//		try{
//			//1. attribute의 size가 0인 경우
//			if (this.attribute.size() <= 0)
//			{
//				return;
//			}
//
//			//2. attribute 의 key set 추출
//			Iterator iset = this.attribute.keySet().iterator();
//
//			//3. attribute loop 시작
//			ArrayList delKeys = new ArrayList();
//			while (iset.hasNext())
//			{
//				//3.1. attribute에서 value 추출
//				String key = (String)iset.next();
//				Object value = this.attribute.get(key);
//
//				//3.2. attribute의 value type이 String 인지 여부 확인
//				if (value == null)
//				{
//					delKeys.add(key);
//					continue;
//				}
//				else if ("java.lang.String".equals(value.getClass().getName()))
//				{
//					//3.2.1. attribute의 value type이 String 인 경우
//					String tmpValue = (String)value;
//					if (tmpValue == null || "".equals(tmpValue))
//					{
//						delKeys.add(key);
//						continue;
//					}
//				}
//				else if ("int, double, float, java.lang.Integer, java.lang.Double, java.lang.Float, java.math.BigDecimal".indexOf(value.getClass().getName()) >= 0)
//				{
//					//3.2.2. type이 number 인 경우(int, double, float, Integer, Double, Float, BigDecimal)
//					if (value == null)
//					{
//						delKeys.add(key);
//						continue;
//					}
//
//					String tmpValue = String.valueOf(value);
//					if ("0".equals(tmpValue))
//					{
//						delKeys.add(key);
//						continue;
//					}
//				}
//				else
//				{
//					//3.2.3. type이 기타인 경우
//					if (value == null)
//					{
//						delKeys.add(key);
//						continue;
//					}
//				}
//			}
//			//4. attribute loop 끝
//
//			//5. delKey list에 저장된 key의 값을 삭제 Loop 시작
//			for (int i=delKeys.size()-1;i>=0;i--)
//			{
//				this.attribute.remove(delKeys.get(i));
//			}
//			//6. delKey list에 저장된 key의 값을 삭제 Loop 끝
//		}
//		catch(Exception e)
//		{
//			throw new Exception("CMME0011", e, this.CLASS_NAME, methodName);
//		}
//	}


//	/**
//	 * 현재 Object에 등록된 BinaryData중 유효하지 않은 data를 삭제
//	 *
//	 * @param trLog : 송수신로그정보
//	 * @throws Exception
//	 */
//	public void deleteNullBinaryData(TrLogVO trLog) throws Exception
//	{
//		String methodName = "deleteNullBinaryData";
//
//		try{
//			//1. binaryData의 size가 0인 경우
//			System.out.println("[VO.deleteNullBinaryData] 1. binaryData의 size가 0인 경우");
//			if (this.binaryData.size() <= 0)
//			{
//				return;
//			}
//
//			//2. binaryData 의 key set 추출
//			System.out.println("[VO.deleteNullBinaryData] 2. binaryData 의 key set 추출");
//			System.out.println("[VO.deleteNullBinaryData] this.binaryData: " + this.binaryData);
//			Iterator iset = this.binaryData.keySet().iterator();
//			ArrayList delKeys = new ArrayList();
//
//			//3. binaryData loop 시작
//			System.out.println("[VO.deleteNullBinaryData] 3. binaryData loop 시작");
//			while (iset.hasNext())
//			{
//				//3.1. binaryData에서 value 추출
//				System.out.println("[VO.deleteNullBinaryData] 3.1. binaryData에서 value 추출");
//				String key = (String)iset.next();
//				Object value = this.binaryData.get(key);
//
//				//3.2. value 가 null인지 확인
//				System.out.println("[VO.deleteNullBinaryData] 3.2. value 가 null인지 확인 ");
//				if (value == null)
//				{
//					//3.2.1. value 가 null 인 경우
//					this.binaryData.remove(key);
//					continue;
//				}
//
//				//3.2. byte[]을 추출하여 길이 확인
//				System.out.println("[VO.deleteNullBinaryData] 3.2. byte[]을 추출하여 길이 확인");
//				byte[] tmpValue = (byte[])value;
//				if (tmpValue.length <= 0)
//				{
//					delKeys.add(key);
//					continue;
//				}
//			}
//			//4. binaryData loop 끝
//			System.out.println("[VO.deleteNullBinaryData] 4. binaryData loop 끝");
//
//			//5. delKeys에 등록된 binary Data 삭제
//			System.out.println("[VO.deleteNullBinaryData] 5. delKeys에 등록된 binary Data 삭제");
//			System.out.println("[VO.deleteNullBinaryData] delKeys size: " + delKeys.size());
//			for(int i=delKeys.size()-1;i>=0;i--)
//			{
//				this.binaryData.remove(delKeys.get(i));
//			}
//		}
//		catch(Exception e)
//		{
//			throw new Exception("CMME0011", e, this.CLASS_NAME, methodName);
//		}
//	}

//	/**
//	 * 현재 Object에 등록된 child Object중 Dumy Object를 삭제
//	 *
//	 * @param trLog : 송수신로그정보
//	 * @throws Exception
//	 */
//	public void deleteNullChildObejct(TrLogVO trLog) throws Exception
//	{
//		String methodName = "deleteNullChildObject";
//
//		try{
//			//0. binaryData의 size가 0인 경우
//			if (this.childObject.size() <= 0)
//			{
//				return;
//			}
//
//			//1. child object의 list의 key를 추출한다.
//			Iterator childKey = this.childObject.keySet().iterator();
//			ArrayList delKeys = new ArrayList();
//
//			//2. 추출된 key list에 따라 loop 시작
//			while (childKey.hasNext())
//			{
//				//2.1. key에 의해 추출된 List로 loop 시작
//				String vKey = (String)childKey.next();
//				List childList = (List)this.childObject.get(vKey);
//				ArrayList delIdx = new ArrayList();
//				for (int i=0;i<childList.size();i++)
//				{
//					//2.1.1 List에서 object 추출
//					DataObject childObject = (DataObject)childList.get(i);
//
//					//2.1.2 추출된 Object의 deleteNullAttribute 실행
//					childObject.deleteNullAttribute(trLog);
//
//					//2.1.3 추출된 Object의 deleteNullBinaryData 실행
//					childObject.deleteNullBinaryData(trLog);
//
//					//2.1.4 추출된 Object의 deleteNullChildObject 실행
//					childObject.deleteNullChildObejct(trLog);
//
//					//2.1.5 추출된 Object의 isNull 값이 true 이면 해당 Object 삭제
//					if (childObject.isNull())
//					{
//						delIdx.add(new Integer(i));
//					}
//				}
//				//2.2. key에 의해 추출된 List로 loop 끝
//
//				//2.3. delIdx에 등록된 TrObject를 List에서 삭제 Loop 시작
//				for (int idx=delIdx.size()-1;idx>=0;idx--)
//				{
//					Integer vIdx = (Integer)delIdx.get(idx);
//					childList.remove(vIdx.intValue());
//				}
//
//				//2.4. List의 크기를 확인해서 길이 확인
//				if (childList.size() <= 0)
//				{
//					//2.4.1. List의 길이가 0인 경우
//					delKeys.add(vKey);
//				}
//			}
//			//3. 추출된 key list에 따라 loop 끝
//
//			//4. child Object에서 빈 Object 삭제
//			for (int i=delKeys.size()-1;i>=0;i--)
//			{
//				this.childObject.remove(delKeys.get(i));
//			}
//		}
//		catch (Exception e)
//		{
////			throw new Exception("CMME0011", e, this.CLASS_NAME, methodName);
//			throw e;
//		}
//	}

	/**
	 * 현재 Object에 등록된 child Object중 입력된 objName의 입력된 Index에 해당하는 child Object를 반환
	 *
	 * @param objName : 현재 Object의 objName 명
	 * @param idx : 요청하는 Object의 index
	 * @return : childObject
	 * @throws Exception
	 */
	public DataObject getChildObject(String objName, int idx) throws Exception
	{
		if (objName == null || "".equals(objName))
		{
			throw new Exception("[VO.getChildObject] The objName parameter is null.");
		}

		if (idx < 0)
		{
			throw new Exception("[VO.getChildObject] The idx parameter is under -1");
		}

		List childList = (List)this.childObject.get(objName);
		if (childList != null && childList.size() > idx) {
			return (DataObject)childList.get(idx);
		} else {
			return null;
		}
	}


	/**
	 * 현재 Object에 등록된 child Object중 입력된 Path에 해당하는 child object의 개수를 반환한다.
	 *
	 * @param objName : 현재 Object의 objName 명
	 * @return : count
	 * @throws Exception
	 * @see
	 */
	public int getChildObjectCount(String objName) throws Exception
	{
		int childCount = 0;

		if (this.childObject.containsKey(objName))
		{
			List childList = (List)this.childObject.get(objName);
			childCount = childList.size();
		}

		return childCount;
	}

	/**
	 * Object가 Dumy Object인지 확인한다.
	 *
	 * @return : Dumy Object인 경우 true 리턴, 반대의 경우 false 리턴
	 * @throws Exception
	 */
	public boolean isNull() throws Exception
	{
		boolean isnull = true;

		//1. attribute가 비었는지 확인한다.
		if (!this.isEmpty())
		{
			isnull = false;
		}

		//2. childObject가 비었는지 확인한다.
		if (!this.childObject.isEmpty())
		{
			isnull = false;
		}

		//3. binaryData가 비었는지 확인한다.
		if (!this.binaryData.isEmpty())
		{
			isnull = false;
		}

		return isnull;
	}

	/**
	 * attribute에 값을 설정한다.
	 *
	 * @param key
	 * @param value
	 * @throws Exception
	 */
	public void setString(String key, String value) throws Exception
	{
		this.setString(key, value, false);
	}

	/**
	 * <pre>
	 * 입력된 list의 object에 대한 toString 값으로 붙여서 value를 설정한다. String 값을 Add할때 /n를 사용한다.
	 * </pre>
	 *
	 * @param key
	 * @param list
	 * @throws Exception
	 */
	public void setString(String key, List list) throws Exception {
		this.setString(key, list, "\n");
	}

	/**
	 * <pre>
	 * 입력된 list의 object에 대한 toString 값으로 붙여서 value를 설정한다. String 값을 Add할때 입력된 Delimiter를 사용한다.
	 * </pre>
	 *
	 * @param key
	 * @param list
	 * @throws Exception
	 */
	public void setString(String key, List list, String delimiter) throws Exception {
		if (list == null || list.isEmpty()) return;

		for (int i=0;i<list.size();i++) {
			if (i>0) this.setString(key, delimiter, true);
			this.setString(key, String.valueOf(list.get(i)), true);
		}
	}


	/**
	 * attribute에 값을 설정한다.
	 *
	 * @param key
	 * @param value
	 * @throws Exception
	 */
	public void setString(String key, String value, boolean isAdd) throws Exception
	{
		if (key == null)
		{
			throw new Exception("[VO.setString] The key value is null.");
		}

		if (this.containsKey(key) && isAdd)
		{
			String tmpStr = (String)this.get(key);
			this.put(key, tmpStr + value);
		}
		else
		{
			this.put(key, value);
		}
	}


	/**
	 * 현재 Object의 attribute에 값을 String으로 변환하여 반환한다.
	 *
	 * @param key : attribute key name
	 * @return : String 타입의 attribute value 리턴
	 * @throws Exception
	 */
	public String getString(String key)
	{
		if (key == null || "".equals(key))
		{
//			throw new Exception("The key value is null.");
			return "";
		}

		Object orgValue = get(key);
		String value = "";
		if (orgValue != null) {
			value = String.valueOf(orgValue);
		}

		return value;
	}

	/**
	 * 문자열에서 입력된 index에 해당하는 문자를 string으로 리턴
	 * index < 0 인경우 "", index > 문자열길이 인 경우 "" 리턴
	 * @param key
	 * @param index
	 * @return
	 */
	public String getString(String key, int index) {
		if (index < 0)
			return "";
		
		String value = this.getString(key);
		String returnValue = "";
		
		if (value.length() > index) {
			returnValue = value.substring(index, index+1);
		}
		
		return returnValue;
	}
	
	/**
	 * 문자열에서 입력된 시작 index와 끝 index 에 해당하는 문자열 리턴
	 * 문자열의 길이에 맞지 않는 경우 "" 리턴
	 * @param key
	 * @param beginindex
	 * @param endindex
	 * @return
	 */
	public String getString(String key, int beginindex, int endindex) {
		if (beginindex < 0 || endindex < 0 || endindex <= beginindex)
			return "";
		
		String value = this.getString(key);
		String returnValue = "";
		
		if (value.length() >= endindex) {
			returnValue = value.substring(beginindex, endindex);
		} else if (value.length() > beginindex && value.length() < endindex) {
			returnValue = value.substring(beginindex, value.length());
		}
		
		return returnValue;
	}

	/**
	 * 현재 Object의 attribute에 값을 String으로 변환하여 반환한다.
	 *
	 * @param key : attribute key name
	 * @return : String 타입의 attribute value 리턴
	 * @throws Exception
	 */
	public String getClobString(String key) throws Exception
	{
		if (key == null || "".equals(key))
		{
//			throw new Exception("The key value is null.");
			return "";
		}

		String value = "";
		CLOB clob = (CLOB) this.get(key);
		if(clob != null){
			Reader rd = clob.characterStreamValue();
			StringBuffer sb = new StringBuffer();
		    char[] buf = new char[1024];
		    int readcnt;
		    while ((readcnt = rd.read(buf, 0, 1024)) != -1) {
		    	// 스트림으로부터 읽어서 스트링 버퍼에 넣는다.
		    	sb.append(buf, 0, readcnt);
		    }
		    rd.close();
		    value = sb.toString();
		}
		return value;
	}

	/**
	 * attribute에 Object Type의 값을 설정한다.
	 *
	 * @param key
	 * @param value
	 * @throws Exception
	 */
	public void setValue(String key, Object value) throws Exception
	{
		if (key == null)
		{
			throw new Exception("[VO.setValue] key value is null.");
		}

		this.put(key, value);
	}

	/**
	 * attribute의 Object Type의 값을 반환한다.
	 *
	 * @param key : attribute key name
	 * @return : attribute value 리턴
	 * @throws Exception
	 */
	public Object getValueObject(String key) throws Exception
	{
		if (key == null || "".equals(key))
		{
			throw new Exception("[VO.getValue] The key parameter is null.");
		}

		Object value = this.get(key);

		return value;
	}

	/**
	 * objName에 해당하는 Child Object가 존재하는지 확인
	 *
	 * @param objName : 확인할 자식 object name
	 * @return : 자식 인스턴스가 존재하는 경우 true 리턴, 반대의 경우 false 리턴
	 * @throws Exception
	 */
	public boolean existChildObject(String objName) throws Exception
	{
		if (this.getChildObjectCount(objName) > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * attiName에 해당하는 attribute가 존재하는지 확인
	 *
	 * @param attiName : attribute key 명
	 * @return : 존해하는 경우 true 리턴, 반대의 경우 false 리턴
	 * @throws Exception
	 */
	public boolean existAttribute(String attiName) throws Exception
	{
		if (this.containsKey(attiName))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * binaryData Getter
	 *
	 * @return binaryData
	 * @throws Exception
	 */
	public LinkedHashMap getBinaryData()
	{
		return this.binaryData;
	}

	/**
	 * childObject Getter
	 *
	 * @return : childObject
	 * @throws Exception
	 */
	public LinkedHashMap getChildObject()
	{
		return this.childObject;
	}


	/**
	 * 입력된 key로 입력된 byte[]를 저장
	 *
	 * @param key : binary data의 key name
	 * @param bData : binary data
	 */
	public void setBinaryData(String key, byte[] bData)
	{
		this.binaryData.put(key, bData);
	}

	/**
	 * binaryMimeCode getter
	 *
	 * @param key : binaryMineCode key name
	 * @param mimeCode
	 */
	public void setBinaryMimeCode(String key, String mimeCode)
	{
		this.binaryMimeCode.put(key, mimeCode);
	}

	/**
	 * binaryMimeCode LinkedHashMap Getter
	 *
	 * @return : binaryMimeCode
	 */
	public LinkedHashMap getBinaryMimeCode()
	{
		return binaryMimeCode;
	}

	/**
	 * binaryMimeCode setter
	 *
	 * @param binaryMimeCode
	 */
	public void setBinaryMimeCode(LinkedHashMap binaryMimeCode)
	{
		this.binaryMimeCode = binaryMimeCode;
	}

	/**
	 * binaryData Getter
	 *
	 * @param key : key name
	 * @return : binaryData
	 */
	public byte[] getBinaryData(String key)
	{
		if (this.binaryData.containsKey(key))
		{
			return (byte[])this.binaryData.get(key);
		}
		else
		{
			return null;
		}
	}

	/**
	 * binaryMimeCode Getter
	 *
	 * @param key : key name
	 * @return : binaryMimeCode
	 */
	public String getBinaryMimeCode(String key)
	{
		if (this.binaryMimeCode.containsKey(key))
		{
			return (String)binaryMimeCode.get(key);
		}
		else
		{
			return null;
		}
	}

//	/**
//	 * <pre>
//	 * 값을 int로 리턴
//	 * </pre>
//	 *
//	 * @param key
//	 * @return
//	 */
//	public int getInt(String key) throws Exception {
//		if (this.get(key) != null) {
//			Integer intValue = (Integer)CopyUtil.convertTypeNecessary(this.get(key), Integer.class);
//			return intValue.intValue();
//		} else {
//			return 0;
//		}
//	}

	/**
	 * <pre>
	 * 입력된 key의 value를 String으로 반환한다.
	 * </pre>
	 *
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String getValue(String key) throws Exception {
		return this.getString(key);
	}

	/**
	 * <pre>
	 * 입력된 key의 값을 String[]로 리턴
	 * key에 저장된 정보가 String[]이 아닌 경우 String[]로 변환
	 * </pre>
	 *
	 * @param key
	 * @return
	 */
	public String[] getValues(String key) {
		String[] result = null;

		if (!this.containsKey(key) || this.get(key) instanceof List
				|| this.get(key) instanceof Map) {
			result = new String[0];
		} else if (this.get(key) instanceof String[]) {
			result = (String[])this.getValues(key);
		} else if (this.get(key) instanceof Object[]) {
			Object[] objectArray = (Object[])this.get(key);
			result = new String[objectArray.length];
			for (int i=0;i<objectArray.length;i++) {
				result[i] = String.valueOf(objectArray[i]);
			}
		} else if (this.get(key) instanceof String) {
			result = new String[1];
			result[0] = (String)this.get(key);
		} else if (this.get(key) instanceof Object) {
			result = new String[1];
			result[0] = String.valueOf(this.get(key));
		}

		return result;
	}

	/**
	 * 키와값 추가
	 * @param key
	 * @param data
	 */
	public void addValue(String key, String data) {
		if (data == null || data.equals("")) return;
		///logger.debug("#1:" + key + ":" + this.get(key));
		if (this.get(key) == null)
			this.put(key,data);
		else
			this.put(key, String.valueOf(this.get(key)) + "\n"+ data);
	}

	public void addValue(String key, int data) {
		this.put(key,String.valueOf(data));
	}

	public void addValue(String key, Timestamp data) {
		this.put(key,data);
	}

	public void addValue(String key, Double data) {
		this.put(key,data);
	}

	public void addValue(String key, Float data) {
		this.put(key,data);
	}

	public void addValue(String key, Integer data) {
		this.put(key,data);
	}

	public void addKey(String key) {
		this.put(key,"");
	}

	public void addValue(String key, Object data){
		this.put(key,data);
	}

	public void appendString(String key, String data) throws Exception{
		this.setString(key, data, true);
	}
	/**
	 * 키에 의한 정수값 반환
	 * @param key
	 * @return
	 */
	public int getIntValue(String key) {
		/* 기존 로직 주석처리 by jhong (2013.11.12)
		 * 
		key = key.toUpperCase();
		if (this.get(key) == null ) {
			return 0;
		} else
			return Integer.parseInt((String)this.get(key));
		*/
		
		Integer result = null;
		if (this.get(key) != null) {
			result = Integer.parseInt(this.get(key)+"");
			
		} else if (this.get(key.toUpperCase()) != null) {
			result = Integer.parseInt(this.get(key.toUpperCase())+"");
			
		} else {
			return 0;
		}
		
		return result;
	}

	/**
	* 키에 의한 포맷된 스트링값 반환
	* @param key
	* @param format
	* @return
	*/
	public String getIntFormatValue(String key, String format) {
		key = key.toUpperCase();
		if (this.containsKey(key)) {
			if (this.get(key) == null ) {

				return "";
			} else

				return StringUtil.getDecimalToCommaString(Integer.parseInt((String)this.get(key)), format);
		}else
			return "";
	}

	/**
	* 키에 의한 포맷된 스트링값 반환
	* @param key
	* @return
	*/
	public String getIntFormatValue(String key) {
		if (this.containsKey(key)) {
			if (this.get(key) == null ) {

				return "";
			} else
				return getIntFormatValue(key,"#,###");
		}else{
			return "";
		}
	}

	/**
	* 키에 의한 포맷된 더블값 반환
	* @param key
	* @return
	*/
	public Double getDoubleValue(String key) throws Exception {
		key = key.toUpperCase();
		if (this.get(key) == null ) {

			return Double.valueOf("0");
		} else{
			return Double.valueOf(getValue(key));
		}
	}

	/**
	* 키에 의한 포맷된 스트링값 반환
	* @param key
	* @param format
	* @return
	*/
	public String getDoubleFormatValue(String key, String format) throws Exception {
		key = key.toUpperCase();
		if (this.get(key) == null) {

			return "";
		} else{
			return StringUtil.getDecimalToCommaString(Double.valueOf(getValue(key)), format);
		}

	}

	/**
	* 키에 의한 포맷된 스트링값 반환
	* @param key
	* @return
	*/
	public String getDoubleFormatValue(String key) throws Exception {
		if (this.get(key) == null ) {

		}
		return getDoubleFormatValue(key, "#,###.####");
	}

//	/**
//	 * <pre>
//	 * 통화단위에 따른 소수점 처리를 하여 String으로 리턴
//	 * </pre>
//	 *
//	 * @param key
//	 * @param currKey
//	 * @return
//	 */
//	public String getDoubleFormatValueByCurr(String key, String currKey) throws Exception {
//
//		if (this.get(key) != null) {
////			return NumberUtil.getFormedMoney(this.getDoubleValue(key), this.getString(currKey));
//			return NumberUtil.getFormattedAmtByCurr(this.getValue(key), this.getString(currKey)); // NumberUtil 수정 (2013.06.20)
//		} else {
//			return "";
//		}
//	}

	/**
	 * <pre>
	 * ",###.####원" 포멧의 String으로 리턴
	 * </pre>
	 *
	 * @param key
	 * @param currKey
	 * @return
	 */
	public String getWonFormatValue(String key) throws Exception {

		if (this.get(key) != null) {
			if(!"0".equals(getString(key))){
				return getDoubleFormatValue(key, "#,###.####") +"원";
			} else{
				return "0원";
			}
		} else {
			return "&nbsp;";	// jsp 의 form 객체로 주로 이용하므로 공백 표시
		}
	}

//	/**
//	*
//	* @param key
//	* @return
//	*/
//	public Timestamp getTimestampValue(String key) {
//		if (this.get(key) == null ) {
//
//		}
//		return (Timestamp)this.get(key);
//	}
//
//	/**
//	*
//	* @param key
//	* @param format
//	* @return
//	*/
//	public String getTimestampFormatValue(String key, String format) {
//		if (!StringUtils.isEmpty((String)this.get(key)))
//			return StringUtil.getDateString((Timestamp)this.get(key), format);
//		else
//			return "";
//	}
//
//	/**
//	*
//	* @param key
//	* @return
//	*/
//	public String getTimestampFormatValue(String key) {
//	   String dateFormat = DateUtil.PATTERN_DATETIME_TO_STRING;
//		if (this.get(key) == null ) {
//
//			return "";
//		} else if (this.get(key) instanceof String) {
//			return (String)this.get(key);
//		} else
//			return getTimestampFormatValue(key, dateFormat);
//	}

	/**
	*
	* @param key
	* @return
	*/
	public String getDateFormatValue(String key) {
		String dateFormat = DateUtil.PATTERN_DATE_TO_STRING;
		Object value = this.get(key);

		if (value == null ) {
			return "";
		} else if (value instanceof String) {
			return (String)this.get(key);
		} else
			return getDateFormatValue(key, dateFormat);
	}

	/**
	*
	* @param key
	* @param format
	* @return
	*/
	public String getDateFormatValue(String key, String format) {
		if (this.get(key) == null) {
			return null;
		} else {
			return DateFormatUtils.format((Date)this.get(key), format);
		}
	}

	 /**
	  * 사업자번호를 입력받아서 마스킹 한다
	  * @param data
	  * @return 대쉬(-)가 들어간 사업자번호
	  */
	 public String getRegistNO(int data) {
	     return StringUtil.getRegistNO(data);
	 }

	 /**
	  *
	  * @param data
	  * @return 대쉬(-)가 들어간 사업자번호
	  */
	 public String getRegistNO(String key) throws Exception {
	     return StringUtil.getRegistNO(getValue(key));
	 }


	 /**
	  * HS코드를 표시
	  * @param data
	  * @return 포멧된HS코드
	  */
	 public String getHSString(String key) throws Exception {
	 	return StringUtil.getHSString( getValue(key) );
	 }

	 /**
	 * <pre>
	 * get을 했을 경우 key값의 대소문자 구분없이 Map에서 값을 가져올 수 있도록 get method 재정의
	 * </pre>
	 *
	 * @param key
	 * @return
	 */
	public Object get(String key) {
		Object value = null;
		if (this.containsKey(key)) { // 순서변경 by jhong (2013.02.12)
			value = super.get(key);
			
		} else if (this.containsKey(StringUtil.convert2CamelCase(key))) { // by jhong (2013.02.12)
			value = super.get(StringUtil.convert2CamelCase(key));
			
		} else if (this.containsKey(key.toUpperCase())) {
			value = super.get(key.toUpperCase());
			
		} else if (this.containsKey(key.toLowerCase())){
			value = super.get(key.toLowerCase());
		}
		return value;
	 }

	/**
	 * <pre>
	 * 현재 Map 에 등록된 Key의 값을 모두 소문자로 변경한다.
	 * </pre>
	 *
	 */
	public void toLowerCaseKeyValue() {
		Iterator itr = this.keySet().iterator();
		List keyList = new ArrayList();
		Map tempMap = new LinkedHashMap();
		while(itr.hasNext()) {
			String keyName = (String)itr.next();
			String newKeyName = keyName.toLowerCase();
			if (!keyName.equals(newKeyName)) {
				tempMap.put(newKeyName, this.get(keyName));
				keyList.add(keyName);
			}
		}
		for(int i=0;i<keyList.size();i++) {
			this.remove(keyList.get(i));
		}
		this.putAll(tempMap);

		this.toLowerCaseKeyChildList();
	}

	/**
	 * <pre>
	 * 현재 Chiled Object Map 에 등록된 모든 Child Object의 Key의 값을 모두 소문자로 변경한다.
	 * </pre>
	 *
	 */
	public void toLowerCaseKeyChildList() {
		try {
			Iterator citr = this.childObject.keySet().iterator();
			while(citr.hasNext()) {
				String childName = (String)citr.next();
				List chList = this.getChildObjectList(childName);
				for (int i=0;chList!=null && i<chList.size();i++) {
					((DataObject)chList.get(i)).toLowerCaseKeyValue();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String getPrintValues() {
		StringBuffer logString = new StringBuffer();
		logString.append(this)
		   		 .append("\n");

		Iterator itr = this.childObject.keySet().iterator();
		DataObject item = null;
		List list = null;
		while(itr.hasNext()) {
			String key = (String)itr.next();
			try{
				list = (List)this.childObject.get(key);
				logString.append("Child Name: ")
					   .append(key)
					   .append(", count: ")
					   .append(list.size())
				   .append("[\n");
				for (int i=0;i<list.size();i++) {
					item = (DataObject)list.get(i);
					logString.append(item.getPrintValues());
				}
				logString.append("\n]\n");
			}catch(Exception e){
				e.printStackTrace();
			}
		}

		return logString.toString();
	}

	/**
	 * <pre>
	 * key의 value가 DataObject이고 DataObject내 subKey에를 key로 하는 value가 String, Double, Date일 경우 사용
	 * subKey에 해당하는 정보가 String, Double, Date가 아닐 경우 "" 리턴
	 * </pre>
	 *
	 * @param key
	 * @param subKey
	 * @return
	 */
	public String getBizData(String key, String subKey) {
		String value = "";
		try {
			if (this.get(key) instanceof DataObject) {
				value = ((DataObject)this.get(key)).getString(subKey);
			}
		}catch(Exception e) {
			logger.warn(e.getMessage());
		}
		return value;
	}
}
