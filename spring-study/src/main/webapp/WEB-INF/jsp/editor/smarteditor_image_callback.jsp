<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="net.study.common.BizCondition" %>
<%@ page import="net.study.common.BizConst" %>
<%@ page import="net.study.editor.EditorVo"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="java.util.Map" %>
<%@ page import="net.study.common.BizCondition" %>
<%@ page import="net.study.common.BizConst" %>
<%@ page import="net.study.attach.AttachVo"%>
<%
AttachVo form = (AttachVo)request.getAttribute("attachVo");
BizCondition condition = (BizCondition)request.getAttribute("condition");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>Daum에디터 - 이미지 첨부</title> 
<script type="text/javascript">
//alert("callback");

//document.domain 설정
//try { document.domain = "http://*.naver.com"; } catch(e) {}

/*
// execute callback script
var sUrl = document.location.search.substr(1);
if (sUrl != "blank") {
	var oParameter = {}; // query array

	sUrl.replace(/([^=]+)=([^&]*)(&|$)/g, function(){
		oParameter[arguments[1]] = arguments[2];
		return "";
	});
	
      
	if ((oParameter.errstr || '').length) { // on error
		(parent.jindo.FileUploader._oCallback[oParameter.callback_func+'_error'])(oParameter);
	} else {
		(parent.jindo.FileUploader._oCallback[oParameter.callback_func+'_success'])(oParameter);
	}
}
*/

var oParameter = {callback_func:"<%=condition.getString("callback_func")%>"
	,sFileName : "<%=form.getFilename()%>"
	,sFileURL : "<%=request.getContextPath()%>/attach.do?command=downloadImage&filekey=<%=form.getFilekey()%>"
	,bNewLine : true 
	}; // query array

if ((oParameter.errstr || '').length) { // on error
	(parent.jindo.FileUploader._oCallback[oParameter.callback_func+'_error'])(oParameter);
} else {
	(parent.jindo.FileUploader._oCallback[oParameter.callback_func+'_success'])(oParameter);
}

</script>
</head>
<body>
</html>
