package net.study.editor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import net.study.attach.AttachFacade;
import net.study.common.BizCondition;
import net.study.common.BizConst;
import net.study.common.BizResult;
import net.study.common.DataObject;
import net.study.util.DateUtil;
import net.study.util.StringUtil;

import org.apache.commons.lang.time.DateFormatUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**  
 * <pre>
 * 에디터 Implementation
 * </pre>
 *
 * @version 2014.05.12
 * @author 김지홍 
 */
@Service("editorFacade")
public class EditorImpl implements EditorFacade {

	/**
	* logger
	*/
	private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * dao
	 */
	@Resource(name="editorDao")
    private EditorDao dao;

	/**
	 * <pre>
	 * dao setter
	 * </pre>
	 *
	 * @param dao
	 */
	public void setEditorDao(EditorDao dao) {
		this.dao = dao;
	}
	
	/**
	 * attachFacade
	 */
	@Autowired
	private AttachFacade attachFacade;
	
	/**
	 * <pre>
	 * 에디터 목록화면 표시
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
	 * 에디터 목록 조회
	 * </pre>
	 *
	 * @param condition
	 * @return BizResult
	 * @throws Exception
	 */
	public BizResult findList(BizCondition condition) throws Exception {
//		LoginSession session = condition.getLoginSession();
//		condition.setString("edicompanykey", session.getHousekey());

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
	 * 에디터 등록화면
	 * </pre>
	 *
	 * @param condition
	 * @return BizResult
	 * @throws Exception
	 */
	public BizResult entry(BizCondition condition) throws Exception {
//		LoginSession session = condition.getLoginSession();
	
    	EditorVo editorVo = new EditorVo();
    	// TODO: LoginSession 정보 중, 필요한 정보 세팅
    	
    	BizResult bizResult = new BizResult();
    	bizResult.setBizData(editorVo);
    	bizResult.setCondition(condition); // 검색조건 유지
		bizResult.setStatus(BizConst.CODE_STATUS_SUCCESS);
		bizResult.setMessage(BizConst.MSG_COMPLETE);
		return bizResult;
	}

	/**
	 * <pre>
	 * 에디터 입력/수정 전 데이터 준비
	 * </pre>
	 *
	 * @param editorVo
	 * @return BizResult
	 * @throws Exception
	 */
	public EditorVo prepareData(EditorVo editorVo) throws Exception {
    	// TODO: json string을 List 객체로 변환, TEXT 처리, 등
    	
		return editorVo;
	}

	/**
	 * <pre>
	 * 에디터 등록
	 * </pre>
	 *
	 * @param editorVo
	 * @param condition
	 * @return BizResult
	 * @throws Exception
	 */
	public BizResult regist(EditorVo editorVo, BizCondition condition) throws Exception {
//		LoginSession session = condition.getLoginSession();
//		editorVo.setHousekey(session.getHousekey());
//		editorVo.setRegister(session.getUser_id());
		
		// pk
		String uuidKey = UUID.randomUUID().toString();
		editorVo.setBbskey(uuidKey);
		
    	dao.insert(editorVo);

		// 첨부파일 등록/삭제
		List<DataObject> attachList = this.makeAttachList(editorVo.getAttachstr());
		editorVo.setAttachList(attachList);
		this.registAttachList(editorVo);

    	BizResult bizResult = new BizResult();
    	bizResult.setBizData(editorVo);
		bizResult.setStatus(BizConst.CODE_STATUS_SUCCESS);
		bizResult.setMessage(BizConst.MSG_REG_COMPLETE);
		bizResult.setMessageCode("message.complete.reg");
		return bizResult;
	}

	/**
	 * <pre>
	 * 에디터 수정
	 * </pre>
	 *
	 * @param editorVo
	 * @param condition
	 * @return BizResult
	 * @throws Exception
	 */
	public BizResult modify(EditorVo editorVo, BizCondition condition) throws Exception {
//		LoginSession session = condition.getLoginSession();
//		editorVo.setModifier(session.getUser_id());
		
		dao.update(editorVo);

		// 첨부파일 등록/삭제
		List<DataObject> attachList = this.makeAttachList(editorVo.getAttachstr());
		editorVo.setAttachList(attachList);
		this.registAttachList(editorVo);

    	BizResult bizResult = new BizResult();
    	bizResult.setBizData(editorVo);
		bizResult.setStatus(BizConst.CODE_STATUS_SUCCESS);
		bizResult.setMessage(BizConst.MSG_MOD_COMPLETE);
		bizResult.setMessageCode("message.complete.mod");
		return bizResult;
	}

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
	public BizResult delete(List selList, BizCondition condition) throws Exception {
//		LoginSession session = condition.getLoginSession();
		
		int result = 0;
		for (int i = 0; i < selList.size(); i++) {
			DataObject bizData = (DataObject)selList.get(i);
			
			// 삭제
			result += dao.delete(bizData);
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
	 * 에디터 상세조회
	 * </pre>
	 *
	 * @param condition
	 * @return BizResult
	 * @throws Exception
	 */
	public BizResult findDetail(BizCondition condition) throws Exception {
		BizCondition param = new BizCondition();
		param.put("bbskey", condition.getSelectedKey());
//		param.put("edicompanykey", condition.getLoginSession().getHousekey());

    	EditorVo resultVo = dao.selectDetail(param);
    	
    	BizCondition param2 = new BizCondition();
    	param2.put("ref_no", condition.getSelectedKey());
    	
    	BizResult attachResult = attachFacade.findListByRefNo(param2);
    	List attachList = attachResult.getBizList();
    	
		BizResult bizResult = new BizResult();
		bizResult.setBizData(resultVo);
		bizResult.setBizList(attachList);
		bizResult.setStatus(BizConst.CODE_STATUS_SUCCESS);
		bizResult.setMessage(BizConst.MSG_GET_COMPLETE);
		return bizResult;
	}

	/**
	 * <pre>
	 * 첨부파일 jsong string 으로부터 List 생성하기
	 * </pre>
	 * 
	 * @param attachstr
	 * @return
	 * @throws Exception
	 */
	public List<DataObject> makeAttachList(String attachstr) throws Exception {
		logger.info("attachstr={}", attachstr);
		if (StringUtil.isEmpty(attachstr)) return null;

		List<DataObject> attachList = new ArrayList<DataObject>();
		JSONArray jsonArr = new JSONArray(attachstr);
		for (int i=0; jsonArr!=null && i<jsonArr.length(); i++) {
			JSONObject jsonObj = (JSONObject)jsonArr.get(i);
			DataObject data = new DataObject();
			data.put("filekey", jsonObj.get("filekey"));
			data.put("deletedMark", jsonObj.get("deletedMark"));
			logger.info("json i={}", i+", data="+data);
			attachList.add(data);
		}
		return attachList;
	}
	
	/**
	 * <pre>
	 * 첨부파일 등록/삭제 수행
	 * </pre>
	 * 
	 * @param attachList
	 * @param bbskey
	 * @throws Exception
	 */
	public void registAttachList(EditorVo editorVo) throws Exception {
		
		List<DataObject> attachList = editorVo.getAttachList();
		String bbskey = editorVo.getBbskey();
		
		for (int i=0; attachList!=null && i<attachList.size(); i++) {
			DataObject attachData = attachList.get(i);
			attachData.put("ref_no", bbskey);
			
			String deletedMark = attachData.getString("deletedMark");
			if ("true".equals(deletedMark)) {
				attachFacade.delete(attachData, null); // 삭제
				
			} else {
				attachFacade.connectParent(attachData, null); // bbs에 연결되어있지 않은 경우 연결
			}
		}
	}
}
