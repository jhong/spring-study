package net.study.attach;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;


/**  
 * <pre>
 * 첨부파일 Vo
 * </pre>
 
 * @version 2014.05.13
 * @author 김지홍 
 */
public class AttachVo implements Serializable {

	private static final long serialVersionUID = 1L;
	
		
	/** FILEKEY */
	private String filekey = "";
	
	/** HOUSEKEY */
	private String housekey = "";
	
	/** COMPANYKEY */
	private String companykey = "";
	
	/** FILENAME */
	private String filename = "";
	
	/** MIMETYPE */
	private String mimetype = "";
	
	/** CHARSET */
	private String charset = "";
	
	/** FILESIZE */
	private BigDecimal filesize = null;
	
	/** UPLOADDATE */
	private Date uploaddate = null;
	
	/** FILEDESC */
	private String filedesc = "";
	
	/** REF_NO */
	private String refNo = "";
	
	/** REF_NO2 */
	private String refNo2 = "";
	
	/** REF_NO_SEQ */
	private BigDecimal refNoSeq = null;
	
	/** USERID */
	private String userid = "";

	/**
	* file
	*/
	private File file;


	
	public String getFilekey() {
		return this.filekey;
	}
	
	public void setFilekey(String filekey) {
		this.filekey = filekey;
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

	public String getFilename() {
		return this.filename;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getMimetype() {
		return this.mimetype;
	}
	
	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	public String getCharset() {
		return this.charset;
	}
	
	public void setCharset(String charset) {
		this.charset = charset;
	}

	public BigDecimal getFilesize() {
		return this.filesize;
	}
	
	public void setFilesize(BigDecimal filesize) {
		this.filesize = filesize;
	}

	public Date getUploaddate() {
		return this.uploaddate;
	}
	
	public void setUploaddate(Date uploaddate) {
		this.uploaddate = uploaddate;
	}

	public String getFiledesc() {
		return this.filedesc;
	}
	
	public void setFiledesc(String filedesc) {
		this.filedesc = filedesc;
	}

	public String getRefNo() {
		return this.refNo;
	}
	
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public String getRefNo2() {
		return this.refNo2;
	}
	
	public void setRefNo2(String refNo2) {
		this.refNo2 = refNo2;
	}

	public BigDecimal getRefNoSeq() {
		return this.refNoSeq;
	}
	
	public void setRefNoSeq(BigDecimal refNoSeq) {
		this.refNoSeq = refNoSeq;
	}

	public String getUserid() {
		return this.userid;
	}
	
	public void setUserid(String userid) {
		this.userid = userid;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * toString
	 * @return String
	 */
    public String toString() {
    	return ToStringBuilder.reflectionToString(this);
    }
	
}
