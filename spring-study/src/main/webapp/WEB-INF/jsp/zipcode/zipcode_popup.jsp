<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="java.util.Map"%>
<%
Map condition = (Map)request.getAttribute("condition");
/*
[condition 결과 예]
inputYn=Y, 
zipNo=05398, 
jibunAddr=서울특별시 강동구 성내동   452-3 신호빌라트, 
roadFullAddr=서울특별시 강동구 강동대로 143-40, ㅎㅎㅎㅎ (성내동, 신호빌라트), 
roadAddrPart1=서울특별시 강동구 강동대로 143-40, 
roadAddrPart2=(성내동, 신호빌라트), 
addrDetail=ㅎㅎㅎㅎ, 
engAddr=143-40, Gangdong-daero, Gangdong-gu, Seoul, 
admCd=1174010800, 
rnMgtSn=117402123001, 
bdMgtSn=1174010800104520003007159, 
rtnCallBack=shipperZipcodeCallBack
*/
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>우편번호 찾기</title>
<script type="text/javascript" src="<c:url value='/common/js/jquery-1.7.2.min.js'/>"></script>
<script type="text/javascript">
function initPage(){
	var url = location.href;
	var confmKey = ""; // TODO: 승인키 발급받고 properties 파일로 분리하기!! (http://www.juso.go.kr/addrlink/addrLinkRequestMainNew.do?cntcMenu=URL)
	var inputYn = "<%=condition.get("inputYn")%>";
	var form = document.forms["zipcodeForm"];

	if(inputYn != "Y"){ // 처음 팝업을 띄울 때
		form.confmKey.value = confmKey;
		form.returnUrl.value = url;
		form.action="http://www.juso.go.kr/addrlink/addrLinkUrl.do"; // TODO: properties 파일로 분리하기
		form.submit();
		
	}else{ // 우편번호 검색 후 팝업에서 [주소입력] 버튼 클릭 할 때
		var rtnObj = new Object();
		rtnObj.zipNo = "<%=condition.get("zipNo")%>";		// 우편번호
		rtnObj.roadAddrPart1 = "<%=condition.get("roadAddrPart1")%>";	// 도로명 주소
		rtnObj.roadAddrPart2 = "<%=condition.get("roadAddrPart2")%>";	// 참고주소
		rtnObj.engAddr = "<%=condition.get("engAddr")%>";			// 영문도로명주소
		rtnObj.jibunAddr = "<%=condition.get("jibunAddr")%>";			// 지번주소
		rtnObj.addrDetail = "<%=condition.get("addrDetail")%>";			// 고객입력 상세주소
		rtnObj.admCd = "<%=condition.get("admCd")%>";					// 행정구역코드
		rtnObj.rnMgtSn = "<%=condition.get("rnMgtSn")%>";				// 도로명코드
		rtnObj.bdMgtSn = "<%=condition.get("bdMgtSn")%>";				// 건물관리번호
		
		var rtnJson = JSON.stringify(rtnObj);

		// callback 메서드 실행
		opener.<%=condition.get("rtnCallBack")%>(rtnJson);

		window.close();
	}
}

$(document).ready(function() {
	initPage();
});
</script>
</head>
<body>
	<form id="zipcodeForm" name="zipcodeForm" method="post">
		<input type="hidden" id="confmKey" name="confmKey" value=""/>
		<input type="hidden" id="returnUrl" name="returnUrl" value="<%=condition.get("returnUrl")%>"/>
	</form>
</body>
</html>