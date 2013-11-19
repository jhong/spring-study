package net.study.common;

import java.io.Serializable;
import java.util.Locale;
import java.util.Properties;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <pre>
 * 국내수발주를 위한 로그인 세션 정보
 * </pre>
 *
 * @version 2008. 09. 15
 * @author 김창교
 */
public class LoginSession implements Serializable {

	private String house_code;
	private String housekey;
	private String housetype;
	private String parenthousekey;
	private String dept;
	private String user_id;
	private String password;
	private String user_name_loc;
	private String user_type;
	private String phone_no;
	private String fax_no;
	private String mobile_no;
	private String email;
	private String work_type;
	private String ismaster;
	private String sign_status;
	private String house_name_loc;
	private String company_code;
	private String companykey;
	private String company_name_loc;
	private String working_area;
	private String working_name_loc;
	private String vendor_code;
	private String vendor_name_loc;

	private String ceo;
	private String bizregistno;
	private String biztype;
	private String bizcategory;
	private String address1;
	private String address2;
	private String address3;
	private String engpartyname1;
	private String engaddress1;
	private String engaddress2;
	private String engaddress3;
	private String engceo;
	private String edisign;
	private String basicsrid;
	private String prezipcode;
	private String customspropercode;
	private String customscode;
	private String prezipcode_text;
	private String industrialplacecode;
	private String industrialplacecode_text;

	private String companytype;

	private String typecode;
	private String hostname;

	private Locale locale;

	private String user_engname;

	private String filelogo;
	private String fileshippingmark;
	private String urllogo;
	private String urlshippingmark;

	private String payno;
	private String korchamid;

	private String isadmin; // 서비스관리자 여부 by jhong (2012.11.26)
	private String remoteaddr; // 접속 IP by jhong (2013.02.06)
	private String sitetype; // 사이트 종류 by jhong (2013.04.26)
	private String servicemenutype; // 서비스 메뉴 종류 by jhong (2013.04.26)
	private String authtype; // 권한종류 by jhong (2013.11.01)


