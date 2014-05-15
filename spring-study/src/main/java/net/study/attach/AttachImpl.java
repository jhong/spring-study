package net.study.attach;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import net.study.common.BizCondition;
import net.study.common.BizConst;
import net.study.common.BizResult;
import net.study.common.DataObject;
import net.study.common.LoginSession;
import net.study.common.Properties;
import net.study.util.DateUtil;
import net.study.util.FileUtil;
import net.study.util.StringUtil;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**  
 * <pre>
 * 첨부파일 Implementation
 * </pre>
 *
 * @version 2014.05.13
 * @author 김지홍 
 */
@Service("attachFacade")
public class AttachImpl implements AttachFacade {

	/**
	 * dao
	 */
	@Resource(name="attachDao")
    private AttachDao dao;

	/**
	 * <pre>
	 * dao setter
	 * </pre>
	 *
	 * @param dao
	 */
	public void setAttachDao(AttachDao dao) {
		this.dao = dao;
	}
	
	/**
	 * <pre>
	 * 첨부파일 목록화면 표시
	 * </pre>
	 *
	 * @param condition
	 * @return BizResult
	 * @throws Exception
	 */
	public BizResult viewList(BizCondition condition) throws Exception {
		
		if (condition.isEmpty(BizCondition.NAME_DATEFIELD))
			condition.setString(BizCondition.NAME_DATEFIELD, "registdate"); // TODO : 기본 정렬 Date 필드명 확인하기!!
		
		if (condition.isEmpty(BizCondition.NAME_STARTDATE))
			condition.setString(BizCondition.NAME_STARTDATE, DateFormatUtils.format(DateUtil.getDayAddMonth(-3), DateUtil.PATTERN_DATE_TO_STRING_2));

		if (condition.isEmpty(BizCondition.NAME_ENDDATE))
			condition.setString(BizCondition.NAME_ENDDATE, DateFormatUtils.format(DateUtil.getDayAddMonth(1), DateUtil.PATTERN_DATE_TO_STRING_2));
		
		BizResult bizResult = new BizResult();
		bizResult.setCondition(condition);

		return bizResult;
	}

	/**
	 * <pre>
	 * 첨부파일 목록 조회
	 * </pre>
	 *
	 * @param condition
	 * @return BizResult
	 * @throws Exception
	 */
	public BizResult findList(BizCondition condition) throws Exception {
		LoginSession session = condition.getLoginSession();

		// total count
		int totalRow = dao.selectListCount(condition);
		condition.setTotalRow(totalRow);
		
		// list
		List list = dao.selectListCondition(condition);
		
		BizResult bizResult = new BizResult();
		bizResult.setCondition(condition);
		bizResult.setBizList(list);
		bizResult.setStatus(BizConst.CODE_STATUS_SUCCESS);
		bizResult.setMessage(BizConst.MSG_GET_COMPLETE);
        return bizResult;
	}

	/**
	 * <pre>
	 * 첨부파일 목록 조회
	 * </pre>
	 *
	 * @param condition
	 * @return BizResult
	 * @throws Exception
	 */
	public BizResult findListByRefNo(BizCondition condition) throws Exception {
		LoginSession session = condition.getLoginSession();
		
		// ref_no
		condition.put("searchType1", "ref_no");
		condition.put("searchValue1", condition.getString("ref_no"));
		
		// filekey
		String searchFilekey = condition.getString("filekey");
		if (StringUtil.isNotEmpty(searchFilekey)) {
			condition.put("searchType2", "filekey");
			condition.put("searchValue2", searchFilekey);
		}
		
		// TODO: ref_no2 필요하면 검색조건으로 추가

		// list
		List list = dao.selectListAll(condition);
		
		BizResult bizResult = new BizResult();
		bizResult.setCondition(condition);
		bizResult.setBizList(list);
		bizResult.setStatus(BizConst.CODE_STATUS_SUCCESS);
		bizResult.setMessage(BizConst.MSG_GET_COMPLETE);
        return bizResult;
	}

	/**
	 * <pre>
	 * 첨부파일 등록화면
	 * </pre>
	 *
	 * @param condition
	 * @return BizResult
	 * @throws Exception
	 */
	public BizResult entry(BizCondition condition) throws Exception {
		LoginSession session = condition.getLoginSession();
	
    	AttachVo attachVo = new AttachVo();
    	// TODO: LoginSession 정보 중, 필요한 정보 세팅
    	
    	BizResult bizResult = new BizResult();
    	bizResult.setBizData(attachVo);
    	bizResult.setCondition(condition); // 검색조건 유지
		bizResult.setStatus(BizConst.CODE_STATUS_SUCCESS);
		bizResult.setMessage(BizConst.MSG_COMPLETE);
		return bizResult;
	}

	/**
	 * <pre>
	 * 첨부파일 입력/수정 전 데이터 준비
	 * </pre>
	 *
	 * @param attachVo
	 * @return BizResult
	 * @throws Exception
	 */
	public AttachVo prepareData(AttachVo attachVo) throws Exception {
    	// TODO: json string을 List 객체로 변환, TEXT 처리, 등
    	
		return attachVo;
	}

	/**
	 * <pre>
	 * 첨부파일 등록
	 * </pre>
	 *
	 * @param attachVo
	 * @param condition
	 * @return BizResult
	 * @throws Exception
	 */
	public BizResult regist(AttachVo attachVo, BizCondition condition) throws Exception {
		LoginSession session = condition.getLoginSession();
		
		// pk
		if (StringUtil.isEmpty(attachVo.getFilekey())) {
			String uuidKey = UUID.randomUUID().toString();
			attachVo.setFilekey(uuidKey);
		}
	
    	dao.insert(attachVo);
    	
    	BizResult bizResult = new BizResult();
    	bizResult.setBizData(attachVo);
		bizResult.setStatus(BizConst.CODE_STATUS_SUCCESS);
		bizResult.setMessage(BizConst.MSG_REG_COMPLETE);
		bizResult.setMessageCode("message.complete.reg");
		return bizResult;
	}

