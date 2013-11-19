<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="net.study.code.CodeVo"%>
<%
String contextPath = request.getContextPath();
%>
<script type="text/javascript">
<!--
/*
 * 저장
 */
function doRegist() {

	var form = document.forms["subForm"];
	
	form.command.value = "modify";
	if (form.dbmode.value == "C")
		form.command.value = "regist";

	form.submit();
}

/*
 * 삭제
 */
function doDelete() {

	var form = document.forms["subForm"];
	form.command.value = "delete";

	form.submit();
}

/*
 * 목록으로 이동
 */
function goList() {

	var form = document.forms["subForm"];
	form.command.value = "viewListGrid";

	form.submit();
}

//-->
</script>

<div class="layout_2">
<div class="content">

<%
CodeVo codeVo = (CodeVo)request.getAttribute("codeVo");
String dbmode = (String)request.getAttribute("dbmode");
out.print("dbmode="+dbmode+"<br/>codeVo="+codeVo);
%>
<hr/>
<form:form commandName="codeVo" id="subForm" name="subForm" method="post" action="${pageContext.request.contextPath}/code.do">
<input type="hidden" name="command" />
<input type="hidden" name="dbmode" value="<%=dbmode%>"/>

<ul>
	<li>codecategorykey : <form:input path="codecategorykey" /></li>
	<li>code : <form:input path="code" /></li>
	<li>codeexplain : <form:input path="codeexplain" size="50"/></li>
	<li>codename : <form:input path="codename" size="50"/></li>
	<li>codeengname : <form:input path="codeengname" size="50"/></li>
	<li>status : <form:input path="status" /></li>
</ul>

</form:form>

<hr/>
<div class="btn_container">
	<input type="button" value="저장" onclick="doRegist();"/>
	<input type="button" value="삭제" onclick="doDelete();"/>
	<input type="button" value="목록으로" onclick="goList();"/>
</div>

</div><!-- // content -->
</div><!-- // layout_2 -->
