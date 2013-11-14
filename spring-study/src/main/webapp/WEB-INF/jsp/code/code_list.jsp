<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
code_list.jsp
<hr/>
<%
String codeName = (String)request.getAttribute("codeName");
out.print("codeName : "+codeName);

int totalRow = (Integer)request.getAttribute("totalRow");
out.print("<br/>totalRow : "+totalRow);

List bizList = (List)request.getAttribute("bizList");
//out.print("<br/>bizList : "+bizList);
for (int i=0; bizList!=null && i<bizList.size(); i++) {
	Map data = (HashMap)bizList.get(i);
	out.print("<br/>i="+i+", data="+data);
}
%>