	/**
	 * @return Returns the house_code.
	 */
	public String getHouse_code() {
		return house_code;
	}
	/**
	 * @param house_code The house_code to set.
	 */
	public void setHouse_code(String house_code) {
		this.house_code = house_code;
	}
	/**
	 * @return Returns the dept.
	 */
	public String getDept() {
		if(dept == null) dept = "";
		return dept;
	}
	/**
	 * @param dept The dept to set.
	 */
	public void setDept(String dept) {
		this.dept = dept;
	}
	/**
	 * @return Returns the user_id.
	 */
	public String getUser_id() {
		return user_id;
	}
	/**
	 * @param user_id The user_id to set.
	 */
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	/**
	 * @return Returns the password.
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password The password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return Returns the user_name_loc.
	 */
	public String getUser_name_loc() {
		if(user_name_loc == null) user_name_loc = "";
		return user_name_loc;
	}
	/**
	 * @param user_name_loc The user_name_loc to set.
	 */
	public void setUser_name_loc(String user_name_loc) {
		this.user_name_loc = user_name_loc;
	}
	/**
	 * @return Returns the user_type.
	 */
	public String getUser_type() {
		if(user_type == null) user_type = "";
		return user_type;
	}
	/**
	 * @param user_type The user_type to set.
	 */
	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}
	/**
	 * @return Returns the phone_no.
	 */
	public String getPhone_no() {
		if(phone_no == null) phone_no = "";
		return phone_no;
	}
	/**
	 * @param phone_no The phone_no to set.
	 */
	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}
	/**
	 * @return Returns the fax_no.
	 */
	public String getFax_no() {
		if(fax_no == null) fax_no = "";
		return fax_no;
	}
	/**
	 * @param fax_no The fax_no to set.
	 */
	public void setFax_no(String fax_no) {
		this.fax_no = fax_no;
	}
	/**
	 * @return Returns the mobile_no.
	 */
	public String getMobile_no() {
		if(mobile_no == null) mobile_no = "";
		return mobile_no;
	}
	/**
	 * @param mobile_no The mobile_no to set.
	 */
	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
	}
	/**
	 * @return Returns the email.
	 */
	public String getEmail() {
		if(email == null) email = "";
		return email;
	}
	/**
	 * @param email The email to set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return Returns the work_type.
	 */
	public String getWork_type() {
		if(work_type == null) work_type = "";
		return work_type;
	}
	/**
	 * @param work_type The work_type to set.
	 */
	public void setWork_type(String work_type) {
		this.work_type = work_type;
	}
	/**
	 * @return Returns the work_type.
	 */
	public String getIsmaster() {
		return ismaster;
	}
	/**
	 * @param work_type The work_type to set.
	 */
	public void setIsmaster(String ismaster) {
		if(ismaster == null) ismaster = "";
		this.ismaster = ismaster;
	}
	/**
	 * @return Returns the sign_status.
	 */
	public String getSign_status() {
		if(sign_status == null) sign_status = "";
		return sign_status;
	}
	/**
	 * @param sign_status The sign_status to set.
	 */
	public void setSign_status(String sign_status) {
		this.sign_status = sign_status;
	}
	/**
	 * @return Returns the house_name_loc.
	 */
	public String getHouse_name_loc() {
		if(house_name_loc == null) house_name_loc = "";
		return house_name_loc;
	}
	/**
	 * @param house_name_loc The house_name_loc to set.
	 */
	public void setHouse_name_loc(String house_name_loc) {
		this.house_name_loc = house_name_loc;
	}
	/**
	 * @return Returns the company_code.
	 */
	public String getCompany_code() {
		if(company_code == null) company_code = "";
		return company_code;
	}
	/**
	 * @param company_code The company_code to set.
	 */
	public void setCompany_code(String company_code) {
		this.company_code = company_code;
	}
	/**
	 * @return Returns the company_name_loc.
	 */
	public String getCompany_name_loc() {
		if(company_name_loc == null) company_name_loc = "";
		return company_name_loc;
	}
	/**
	 * @param company_name_loc The company_name_loc to set.
	 */
	public void setCompany_name_loc(String company_name_loc) {
		this.company_name_loc = company_name_loc;
	}
	/**
	 * @return Returns the working_area.
	 */
	public String getWorking_area() {
		if(working_area == null) working_area = "";
		return working_area;
	}
	/**
	 * @param working_area The working_area to set.
	 */
	public void setWorking_area(String working_area) {
		this.working_area = working_area;
	}
	/**
	 * @return Returns the working_name_loc.
	 */
	public String getWorking_name_loc() {
		if(working_name_loc == null) working_name_loc = "";
		return working_name_loc;
	}
	/**
	 * @param working_name_loc The working_name_loc to set.
	 */
	public void setWorking_name_loc(String working_name_loc) {
		this.working_name_loc = working_name_loc;
	}
	/**
	 * @return Returns the vendor_code.
	 */
	public String getVendor_code() {
		if(vendor_code == null) vendor_code = "";
		return vendor_code;
	}
	/**
	 * @param vendor_code The vendor_code to set.
	 */
	public void setVendor_code(String vendor_code) {
		this.vendor_code = vendor_code;
	}
	/**
	 * @return Returns the vendor_name_loc.
	 */
	public String getVendor_name_loc() {
		if(vendor_name_loc == null) vendor_name_loc = "";
		return vendor_name_loc;
	}
	/**
	 * @param vendor_name_loc The vendor_name_loc to set.
	 */
	public void setVendor_name_loc(String vendor_name_loc) {
		this.vendor_name_loc = vendor_name_loc;
	}
	/**
	 * @return Returns the housekey.
	 */
	public String getHousekey() {
		return housekey;
	}
	/**
	 * @param housekey The housekey to set.
	 */
	public void setHousekey(String housekey) {
		this.housekey = housekey;
	}
	/**
	 * @return Returns the companykey.
	 */
	public String getCompanykey() {
		return companykey;
	}
	/**
	 * @param companykey The companykey to set.
	 */
	public void setCompanykey(String companykey) {
		this.companykey = companykey;
	}
