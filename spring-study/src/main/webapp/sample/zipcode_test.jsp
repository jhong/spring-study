<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String contextPath = request.getContextPath();
%>
<script type="text/javascript" src="<%=contextPath%>/common/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript">
<!--
/** juso.go.kr 우편번호 찾기 */
function searchZipcode(rtnFuc) {
	var url = "<%=contextPath%>/zipcode.do?command=searchPopup";
	if(rtnFuc != null) {
		url += "&rtnCallBack=" + rtnFuc;
	}

	var w = 520;
	var h = 620;
	var LeftPosition = (screen.width) ? (screen.width - w) / 2 : 0;
	var TopPosition = (screen.height) ? (screen.height - h) / 2 : 0;
	var settings = "height=" + h
			   + ",width=" + w
			   + ",top=" + TopPosition
			   + ",left=" + LeftPosition
			   + ",scrollbars=yes, resizable=yes";

	var win = window.open( url, "zipCodeSearchWin", settings );
}

/** 수출화주 우편번호 조회 후 수행하는 callback 메서드 */
function shipperZipcodeCallBack(data) {
	var elmt = $.parseJSON(data);
	//alert("shipperZipcodeCallBack() elmt="+JSON.stringify(elmt));
	
	var form1 = document.forms["form1"];
	form1.shipperprezipcode.value = elmt.zipNo;
	form1.shipperaddress1.value = elmt.roadAddrPart1;
	form1.shipperaddress2.value = elmt.addrDetail;
	form1.streetcode.value = elmt.rnMgtSn;
	form1.buildingmanageno.value = elmt.bdMgtSn;
}
//-->
</script>
<form name="form1">
<fieldset>
	<legend>우편번호 조회 (juso.go.kr)</legend>
	<label>수출화주</label>
	<ul>
		<li>우편번호 : <input type="text" name="shipperprezipcode" />
			<input id="btn_zipcode" type="button" value="우편번호 검색" onclick="searchZipcode('shipperZipcodeCallBack')" /></li>
		<li>주소1 : <input type="text" name="shipperaddress1" size="50"/></li>
		<li>주소2 : <input type="text" name="shipperaddress2" size="50"/></li>
		<li>도로명코드 : <input type="text" name="streetcode" /></li>
		<li>건물관리번호 : <input type="text" name="buildingmanageno" size="30"/></li>
	</ul>
</fieldset>
</form>
* juso.go.kr 통합검색창 사용 위한 승인키 발급받아야 함
<br/>http://www.juso.go.kr/addrlink/addrLinkRequestMainNew.do?cntcMenu=URL
<br/>발급받은 승인키는 [/webapp/WEB-INF/jsp/zipcode/zipcode_popup.jsp] 파일 initPage() 의 confmKey 변수 값으로 세팅하기