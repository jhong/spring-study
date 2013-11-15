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

	/**
	 * <pre>
	 * 상세조회
	 * </pre>
	 *
	 * @param condition
	 * @return CodeVo
	 * @throws Exception
	 */
	public CodeVo findDetail(Map condition) throws Exception {

    	CodeVo resultVo = dao.selectDetail(condition);
		return resultVo;
	}

	/**
	 * <pre>
	 * 코드 등록화면
	 * </pre>
	 *
	 * @param condition
	 * @return BizResult
	 * @throws Exception
	 */
	public CodeVo entry(Map condition) throws Exception {
	
    	CodeVo codeVo = new CodeVo();
		return codeVo;
	}

	/**
	 * <pre>
	 * 코드 등록
	 * </pre>
	 *
	 * @param codeVo
	 * @param condition
	 * @return BizResult
	 * @throws Exception
	 */
	public CodeVo regist(CodeVo codeVo, Map condition) throws Exception {
		
    	dao.insert(codeVo);
		return codeVo;
	}

	/**
	 * <pre>
	 * 코드 수정
	 * </pre>
	 *
	 * @param codeVo
	 * @param condition
	 * @return BizResult
	 * @throws Exception
	 */
	public CodeVo modify(CodeVo codeVo, Map condition) throws Exception {
		
		dao.update(codeVo);
		return codeVo;
	}

	/**
	 * <pre>
	 * 코드 삭제
	 * </pre>
	 *
	 * @param param
	 * @return BizResult
	 * @throws Exception
	 */
	public int delete(Map param) throws Exception {
		
		// 삭제
		int result = dao.delete(param);
		return result;
	}

}
