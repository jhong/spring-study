<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/js/slick/css/style.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/js/slick/slick/slick.css"/>
</head>
<body>
<h1>slick</h1>
<section id="features" class="blue">
	<div class="content">
	<div class="slider single-item">
		<div><h3>1</h3></div>
		<div><h3>2</h3></div>
		<div><h3>3</h3></div>
		<div><h3>4</h3></div>
		<div><h3>5</h3></div>
		<div><h3>6</h3></div>
	</div>
	</div>
</section>

<script type="text/javascript" src="<%=contextPath%>/common/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/slick/slick/slick.min.js"></script>
<script type="text/javascript">
<!--

$(document).ready(function(){
	$(".single-item").slick({
		dots:true
	  //setting-name: setting-value
	});
});

//-->
</script>
</body>
</html>