package net.study.code;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.study.common.BizCondition;
import net.study.common.Properties;
import net.study.util.StringUtil;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("codeFacade")
public class CodeImpl implements CodeFacade {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

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
		if (condition == null) condition = new HashMap();

		// total count
		int totalRow = dao.selectListCount(condition);
		condition.put("totalRow", totalRow);
		
		// 페이징 검색조건 없을 경우 처리
		if (condition.get("firstRowIndex") == null) {
//			condition.put("firstRowIndex", 0);
			
			int defaultFirstRowIndex = Properties.getInt("condition.firstRowIndex");
			logger.info("findList() defaultFirstRowIndex={}", defaultFirstRowIndex);
			condition.put("firstRowIndex", defaultFirstRowIndex);
		}
		if (condition.get("rowCountPerPage") == null) {
//			condition.put("rowCountPerPage", totalRow);
			int defaultRowCountPerPage = Properties.getInt("condition.rowCountPerPage");
			logger.info("findList() defaultRowCountPerPage={}", defaultRowCountPerPage);
			condition.put("rowCountPerPage", defaultRowCountPerPage);
		}
		
		// list
//		List list = dao.selectListAll(condition);		// 전체 목록
		List list = dao.selectListCondition(condition); // 페이징 적용 목록
		
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

    /**
     * <pre>
     * 코드로 조회하여 코드 및 설명정보 조회
     * </pre>
     *
     * @param condition
     * @return
     * @throws Exception
     */
    public String getCodeValue(Map condition) throws Exception {
    	String codename = "";
    	CodeVo result = dao.selectDetail(condition);
    	if (result != null) codename = result.getCodename();
		return codename;
	}

	/**
	 * <pre>
	 * EXCEL 목록 조회
	 * </pre>
	 *
	 * @param condition
	 * @return list
	 * @throws Exception
	 */
	public List findListExcel(BizCondition condition) throws Exception {

		// list
		List list = dao.selectListAll(condition);
		condition.setTotalRow(list == null ? 0 : list.size());
		logger.info("findListExcel() condition={}", condition);
		
        return list;
	}

	/**
	 * <pre>
	 * 상세조회 (XML)
	 * </pre>
	 *
	 * @param condition
	 * @return BizResult
	 * @throws Exception
	 */
	public Document findDetailXml(BizCondition condition) throws Exception {
		Map param = new HashMap();
		param.put("codecategorykey", condition.get("codecategorykey"));
		param.put("code", condition.get("code"));

		// xml string 조회 및 Document 생성
		Document root = null;
    	String xmlStr = dao.selectDetailXml(param);
     	if (StringUtil.isNotEmpty(xmlStr)) {
			root = DocumentHelper.parseText(xmlStr);
    	}
    	
		return root;
	}

}
