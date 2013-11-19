<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String contextPath = request.getContextPath();
%>
<html>
<body>
<h2>spring-study</h2>
<hr/>
<ul>
	<li><a href="<%=contextPath%>/code.do?command=viewList&firstRowIndex=0&rowCountPerPage=20">기본 목록 (map list)</a></li>
	<li><a href="<%=contextPath%>/code.do?command=viewListJson&firstRowIndex=0&rowCountPerPage=5">기본 목록 (json list) - ajax 예제 포함</a></li>
	<li><a href="<%=contextPath%>/code.do?command=viewListGrid">jqGrid 목록</a></li>
</ul>
</body>
</html>
