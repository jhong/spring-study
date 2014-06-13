<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.LinkedHashMap" %>
<%@ page import="net.study.common.BizCondition" %>
<%@ page import="net.study.common.BizConst" %>
<%@ page import="net.study.attach.AttachVo"%>
<%
AttachVo form = (AttachVo)request.getAttribute("attachVo");
BizCondition condition = (BizCondition)request.getAttribute("condition");
%>

<script type="text/javaScript">
<!--
/*
 * 저장
 */
function doRegist() {
	
	var form = document.forms["subForm"];
	
	form.command.value = "modify";
	if (form.dbmode.value == "<%=BizConst.CODE_DB_INSERT%>")
		form.command.value = "regist";
	
	form.submit();
}

/*
 * 목록으로 이동
 */
function goList() {

	var form = document.forms["subForm"];
	form.command.value = "viewList";

	form.submit();
}

//-->
</script>

<div class="layout_2">
<div class="content">


<div class="btn_container">
	<input type="button" value="저장" onclick="doRegist();"/>
	<input type="button" value="목록으로" onclick="goList();"/>
</div>


</div><!-- // content -->
</div><!-- // layout_2 -->
