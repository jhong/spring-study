<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="net.study.code.CodeVo"%>
code_edit.jsp
<br/>
<%
CodeVo codeVo = (CodeVo)request.getAttribute("codeVo");
out.print("codeVo : "+codeVo);
%>
<hr/>
<form:form commandName="codeVo" id="subForm" name="subForm" method="post" action="${pageContext.request.contextPath}/code.do">

<ul>
	<li>command : <input type="text" name="command" /></li>
	<li>codecategorykey : <input type="text" name="codecategorykey" value="<%=codeVo.getCodecategorykey()%>"/></li>
	<li>code : <form:input path="code" /></li>
	<li>codeexplain : <form:input path="codeexplain" size="50"/></li>
	<li>codename : <form:input path="codename" size="50"/></li>
	<li>codeengname : <form:input path="codeengname" size="50"/></li>
	<li>status : <form:input path="status" /></li>
</ul>

</form:form>
