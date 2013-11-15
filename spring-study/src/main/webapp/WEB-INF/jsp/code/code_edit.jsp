<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="net.study.code.CodeVo"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>코드 상세</title>
</head>
<body>
code_edit.jsp
<br/>
<%
String contextPath = request.getContextPath();
CodeVo codeVo = (CodeVo)request.getAttribute("codeVo");
out.print("codeVo : "+codeVo);
%>
<hr/>
<form:form commandName="codeVo" id="subForm" name="subForm" method="post" action="${pageContext.request.contextPath}/code.do">

<ul>
	<li>command : <input type="text" name="command" /></li>
	<li>codecategorykey : <form:input path="codecategorykey" /></li>
	<li>code : <form:input path="code" /></li>
	<li>codeexplain : <form:input path="codeexplain" size="50"/></li>
	<li>codename : <form:input path="codename" size="50"/></li>
	<li>codeengname : <form:input path="codeengname" size="50"/></li>
	<li>status : <form:input path="status" /></li>
</ul>

</form:form>

<hr/>
<a href="<%=contextPath%>/code.do?command=viewList">[목록으로]</a>

</body>
</html>