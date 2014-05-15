package net.study.editor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import net.study.common.DataObject;

import org.apache.commons.lang.builder.ToStringBuilder;

public class EditorVo implements Serializable {

	/** BBSKEY */
	private String bbskey = "";
	
	/** BBSTYPE */
	private String bbstype = "";
	
	/** BBSCATEGORY */
	private String bbscategory = "";
	
	/** PRIORITY */
	private String priority = "";
	
	/** TITLE */
	private String title = "";
	
	/** CONTENTS */
	private String contents = "";
	
	/** HIT */
	private BigDecimal hit = null;
	
	/** GROUPID */
	private BigDecimal groupid = null;
	
	/** SORTORDER */
	private BigDecimal sortorder = null;
	
	/** REPLYDEPTH */
	private BigDecimal replydepth = null;
	
	/** PARENTKEY */
	private String parentkey = "";
	
	/** STATUS */
	private String status = "";
	
	/** SITETYPE */
	private String sitetype = "";
	
	/** HOUSEKEY */
	private String housekey = "";
	
	/** COMPANYKEY */
	private String companykey = "";
	
	/** REGISTDATE */
	private Date registdate = null;
	
	/** REGISTER */
	private String register = "";
	
	/** MODIFYDATE */
	private Date modifydate = null;
	
	/** MODIFIER */
	private String modifier = "";

	/** ATTACHSTR */
	private String attachstr = "";
	
	/** ATTACH_LIST */
	private List<DataObject> attachList = null;

	
	public String getBbskey() {
		return this.bbskey;
	}
	
	public void setBbskey(String bbskey) {
		this.bbskey = bbskey;
	}

	public String getBbstype() {
		return this.bbstype;
	}
	
	public void setBbstype(String bbstype) {
		this.bbstype = bbstype;
	}

	public String getBbscategory() {
		return this.bbscategory;
	}
	
	public void setBbscategory(String bbscategory) {
		this.bbscategory = bbscategory;
	}

	public String getPriority() {
		return this.priority;
	}
	
	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getTitle() {
		return this.title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return this.contents;
	}
	
	public void setContents(String contents) {
		this.contents = contents;
	}

	public BigDecimal getHit() {
		return this.hit;
	}
	
	public void setHit(BigDecimal hit) {
		this.hit = hit;
	}

	public BigDecimal getGroupid() {
		return this.groupid;
	}
	
	public void setGroupid(BigDecimal groupid) {
		this.groupid = groupid;
	}

	public BigDecimal getSortorder() {
		return this.sortorder;
	}
	
	public void setSortorder(BigDecimal sortorder) {
		this.sortorder = sortorder;
	}

	public BigDecimal getReplydepth() {
		return this.replydepth;
	}
	
	public void setReplydepth(BigDecimal replydepth) {
		this.replydepth = replydepth;
	}

	public String getParentkey() {
		return this.parentkey;
	}
	
	public void setParentkey(String parentkey) {
		this.parentkey = parentkey;
	}

	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

	public String getSitetype() {
		return this.sitetype;
	}
	
	public void setSitetype(String sitetype) {
		this.sitetype = sitetype;
	}

	public String getHousekey() {
		return this.housekey;
	}
	
	public void setHousekey(String housekey) {
		this.housekey = housekey;
	}

	public String getCompanykey() {
		return this.companykey;
	}
	
	public void setCompanykey(String companykey) {
		this.companykey = companykey;
	}

	public Date getRegistdate() {
		return this.registdate;
	}
	
	public void setRegistdate(Date registdate) {
		this.registdate = registdate;
	}

	public String getRegister() {
		return this.register;
	}
	
	public void setRegister(String register) {
		this.register = register;
	}

	public Date getModifydate() {
		return this.modifydate;
	}
	
	public void setModifydate(Date modifydate) {
		this.modifydate = modifydate;
	}

	public String getModifier() {
		return this.modifier;
	}
	
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	
	public String getAttachstr() {
		return attachstr;
	}

	public void setAttachstr(String attachstr) {
		this.attachstr = attachstr;
	}

	public List<DataObject> getAttachList() {
		return attachList;
	}

	public void setAttachList(List<DataObject> attachList) {
		this.attachList = attachList;
	}

	/**
	 * toString
	 * @return String
	 */
    public String toString() {
    	return ToStringBuilder.reflectionToString(this);
    }

}
