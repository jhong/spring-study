package net.study.code;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.math.BigDecimal;


public class CodeVo implements Serializable {

	private static final long serialVersionUID = 1L;
	
		
	/** CODECATEGORYKEY */
	private String codecategorykey = "";
	
	/** CODE */
	private String code = "";
	
	/** CODEEXPLAIN */
	private String codeexplain = "";
	
	/** CODENAME */
	private String codename = "";
	
	/** CODEENGNAME */
	private String codeengname = "";
	
	/** STATUS */
	private String status = "";
	
	/** SORTORDER */
	private BigDecimal sortorder = null;


	
	public String getCodecategorykey() {
		return this.codecategorykey;
	}
	
	public void setCodecategorykey(String codecategorykey) {
		this.codecategorykey = codecategorykey;
	}

	public String getCode() {
		return this.code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}

	public String getCodeexplain() {
		return this.codeexplain;
	}
	
	public void setCodeexplain(String codeexplain) {
		this.codeexplain = codeexplain;
	}

	public String getCodename() {
		return this.codename;
	}
	
	public void setCodename(String codename) {
		this.codename = codename;
	}

	public String getCodeengname() {
		return this.codeengname;
	}
	
	public void setCodeengname(String codeengname) {
		this.codeengname = codeengname;
	}

	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getSortorder() {
		return this.sortorder;
	}
	
	public void setSortorder(BigDecimal sortorder) {
		this.sortorder = sortorder;
	}

	
	/**
	 * toString
	 * @return String
	 */
    public String toString() {
    	return ToStringBuilder.reflectionToString(this);
    }
	
}