//	/**
//	 * @return Returns the housetype.
//	 */
//	public String getHousetype() {
//		if (this.housetype == null || "".equals(this.housetype)) {
//			if ("KMI".equals(this.housekey)) {
//				this.housetype = BizConst.TYPE_HOUSE_PARENT;
//			} else {
//				this.housetype = BizConst.TYPE_HOUSE_CUSTOM;
//			}
//		}
//		return housetype;
//	}
	/**
	 * @param housetype The housetype to set.
	 */
	public void setHousetype(String housetype) {
		this.housetype = housetype;
	}
	
	
	/**
	 * @return the parenthousekey
	 */
	public String getParenthousekey() {
		return parenthousekey;
	}
	/**
	 * @param parenthousekey the parenthousekey to set
	 */
	public void setParenthousekey(String parenthousekey) {
		this.parenthousekey = parenthousekey;
	}
	/**
	 * @return Returns the ceo.
	 */
	public String getCeo() {
		if(ceo == null) ceo = "";
		return ceo;
	}
	/**
	 * @param ceo The ceo to set.
	 */
	public void setCeo(String ceo) {
		this.ceo = ceo;
	}
	/**
	 * @return Returns the bizregistno.
	 */
	public String getBizregistno() {
		if(bizregistno == null) bizregistno = "";
		return bizregistno;
	}
	/**
	 * @param bizregistno The bizregistno to set.
	 */
	public void setBizregistno(String bizregistno) {
		this.bizregistno = bizregistno;
	}
	/**
	 * @return Returns the biztype.
	 */
	public String getBiztype() {
		if(biztype == null) biztype = "";
		return biztype;
	}
	/**
	 * @param biztype The biztype to set.
	 */
	public void setBiztype(String biztype) {
		this.biztype = biztype;
	}
	/**
	 * @return Returns the bizcategory.
	 */
	public String getBizcategory() {
		if(bizcategory == null) bizcategory = "";
		return bizcategory;
	}
	/**
	 * @param bizcategory The bizcategory to set.
	 */
	public void setBizcategory(String bizcategory) {
		this.bizcategory = bizcategory;
	}

	/**
	 * @return Returns the address.
	 */
	public String getAddress() {
//		String result = address1;
//		if (address2 != null && !"".equals(address2) && !"null".equals(address2)) result += " " + address2;
//		if (address3 != null && !"".equals(address3) && !"null".equals(address3)) result += " " + address3;
//		return result;
		return getAddress(" ");
	}

	/**
	 * <pre>
	 * delimiter 로 연결된 전체 주소 반환
	 * by jhong (2013.05.31)
	 * </pre>
	 * @param delimiter
	 * @return
	 */
	public String getAddress(String delimiter) {
		String result = address1;
		if (address2 != null && !"".equals(address2) && !"null".equals(address2)) result += delimiter + address2;
		if (address3 != null && !"".equals(address3) && !"null".equals(address3)) result += delimiter + address3;
		return result;
	}

	/**
	 * @return Returns the address1.
	 */
	public String getAddress1() {
		if(address1 == null) address1 = "";
		return address1;
	}
	/**
	 * @param address1 The address1 to set.
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	/**
	 * @return Returns the address2.
	 */
	public String getAddress2() {
		if(address2 == null) address2 = "";
		return address2;
	}
	/**
	 * @param address2 The address2 to set.
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	/**
	 * @return Returns the address3.
	 */
	public String getAddress3() {
		if(address3 == null) address3 = "";
		return address3;
	}
	/**
	 * @param address3 The address3 to set.
	 */
	public void setAddress3(String address3) {
		this.address3 = address3;
	}
	/**
	 * @return Returns the engpartyname1.
	 */
	public String getEngpartyname1() {
		if(engpartyname1 == null) engpartyname1 = "";
		return engpartyname1;
	}
	/**
	 * @param engpartyname1 The engpartyname1 to set.
	 */
	public void setEngpartyname1(String engpartyname1) {
		this.engpartyname1 = engpartyname1;
	}
	
	/**
	 * <pre>
	 * delimiter 로 연결된 전체 주소 (영문) 반환
	 * by jhong (2013.05.31)
	 * </pre>
	 * @param delimiter
	 * @return
	 */
	public String getEngaddress(String delimiter) {
		String result = engaddress1;
		if (engaddress2 != null && !"".equals(engaddress2) && !"null".equals(engaddress2)) result += delimiter + engaddress2;
		if (engaddress3 != null && !"".equals(engaddress3) && !"null".equals(engaddress3)) result += delimiter + engaddress3;
		return result;
	}
 
	/**
	 * @return Returns the engaddress1.
	 */
	public String getEngaddress1() {
		if(engaddress1 == null) engaddress1 = "";
		return engaddress1;
	}
	/**
	 * @param engaddress1 The engaddress1 to set.
	 */
	public void setEngaddress1(String engaddress1) {
		this.engaddress1 = engaddress1;
	}
	/**
	 * @return Returns the engaddress2.
	 */
	public String getEngaddress2() {
		if(engaddress2 == null) engaddress2 = "";
		return engaddress2;
	}
	/**
	 * @param engaddress2 The engaddress2 to set.
	 */
	public void setEngaddress2(String engaddress2) {
		this.engaddress2 = engaddress2;
	}
	/**
	 * @return Returns the engaddress3.
	 */
	public String getEngaddress3() {
		if(engaddress3 == null) engaddress3 = "";
		return engaddress3;
	}
	/**
	 * @param engaddress3 The engaddress3 to set.
	 */
	public void setEngaddress3(String engaddress3) {
		this.engaddress3 = engaddress3;
	}
	/**
	 * @return Returns the engceo.
	 */
	public String getEngceo() {
		if(engceo == null) engceo = "";
		return engceo;
	}
	/**
	 * @param engceo The engceo to set.
	 */
	public void setEngceo(String engceo) {
		this.engceo = engceo;
	}
	/**
	 * @return Returns the edisign.
	 */
	public String getEdisign() {
		if(edisign == null) edisign = "";
		return edisign;
	}
	/**
	 * @param edisign The edisign to set.
	 */
	public void setEdisign(String edisign) {
		this.edisign = edisign;
	}
	/**
	 * @return Returns the basicsrid.
	 */
	public String getBasicsrid() {
		if(basicsrid == null) basicsrid = "";
		return basicsrid;
	}
	/**
	 * @param basicsrid The basicsrid to set.
	 */
	public void setBasicsrid(String basicsrid) {
		this.basicsrid = basicsrid;
	}
	/**
	 * @return Returns the prezipcode.
	 */
	public String getPrezipcode() {
		if(prezipcode == null) prezipcode = "";
		return prezipcode;
	}
	/**
	 * @param basicsrid The prezipcode to set.
	 */
	public void setPrezipcode(String prezipcode) {
		this.prezipcode = prezipcode;
	}
	/**
	 * @return Returns the customspropercode.
	 */
	public String getCustomspropercode() {
		if(customspropercode == null) customspropercode = "";
		return customspropercode;
	}
	/**
	 * @param basicsrid The customspropercode to set.
	 */
	public void setCustomspropercode(String customspropercode) {
		this.customspropercode = customspropercode;
	}
	/**
	 * @return Returns the companytype.
	 */
	public String getCompanytype() {
		if(companytype == null) companytype = "";
		return companytype;
	}
	/**
	 * @param companytype The companytype to set.
	 */
	public void setCompanytype(String companytype) {
		this.companytype = companytype;
	}
