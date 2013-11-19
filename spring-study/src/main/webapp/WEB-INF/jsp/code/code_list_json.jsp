<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page import="net.sf.json.JSONArray"%>
<%@ page import="net.sf.json.JSONObject"%>
<%
String contextPath = request.getContextPath();
%>
<script type="text/javascript">
<!--
/*
 * 목록 검색
 */
function searchList(){
	var form = document.forms["listForm"];
	form.command.value = "viewList";
	form.submit();
}
//-->
</script>
<div class="layout_2">
<div class="content">

<%
int totalRow = (Integer)request.getAttribute("totalRow");
//out.print("<br/>totalRow : "+totalRow);

JSONArray bizList = (JSONArray)request.getAttribute("bizList");
//out.print("<br/>bizList : "+bizList);
%>
<br/>
<form id="listForm" name="listForm" method="post" action="<%=contextPath%>/code.do">
<input type="hidden" name="command"/>

<fieldset>
	<legend>코드 목록</legend>
	<label>코드 카테고리 : </label><input type="text" name="codecategorykey" value=""/>
	<label>코드 : </label><input type="text" name="code" value=""/>
	<br/>
	<label>firstRowIndex : </label><input type="text" name="firstRowIndex" value="" size="5"/>
	<label>rowCountPerPage : </label><input type="text" name="rowCountPerPage" value="" size="5"/>
	<label>totalRow : </label><%=totalRow%>
	<br/>
	<input type="submit" name="search" value="검색" onclick="searchList();"/>
</fieldset>

</form>

<div class="btn_container">
	<a href="<%=contextPath%>/code.do?command=entry">[등록]</a>
</div>
<table border="1">
	<tr>
		<th>index</th>
		<th>CODECATEGORYKEY</th>
		<th>CODE</th>
		<th>CODENAME</th>
	</tr>
<%
for (int i=0; bizList!=null && i<bizList.size(); i++) {
	JSONObject data = (JSONObject)bizList.get(i);
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

<ol id="listContainer">
</ol>

<hr/>

<ul id="listContainer2">
</ul>

</div><!-- // content -->
</div><!-- // layout_2 -->

<script type="text/javascript">
<!--
function load(containerId, jsonStr) {
//	console.log('jsonStr='+jsonStr); // firefox, chrome
	alert('jsonStr='+jsonStr);
	
	var jsonArr = eval(jsonStr);
	alert('jsonArr.length='+jsonArr.length);
	
	var listContainer = $("#"+containerId);
	
	for (var i=0; i<jsonArr.length; i++) {
		var jsonObj = jsonArr[i];
		//alert("i="+i+", jsonObj="+jsonObj+", code="+jsonObj.CODE);
		listContainer.append("<li>"+jsonObj.CODECATEGORYKEY+", "+jsonObj.CODE+", "+jsonObj.CODENAME+"</li>")
	}
	
}

function getListAjax(){
	$.ajax({
	    url: "<%=contextPath%>/code.do?command=findListJson",
	    data: ({ codecategorykey: "3039A",
	    	firstRowIndex : 0,
	    	rowCountPerPage : 100
	    }),
	    success: function(returnValue) {
	    	alert("getListAjax() returnValue="+returnValue);
	    	load('listContainer2', returnValue);
	    }
	});

}

/*
 * onload시 실행
 */
$(document).ready(function(){
	load('listContainer', '<%=bizList.toString()%>'); // controller 에서 넘어온 json string 보여주기
	
	getListAjax();
});
//-->
</script>
