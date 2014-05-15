package net.study.editor;

import java.util.List;

import net.study.common.BizCondition;
import net.study.common.BizResult;

/**  
 * <pre>
 * 에디터 Facade
 * </pre>
 *
 * @version 2014.05.12
 * @author 김지홍 
 */
public interface EditorFacade {
	    
	/**
	 * <pre>
	 * 에디터 목록화면 표시
	 * </pre>
	 *
	 * @param condition
	 * @return BizResult
	 * @throws Exception
	 */
	BizResult viewList(BizCondition condition) throws Exception;

	/**
	 * <pre>
	 * 에디터 목록 조회
	 * </pre>
	 *
	 * @param condition
	 * @return BizResult
	 * @throws Exception
	 */
	BizResult findList(BizCondition condition) throws Exception;

	/**
	 * <pre>
	 * 에디터 등록화면
	 * </pre>
	 *
	 * @param condition
	 * @return BizResult
	 * @throws Exception
	 */
	BizResult entry(BizCondition condition) throws Exception;

	/**
	 * <pre>
	 * 에디터 입력/수정 전 데이터 준비
	 * </pre>
	 *
	 * @param editorVo
	 * @return
	 * @throws Exception
	 */
	EditorVo prepareData(EditorVo editorVo) throws Exception;

	/**
	 * <pre>
	 * 에디터 등록
	 * </pre>
	 *
	 * @param editorVo
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	BizResult regist(EditorVo editorVo, BizCondition condition) throws Exception;

	/**
	 * <pre>
	 * 에디터 수정
	 * </pre>
	 *
	 * @param editorVo
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	BizResult modify(EditorVo editorVo, BizCondition condition) throws Exception;

	/**
	 * <pre>
	 * 에디터 삭제
	 * </pre>
	 *
	 * @param bizData
	 * @param condition
	 * @return BizResult
	 * @throws Exception
	 */
	BizResult delete(List selList, BizCondition condition) throws Exception;

	/**
	 * <pre>
	 * 에디터 상세조회
	 * </pre>
	 *
	 * @param condition
	 * @return BizResult
	 * @throws Exception
	 */
	BizResult findDetail(BizCondition condition) throws Exception;
    
}
