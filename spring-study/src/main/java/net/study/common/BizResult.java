package net.study.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.dom4j.Document;

/**
 * <pre>
 * 비즈니스 로직 처리 후 결과 리턴
 * 현재는 기존의 wise out를 상속받아 구현
 * </pre>
 *
 * @version 2008. 09. 15
 * @author 김창교
 */
public class BizResult {
	/**
	* condition
	*/
	private BizCondition condition;

	/**
	* bizData
	*/
	private Object bizData;

	/**
	* errData
	*/
	private Object errData;

	/**
	* bizList
	*/
	private List bizList;

	/**
	* status
	*/
	private String status;

	/**
	* totalCnt
	*/
	private Integer totalCnt;

	/**
	* message
	*/
	private Object message;

	/**
	 * messageCode
	 */
	private String messageCode;
	
	/**
	 * messageArgs
	 */
	private Object[] messageArgs;
	
	/**
	* attached
	*/
	private List attached;

	/**
	* chkKey
	*/
	private String chkKey;

	/**
	 * @return Returns the bizData.
	 */
	public Object getBizData() {
		return bizData;
	}

	/**
	 * @param bizData The bizData to set.
	 */
	public void setBizData(Object bizData) {
		this.bizData = bizData;
	}

	/**
	 * @return Returns the totalCnt.
	 */
	public Integer getTotalCnt() {
		return totalCnt;
	}

	/**
	 * @param totalCnt The totalCnt to set.
	 */
	public void setTotalCnt(Integer totalCnt) {
		this.totalCnt = totalCnt;
	}

	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * @param status The status to set.
	 */
	public void setStatus(int status) {
		if (status == 0) {
			this.status = "false";
		} else {
			this.status = "true";
		}
	}

	/**
	 * @param status The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return Returns the message.
	 */
	public Object getMessage() {
		return message;
	}

	/**
	 * @param message The message to set.
	 */
	public void setMessage(Object message) {
		this.message = message;
	}

	/**
	 * @return Returns the condition.
	 */
	public BizCondition getCondition() {
		return condition;
	}

	/**
	 * @param condition The condition to set.
	 */
	public void setCondition(BizCondition condition) {
		this.condition = condition;
	}

	/**
	 * @return Returns the bizList.
	 */
	public List getBizList() {
		return bizList;
	}

	/**
	 * @param bizList The bizList to set.
	 */
	public void setBizList(List bizList) {
		this.bizList = bizList;
	}

	/**
	 * <pre>
	 * BizResult의 내용을 Map으로 리턴
	 * </pre>
	 *
	 * @return
	 */
	public Map getBizResultMap() {
		Map map = new HashMap();
		map.put("status", this.getStatus());
		map.put("message", this.getMessage());
		map.put("bizData", this.getBizData());
		map.put("bizList", this.getBizList());
		map.put("condition", this.getCondition());
		map.put("totalCnt", this.totalCnt);

		return map;
	}

	/**
	 * <pre>
	 * 처리상태의 성공 유무를 리턴
	 * </pre>
	 *
	 * @return
	 */
	public boolean isSuccess() {
		if (BizConst.CODE_STATUS_SUCCESS.equals(this.status)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * <pre>
	 * 처리상태의 오류 유무를 리턴
	 * </pre>
	 *
	 * @return
	 */
	public boolean isError() {
		if (BizConst.CODE_STATUS_SUCCESS.equals(this.status)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * @return Returns the attached.
	 */
	public List getAttached() {
		return attached;
	}

	/**
	 * @param attached The attached to set.
	 */
	public void setAttached(List attached) {
		this.attached = attached;
	}

//	/**
//	 * <pre>
//	 * 첨부파일 추가
//	 * </pre>
//	 *
//	 * @param contentId
//	 * @param contentType
//	 * @param xml
//	 */
//	public void addAttachment(String contentId, String contentType, Document xml) throws Exception {
//		if (this.attached == null) this.attached = new ArrayList();
//		AttachedFile attachment = new AttachedFile();
//		attachment.setContentId(contentId);
//		attachment.setContentType(contentType);
//		attachment.setXmlAttachment(xml);
//		this.attached.add(attachment);
//	}
//
//	/**
//	 * <pre>
//	 * 첨부파일 추가
//	 * </pre>
//	 *
//	 * @param contentId
//	 * @param contentType
//	 * @param xml
//	 */
//	public void addAttachment(String contentId, String contentType, byte[] byteData) throws Exception {
//		if (this.attached == null) this.attached = new ArrayList();
//		AttachedFile attachment = new AttachedFile();
//		attachment.setContentId(contentId);
//		attachment.setContentType(contentType);
//		attachment.setAttachment(byteData);
//		this.attached.add(attachment);
//	}

	/**
	 * @return Returns the errData.
	 */
	public Object getErrData() {
		return errData;
	}

	/**
	 * @param errData The errData to set.
	 */
	public void setErrData(Object errData) {
		this.errData = errData;
	}

	/**
	 * @return Returns the chkKey.
	 */
	public String getChkKey() {
		return chkKey;
	}

	/**
	 * @param chkKey The chkKey to set.
	 */
	public void setChkKey(String chkKey) {
		this.chkKey = chkKey;
	}

	/**
	 * @return Returns the messageCode.
	 */
	public String getMessageCode() {
		return messageCode;
	}

	/**
	 * @param messageCode The messageCode to set.
	 */
	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}

	/**
	 * @return Returns the messageArgs.
	 */
	public Object[] getMessageArgs() {
		return messageArgs;
	}

	/**
	 * @param messageArgs The errData to messageArgs.
	 */
	public void setMessageArgs(Object[] messageArgs) {
		this.messageArgs = messageArgs;
	}


}
