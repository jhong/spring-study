package net.study.code;

import java.util.List;
import java.util.Map;

import net.study.common.BizCondition;

import org.dom4j.Document;

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

	/**
	 * <pre>
	 * 상세조회
	 * </pre>
	 *
	 * @param condition
	 * @return CodeVo
	 * @throws Exception
	 */
	CodeVo findDetail(Map condition) throws Exception;

	/**
	 * <pre>
	 * 코드 등록화면
	 * </pre>
	 *
	 * @param condition
	 * @return BizResult
	 * @throws Exception
	 */
	CodeVo entry(Map condition) throws Exception;

	/**
	 * <pre>
	 * 코드 등록
	 * </pre>
	 *
	 * @param codeVo
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	CodeVo regist(CodeVo codeVo, Map condition) throws Exception;

	/**
	 * <pre>
	 * 코드 수정
	 * </pre>
	 *
	 * @param codeVo
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	CodeVo modify(CodeVo codeVo, Map condition) throws Exception;

	/**
	 * <pre>
	 * 코드 삭제
	 * </pre>
	 *
	 * @param condition
	 * @return 
	 * @throws Exception
	 */
	int delete(Map condition) throws Exception;

	/**
	 * <pre>
	 * 코드로 조회하여 코드 및 설명정보 조회
	 * </pre>
	 *
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	String getCodeValue(Map condition) throws Exception;

	/**
	 * <pre>
	 * EXCEL 목록 조회
	 * </pre>
	 *
	 * @param condition
	 * @return List
	 * @throws Exception
	 */
	List findListExcel(BizCondition condition) throws Exception;

	/**
	 * <pre>
	 * 상세조회 (XML)
	 * </pre>
	 *
	 * @param condition
	 * @return Document
	 * @throws Exception
	 */
	Document findDetailXml(BizCondition condition) throws Exception;

}
