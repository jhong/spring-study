<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String contextPath = request.getContextPath();
%>
<script type="text/javascript" src="<%=contextPath%>/common/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript">
<!--
/*
 *	입력된 코드의 정보를 조회하며, 코드가 없는 경우 코드 오류메시지를 화면에 표시함
 *	Param. 1 : 코드 object
 *	Param. 2 : 코드 category
 */
function getCodeValue(){
	var codecategorykeyVal = "3225";
	var codeVal = $("#countrycode").val();
	alert("codeVal="+codeVal);
	$.ajax({
	    url: "<%=contextPath%>/code.do?command=getCodeValue",
	    data: ({ codecategorykey: codecategorykeyVal,
	    	code: codeVal
	    }),
	    success: function(returnValue) {
	    	alert("returnValue="+returnValue);
	    	$("#countryname").val(returnValue);
	    }
	});

}

//-->
</script>
<fieldset>
	<legend>코드 값 가져오기 (ajax)</legend>
	<label>원산국가</label>
	<input type="text" id="countrycode" name="countrycode" size="5" onblur="getCodeValue();"/>
	<input type="text" id="countryname" name="countryname" size="30"/>
	<br/>
	<span>* 코드 입력 후 onblur 시 코드명 가져옴 (국가 코드 대문자로 입력할 것)</span>
</fieldset>