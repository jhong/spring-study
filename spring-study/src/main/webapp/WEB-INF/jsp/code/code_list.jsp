<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
code_list.jsp
<hr/>
<%
String codeName = (String)request.getAttribute("codeName");
out.print("codeName : "+codeName);

int totalRow = (Integer)request.getAttribute("totalRow");
out.print("<br/>totalRow : "+totalRow);

%>
