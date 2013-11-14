package net.study.code;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service("codeFacade")
public class CodeImpl implements CodeFacade {

    @Resource(name="codeDao")
    private CodeDao dao;

	/**
	 * <pre>
	 * 목록 조회
	 * </pre>
	 *
	 * @param condition
	 * @return result
	 * @throws Exception
	 */
	public Map findList(Map condition) throws Exception {

		// total count
		int totalRow = dao.selectListCount(condition);
		
		// list
		List list = dao.selectListAll(condition);
		
		Map result = new HashMap();
		result.put("totalRow", totalRow);
		result.put("bizList", list);
        return result;
	}

}