	/**
	 * <pre>
	 * 첨부파일 수정
	 * </pre>
	 *
	 * @param attachVo
	 * @param condition
	 * @return BizResult
	 * @throws Exception
	 */
	public BizResult modify(AttachVo attachVo, BizCondition condition) throws Exception {
		LoginSession session = condition.getLoginSession();
		
		dao.update(attachVo);
		
    	BizResult bizResult = new BizResult();
    	bizResult.setBizData(attachVo);
		bizResult.setStatus(BizConst.CODE_STATUS_SUCCESS);
		bizResult.setMessage(BizConst.MSG_MOD_COMPLETE);
		bizResult.setMessageCode("message.complete.mod");
		return bizResult;
	}

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
	public BizResult connectParent(DataObject bizData, BizCondition condition) throws Exception {
		
		// 중복 체크
		boolean isExist = false;
		BizCondition param = new BizCondition();
		param.put("filekey", bizData.getString("filekey"));
		param.put("ref_no", bizData.getString("ref_no"));
		BizResult existResult = this.findListByRefNo(param);
		if (existResult.getBizList() != null && existResult.getBizList().size() > 0) isExist = true;

    	BizResult bizResult = new BizResult();
    	bizResult.setBizData(bizData);

    	if (!isExist) {
    		dao.updateRefNo(bizData);
    		
    		bizResult.setStatus(BizConst.CODE_STATUS_SUCCESS);
    		bizResult.setMessage(BizConst.MSG_MOD_COMPLETE);
    		bizResult.setMessageCode("message.complete.mod");
    		
    	} else {
			bizResult.setStatus(BizConst.CODE_STATUS_FAIL);
			bizResult.setMessage("동일한 데이터가 존재합니다.");
    	}
		
		return bizResult;
	}

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
	public BizResult deleteList(List selList, BizCondition condition) throws Exception {
		LoginSession session = condition.getLoginSession();
		
		int result = 0;
		for (int i = 0; i < selList.size(); i++) {
			DataObject bizData = (DataObject)selList.get(i);
			
			// 삭제
			BizResult deleteResult = this.delete(bizData, null);
			if (deleteResult.getMessageArgs() != null && deleteResult.getMessageArgs().length > 0) {
				result += (Integer)deleteResult.getMessageArgs()[0];
			}
		}
		
		BizResult bizResult = new BizResult();
		bizResult.setStatus(BizConst.CODE_STATUS_SUCCESS);
		bizResult.setMessage(BizConst.MSG_DEL_COMPLETE);
		bizResult.setMessageCode("message.complete.del.cnt");
		bizResult.setMessageArgs(new Object[]{result});
		return bizResult;
	}

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
	public BizResult delete(DataObject bizData, BizCondition condition) throws Exception {
			
		// 삭제
		int result = dao.delete(bizData);
		
		// TODO: 파일 삭제
		
		BizResult bizResult = new BizResult();
		bizResult.setStatus(BizConst.CODE_STATUS_SUCCESS);
		bizResult.setMessage(BizConst.MSG_DEL_COMPLETE);
		bizResult.setMessageCode("message.complete.del.cnt");
		bizResult.setMessageArgs(new Object[]{result});
		return bizResult;
	}

	/**
	 * <pre>
	 * 첨부파일 상세조회
	 * </pre>
	 *
	 * @param condition
	 * @return BizResult
	 * @throws Exception
	 */
	public BizResult findDetail(BizCondition condition) throws Exception {
		BizCondition param = new BizCondition();
		param.put("filekey", condition.getSelectedKey());

    	AttachVo resultVo = dao.selectDetail(param);
    	
		BizResult bizResult = new BizResult();
		bizResult.setBizData(resultVo);
		bizResult.setStatus(BizConst.CODE_STATUS_SUCCESS);
		bizResult.setMessage(BizConst.MSG_GET_COMPLETE);
		return bizResult;
	}

	/**
	 * <pre>
	 * 넘어온 파일을 저장.
	 * </pre>
	 *
	 * @param formFile
	 * @param attachFile
	 * @throws Exception
	 */
	public void uploadFile(MultipartFile formFile, AttachVo attachFile) throws Exception {
        File saveFile = this.getFile(attachFile.getFilekey());
        FileUtil.saveFile(formFile.getInputStream(), saveFile); // 파일 저장
	}

    /**
     * <pre>
     * filekey에 대한 File 리턴
     * </pre>
     *
     * @param fileKey
     * @return
     * @throws Exception
     */
    private File getFile(String fileKey) throws Exception {
    	if (StringUtil.isEmpty(fileKey)) return null; // null 처리 (2013.10.07)
    	
    	String realFileName = Properties.get("path.attach.upload");
    	if (!realFileName.endsWith("/")) realFileName += "/";
   		realFileName += fileKey;
//    	if (!FileUtil.isExistFile(realFileName)) logger.warn("getFile() File is not exist!! realFileName={}", realFileName);
    	
    	File file = new File(realFileName);
    	return file;
    }

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
	public AttachVo findFile(LoginSession session, String filekey) throws Exception {
       	Map param = new HashMap();
       	param.put("filekey", filekey);
       	AttachVo file = (AttachVo)dao.selectDetail(param);

       	File realFile = getFile(filekey);
       	if (file != null) // 파일 존재할 경우만!! by jhong (2012.11.23)
       		file.setFile(realFile);

		return file;
	}

}
