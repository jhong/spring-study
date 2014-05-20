<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.LinkedHashMap" %>
<%@ page import="net.study.common.BizConst" %>
<%@ page import="net.study.common.BizCondition" %>
<%@ page import="net.study.common.DataObject"%>
<%@ page import="net.study.editor.EditorVo"%>
<%
EditorVo form = (EditorVo)request.getAttribute("editorVo");
BizCondition condition = (BizCondition)request.getAttribute("condition");
List attachList = (List)request.getAttribute("attachList");
%>
<script type="text/javascript">
_editor_url = "<c:url value='/common/js/htmlarea'/>";
_editor_lang = "en";
</script>
<script src="<c:url value='common/js/htmlarea/htmlarea.js'/>" type="text/javascript" charset="utf-8"></script>

<script type="text/javaScript">
<!--
/*
 * 저장
 */
function doRegist() {
	
	var form = document.forms["subForm"];
	
	form.command.value = "modify";
	if (form.dbmode.value == "<%=BizConst.CODE_DB_INSERT%>")
		form.command.value = "regist";
	
	// htmlarea
	//form.save.value = "yes";
	form.onsubmit(); // workaround browser bugs.
	
	form.submit();
}

/*
 * 삭제
 */
function doDelete() {

	var form = document.forms["subForm"];
	form.command.value = "delete";

	form.submit();
}

/*
 * 목록으로 이동
 */
function goList() {

	var form = document.forms["subForm"];
	form.command.value = "viewList";

	form.submit();
}

//-->
</script>

<div class="layout_2">
<div class="content">


<div class="btn_container">
	<input type="button" value="저장" onclick="doRegist();"/>
	<input type="button" value="삭제" onclick="doDelete();"/>
	<input type="button" value="목록으로" onclick="goList();"/>
</div>

<form:form commandName="editorVo" id="subForm" name="subForm" method="post" action="${pageContext.request.contextPath}/editor.do">
<input type="hidden" name="command" />
<input type="hidden" name="dbmode" value="<%=condition.get("dbmode")%>"/>
<input type="hidden" name="editorType" value="<%=condition.get("editorType")%>"/>
<form:hidden path="bbskey"/>
<form:hidden path="bbstype" value="N"/>
<form:hidden path="status" value="USE"/>
<form:hidden path="attachstr"/>

<!-- sub title -->
<div class="sub_title">
	<h2>htmlArea 사용</h2>
</div>
<!--// sub title-->
<div class="bd_wrap">
	<table class="view" summary="">
	    <caption></caption>
		<colgroup>
			<col width="18%"/>
			<col width="82%"/>
		</colgroup>
		<tbody>
		<tr>
			<th scope="row" class="ln_r lft"><label for="title">TITLE</label></th>
			<td class="lft">
				<div class="errdv1_l">
					<form:input path="title" cssErrorClass="type_text err" class="type_text" size="50" maxlength="248"/>
				</div>
				<div class="errdv1_r">
					<form:errors path="title" id="" name="title.errors" cssClass="err" delimiter="</span><span name='title.errors' class='err'>"/>
				</div>
			</td>
		</tr>
		<tr>
			<th scope="row" class="ln_r lft"><label for="contents">CONTENTS</label></th>
			<td class="lft">
				<div class="errdv1_l">
					<form:textarea path="contents" cssErrorClass="type_text err" class="type_text" cols="42" rows="3" style="width:100%;height:300px;"/>
				</div>
				<div class="errdv1_r">
					<form:errors path="contents" id="" name="contents.errors" cssClass="err" delimiter="</span><span name='contents.errors' class='err'>"/>
				</div>
			</td>
		</tr>
		</tbody>
	</table>
</div> <!--// bd_wrap -->

</form:form>



</div><!-- // content -->
</div><!-- // layout_2 -->


<script type="text/javascript">

$(document).ready(function() {
	HTMLArea.init();
});

var editor = null;
function initEditor() {
  editor = new HTMLArea("contents");
  editor.generate();
}
function insertHTML() {
  var html = prompt("Enter some HTML code here");
  if (html) {
    editor.insertHTML(html);
  }
}

HTMLArea.onload = initEditor;
</script>