//	/**
//	 * <pre>
//	 * 현재 로그인한 사용자의 company가 본사인 경우 true 리턴
//	 * </pre>
//	 *
//	 * @return
//	 */
//	public boolean isHouseCompany() {
//		if (BizConst.CODE_COMPANYTYPE_HOUSE.equals(this.companytype)) {
//			return true;
//		} else {
//			return false;
//		}
//	}

	/**
	 * @param user_engname The user_engname to set.
	 */
	public void setUser_engname(String user_engname) {
		this.user_engname = user_engname;
	}
	/**
	 * @return Returns the user_engname.
	 */
	public String getUser_engname() {
		if(user_engname == null) user_engname = "";
		return user_engname;
	}
	/**
	 * @return Returns the hostname.
	 */
	public String getHostname() {
		if(hostname == null) hostname = "";
		return hostname;
	}
	/**
	 * @param hostname The hostname to set.
	 */
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	/**
	 * @return Returns the typecode.
	 */
	public String getTypecode() {
		if(typecode == null) typecode = "";
		return typecode;
	}
	/**
	 * @param typecode The typecode to set.
	 */
	public void setTypecode(String typecode) {
		this.typecode = typecode;
	}
	/**
	 * @return Returns the locale.
	 */
	public Locale getLocale() {
		return locale;
	}
	/**
	 * @param locale The locale to set.
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	/**
	 * @return Returns the filelogo.
	 */
	public String getFilelogo() {
		return filelogo;
	}
	/**
	 * @param locale The filelogo to set.
	 */
	public void setFilelogo(String filelogo) {
		this.filelogo = filelogo;
	}
	/**
	 * @return Returns the fileshippingmark.
	 */
	public String getFileshippingmark() {
		return fileshippingmark;
	}
	/**
	 * @param locale The fileshippingmark to set.
	 */
	public void setFileshippingmark(String fileshippingmark) {
		this.fileshippingmark = fileshippingmark;
	}
	/**
	 * @return Returns the urilogo.
	 */
