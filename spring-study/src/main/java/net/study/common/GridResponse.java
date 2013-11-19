package net.study.common;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * Grid 사용 위한 Response 클래스
 * (jqGrid 에서 사용하는 parameter 넘겨주기 위한 클래스)
 * 
 * @author 김지홍
 * @since 2012.07.02
 * @version 1.0
 * @see
 * 
 *      <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2012.07.02  김지홍          최초 생성
 * 
 * </pre>
 */
public class GridResponse {

	/** 현재페이지 */
	private String page;

	/** Total pages for the query */
	private String total;

	/** Total number of records for the query */
	private String records;

	/** An array that contains the actual objects */
	private List<HashMap> data;
	
	private String msg;

	public GridResponse() {
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getRecords() {
		return records;
	}

	public void setRecords(String records) {
		this.records = records;
	}

	public List<HashMap> getData() {
		return data;
	}

	public void setData(List<HashMap> data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * toString
	 * @return String
	 */
    public String toString() {
    	return ToStringBuilder.reflectionToString(this);
    }

}
