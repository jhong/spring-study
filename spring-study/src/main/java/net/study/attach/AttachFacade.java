package net.study.attach;

import java.util.List;

import net.study.common.BizCondition;
import net.study.common.BizResult;
import net.study.common.DataObject;
import net.study.common.LoginSession;

import org.springframework.web.multipart.MultipartFile;

/**  
 * <pre>
 * 첨부파일 Facade
 * </pre>
 *
 * @version 2014.05.13
 * @author 김지홍 
 */
public interface AttachFacade {
	    
	/**
	 * <pre>
	 * 첨부파일 목록화면 표시
	 * </pre>
	 *
	 * @param condition
	 * @return BizResult
	 * @throws Exception
	 */
	BizResult viewList(BizCondition condition) throws Exception;

	/**
	 * <pre>
	 * 첨부파일 목록 조회
	 * </pre>
	 *
	 * @param condition
	 * @return BizResult
	 * @throws Exception
	 */
	BizResult findList(BizCondition condition) throws Exception;

	/**
	 * <pre>
	 * 첨부파일 목록 조회
	 * </pre>
	 *
	 * @param condition
	 * @return BizResult
	 * @throws Exception
	 */
	BizResult findListByRefNo(BizCondition condition) throws Exception;

	/**
	 * <pre>
	 * 첨부파일 등록화면
	 * </pre>
	 *
	 * @param condition
	 * @return BizResult
	 * @throws Exception
	 */
	BizResult entry(BizCondition condition) throws Exception;

	/**
	 * <pre>
	 * 첨부파일 입력/수정 전 데이터 준비
	 * </pre>
	 *
	 * @param attachVo
	 * @return
	 * @throws Exception
	 */
	AttachVo prepareData(AttachVo attachVo) throws Exception;

	/**
	 * <pre>
	 * 첨부파일 등록
	 * </pre>
	 *
	 * @param attachVo
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	BizResult regist(AttachVo attachVo, BizCondition condition) throws Exception;

	/**
	 * <pre>
	 * 첨부파일 수정
	 * </pre>
	 *
	 * @param attachVo
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	BizResult modify(AttachVo attachVo, BizCondition condition) throws Exception;

	/**
	 * <pre>
	 * 첨부파일 수정 (parent 연결)
	 * </pre>
	 *
	 * @param attachVo
	 * @param condition
	 * @return BizResult
	 * @throws Exception
	 */
	BizResult connectParent(DataObject bizData, BizCondition condition) throws Exception;

	/**
	 * <pre>
	 * 첨부파일 삭제
	 * </pre>
	 *
	 * @param bizData
	 * @param condition
	 * @return BizResult
	 * @throws Exception
	 */
	BizResult deleteList(List selList, BizCondition condition) throws Exception;

	/**
	 * <pre>
	 * 첨부파일 삭제
	 * </pre>
	 *
	 * @param bizData
	 * @param condition
	 * @return BizResult
	 * @throws Exception
	 */
	BizResult delete(DataObject bizData, BizCondition condition) throws Exception;

	/**
	 * <pre>
	 * 첨부파일 상세조회
	 * </pre>
	 *
	 * @param condition
	 * @return BizResult
	 * @throws Exception
	 */
	BizResult findDetail(BizCondition condition) throws Exception;
    
	/**
	 * <pre>
	 * 넘어온 파일을 저장.
	 * </pre>
	 *
	 * @param formFile
	 * @param attachFile
	 * @throws Exception
	 */
	public void uploadFile(MultipartFile formFile, AttachVo attachFile) throws Exception;

	/**
	 * <pre>
	 * filekey에 해당하는 파일(byte[])과 파일정보를 리턴
	 * </pre>
	 *
	 * @param session
	 * @param fileKey
	 * @return
	 * @throws Exception
	 */
	public AttachVo findFile(LoginSession session, String fileKey) throws Exception;

}
