package net.study.common;

import java.io.FileInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

/**
 * <pre>
 * 외부 설정 정보
 * </pre>
 *
 * @version 2008. 08. 20
 * @author 김창교
 */
public class Properties {

	private final static Logger logger = LoggerFactory.getLogger(Properties.class);
	
	/**
	* 외부 설정 정보
	*/
	private static java.util.Properties properties;

	/**
	 * properties file location
	 */
	private static String fileLocation = "";
	
	/**
	 * <pre>
	 * fileLocation setter
	 * </pre>
	 * @param fileLocation
	 */
	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	/**
	 * <pre>
	 * load properties
	 * </pre>
	 * @throws Exception
	 */
	public static void load() throws Exception {
		logger.info("Properties load() start... fileLocation={}", fileLocation);
		properties = new java.util.Properties();
		FileInputStream inStream = null;
		try {
			inStream = new FileInputStream(ResourceUtils.getFile(fileLocation));
			properties.load(inStream);
		}finally{
			inStream.close();
		}
		logger.info("Properties load() end... properties={}", properties);
	}

	/**
	 * <pre>
	 * load properties
	 * </pre>
	 * @throws Exception
	 */
	public static void reload() throws Exception {
		logger.info("Properties reload() start... fileLocation={}", fileLocation);
		load();
	}

	/**
	 * <pre>
	 * 환경정보 리턴
	 * </pre>
	 *
	 * @param key 환경정보 key name
	 * @return
	 * @throws Exception
	 */
	public static String get(String key) {
		try {
			if (properties == null) load();
			return properties.getProperty(key);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * <pre>
	 * 설정정보 중 int 형식의 데이터인 경우 설정정보를 int 로 리턴
	 * </pre>
	 *
	 * @param key
	 * @return
	 */
	public static int getInt(String key) {
		try {
			if (properties == null) load();
			if (properties.getProperty(key) != null && properties.getProperty(key).length() > 0) {
				return Integer.parseInt(properties.getProperty(key));
			} else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
