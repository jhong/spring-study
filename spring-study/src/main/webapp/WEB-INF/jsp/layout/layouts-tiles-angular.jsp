<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%
String CONTEXT_PATH = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>목록</title>
<link rel="stylesheet" type="text/css" href="<%=CONTEXT_PATH%>/common/css/style.css" />
</head>
<body data-ng-app="springStudyApp">

<tiles:insertAttribute name="header"/>
<tiles:insertAttribute name="left"/>
<tiles:insertAttribute name="content"/>

<script type="text/javascript" src="<%=CONTEXT_PATH%>/common/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=CONTEXT_PATH%>/common/js/angular-1.2.16/angular.min.js"></script>
<script type="text/javascript" src="<%=CONTEXT_PATH%>/common/js/angular-1.2.16/angular-resource.min.js"></script>
<script type="text/javascript" src="<%=CONTEXT_PATH%>/common/js/angular-1.2.16/angular-route.min.js"></script>
<script type="text/javascript" src="<%=CONTEXT_PATH%>/app/scripts/app.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/app/scripts/controllers/AttachCtrl.js"></script>

</body>
</html>