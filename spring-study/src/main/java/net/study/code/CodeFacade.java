package net.study.code;

import java.util.Map;

public interface CodeFacade {

	/**
	 * <pre>
	 * 목록 조회
	 * </pre>
	 *
	 * @param condition
	 * @return result
	 * @throws Exception
	 */
	Map findList(Map condition) throws Exception;

}