//	public String getUrllogo() {
//		if(filelogo != null && !"".equals(filelogo))
//			urllogo =  Properties.get("path.attach.company.upimg.url") + filelogo;
//		else urllogo = "";
//		return urllogo;
//	}
	/**
	 * @return Returns the urishippingmark.
	 */
//	public String getUrlshippingmark() {
//		if(fileshippingmark != null && !"".equals(fileshippingmark))
//			urlshippingmark =  Properties.get("path.attach.company.upimg.url") + fileshippingmark;
//		else urlshippingmark = "";
//
//		return urlshippingmark;
//	}
	/**
	 * @return Returns the customscode.
	 */
	public String getCustomscode() {
		return customscode;
	}
	/**
	 * @param customscode The customscode to set.
	 */
	public void setCustomscode(String customscode) {
		this.customscode = customscode;
	}
	/**
	 * @return Returns the prezipcode_text.
	 */
	public String getPrezipcode_text() {
		return prezipcode_text;
	}
	/**
	 * @param prezipcode_text The prezipcode_text to set.
	 */
	public void setPrezipcode_text(String prezipcode_text) {
		this.prezipcode_text = prezipcode_text;
	}
	/**
	 * @return Returns the industrialplacecode.
	 */
	public String getIndustrialplacecode() {
		return industrialplacecode;
	}
	/**
	 * @param industrialplacecode The industrialplacecode to set.
	 */
	public void setIndustrialplacecode(String industrialplacecode) {
		this.industrialplacecode = industrialplacecode;
	}
	/**
	 * @return Returns the industrialplacecode_text.
	 */
	public String getIndustrialplacecode_text() {
		return industrialplacecode_text;
	}
	/**
	 * @param industrialplacecode_text The industrialplacecode_text to set.
	 */
	public void setIndustrialplacecode_text(String industrialplacecode_text) {
		this.industrialplacecode_text = industrialplacecode_text;
	}

	/**
	 * @return Returns the payno.
	 */
	public String getPayno() {
		return payno;
	}
	/**
	 * @param customscode The payno to set.
	 */
	public void setPayno(String payno) {
		this.payno = payno;
	}

	/**
	 * @return Returns the korchamid.
	 */
	public String getKorchamid() {
		return korchamid;
	}
	/**
	 * @param customscode The korchamid to set.
	 */
	public void setKorchamid(String korchamid) {
		this.korchamid = korchamid;
	}
	
	/**
	 * @return Returns the isadmin.
	 */
	public String getIsadmin() {
		return isadmin;
	}
	/**
	 * @param customscode The isadmin to set.
	 */
	public void setIsadmin(String isadmin) {
		this.isadmin = isadmin;
	}
	/**
	 * @return Returns the remoteaddr.
	 */
	public String getRemoteaddr() {
		return remoteaddr;
	}
	/**
	 * @param remoteaddr The remoteaddr to set.
	 */
	public void setRemoteaddr(String remoteaddr) {
		this.remoteaddr = remoteaddr;
	}
	
	public String getUrllogo() {
		return urllogo;
	}
	
	public void setUrllogo(String urllogo) {
		this.urllogo = urllogo;
	}
	
	public String getUrlshippingmark() {
		return urlshippingmark;
	}
	
	public void setUrlshippingmark(String urlshippingmark) {
		this.urlshippingmark = urlshippingmark;
	}
	
	public String getSitetype() {
		return sitetype;
	}
	
	public void setSitetype(String sitetype) {
		this.sitetype = sitetype;
	}
	
	public String getServicemenutype() {
		return servicemenutype;
	}
	
	public void setServicemenutype(String servicemenutype) {
		this.servicemenutype = servicemenutype;
	}
	
	public String getAuthtype() {
		return authtype;
	}
	public void setAuthtype(String authtype) {
		this.authtype = authtype;
	}
	
	/**
	 * toString
	 * @return String
	 */
    public String toString() {
    	return ToStringBuilder.reflectionToString(this);
    }

}
