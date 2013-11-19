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
<link rel="stylesheet" type="text/css" media="screen" href="<%=CONTEXT_PATH%>/common/css/jquery-ui-themes-1.9.2/themes/ui-lightness/jquery-ui.css" />
<link rel="stylesheet" type="text/css" media="screen" href="<%=CONTEXT_PATH%>/common/js/jquery.jqGrid-4.4.1/css/ui.jqgrid.css" />

<script type="text/javascript" src="<%=CONTEXT_PATH%>/common/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=CONTEXT_PATH%>/common/js/jquery.jqGrid-4.4.1/js/i18n/grid.locale-en.js"></script>
<script type="text/javascript" src="<%=CONTEXT_PATH%>/common/js/jquery.jqGrid-4.4.1/js/jquery.jqGrid.min.js"></script>
</head>
<body>

<tiles:insertAttribute name="header"/>
<tiles:insertAttribute name="left"/>
<tiles:insertAttribute name="content"/>

</body>
</html>