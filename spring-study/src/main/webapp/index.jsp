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
	<li><a href="<%=contextPath%>/sample/jquery_test.jsp">jquery & ajax 예제</a></li>
	<li><a href="<%=contextPath%>/code.do?command=viewMsg&bizlocale=ko">messages 사용 예제 (locale:ko)</a></li>
	<li><a href="<%=contextPath%>/code.do?command=viewMsg&bizlocale=en">messages 사용 예제 (locale:en)</a></li>
	<li><a href="<%=contextPath%>/editor.do?command=viewList">editor 목록</a></li>
	<li><a href="<%=contextPath%>/attach.do?command=viewList">attach 목록 (jqGrid 사용)</a></li>
	<li><a href="<%=contextPath%>/rst/attaches/">attach 목록 (angularjs 사용)</a></li>
	<li><a href="<%=contextPath%>/sample/zipcode_test.jsp">우편번호 테스트</a></li>
</ul>
</body>
</html>
