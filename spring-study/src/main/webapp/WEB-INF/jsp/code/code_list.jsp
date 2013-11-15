<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>코드 목록</title>
</head>
<body>
code_list.jsp
<hr/>
<%
String contextPath = request.getContextPath();
String codeName = (String)request.getAttribute("codeName");
out.print("codeName : "+codeName);

int totalRow = (Integer)request.getAttribute("totalRow");
out.print("<br/>totalRow : "+totalRow);

List bizList = (List)request.getAttribute("bizList");
//out.print("<br/>bizList : "+bizList);
%>
<br/>
<table border="1">
	<tr>
		<th>index</th>
		<th>CODECATEGORYKEY</th>
		<th>CODE</th>
		<th>CODENAME</th>
	</tr>
<%
for (int i=0; bizList!=null && i<bizList.size(); i++) {
	Map data = (HashMap)bizList.get(i);
	String codecategorykey = (String)data.get("CODECATEGORYKEY");
	String code = (String)data.get("CODE");
	String url = contextPath+"/code.do?command=findDetail&codecategorykey="+codecategorykey+"&code="+code;
	out.print("<tr>");
	out.print("<td><a href='"+url+"'>"+i+"</a></td>");
	out.print("<td>"+codecategorykey+"</td>");
	out.print("<td>"+code+"</td>");
	out.print("<td>"+data.get("CODENAME")+"</td>");
	out.print("</tr>");
}
%>
</table>

</body>
</html>