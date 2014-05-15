<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.LinkedHashMap" %>
<%@ page import="net.study.common.BizCondition" %>
<%@ page import="net.study.common.BizConst" %>
<%@ page import="net.study.editor.EditorVo"%>
<%
EditorVo form = (EditorVo)request.getAttribute("editorVo");
BizCondition condition = (BizCondition)request.getAttribute("condition");
%>

<script type="text/javaScript">
<!--
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
	<input type="button" value="목록으로" onclick="goList();"/>
</div>

<form:form commandName="editorVo" id="subForm" name="subForm" method="post" action="${pageContext.request.contextPath}/editor.do">
<input type="hidden" name="command" />
<input type="hidden" name="dbmode" value="<%=condition.get("dbmode")%>"/>
<form:hidden path="bbskey"/>

<!-- sub title -->
<div class="sub_title">
	<h2>상세화면</h2>
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
			<th scope="row" class="ln_r lft"><label>BBSKEY</label></th>
			<td class="lft">
				<div class="errdv1_l">
					<%=form.getBbskey()%>
				</div>
			</td>
		</tr>
		<tr>
			<th scope="row" class="ln_r lft"><label>TITLE</label></th>
			<td class="lft">
				<div class="errdv1_l">
					<%=form.getTitle()%>
				</div>
			</td>
		</tr>
		<tr>
			<th scope="row" class="ln_r lft"><label>CONTENTS</label></th>
			<td class="lft" style="background-color:white;">
				<div class="errdv1_l">
					<%=form.getContents()%>
				</div>
			</td>
		</tr>
		<!--
		<tr>
			<th scope="row" class="ln_r lft"><label for="bbstype">BBSTYPE</label></th>
			<td class="lft">
				<div class="errdv1_l">
					<form:input path="bbstype" value="N" cssErrorClass="type_text err" class="type_text" size="5" maxlength="5"/>
				</div>
				<div class="errdv1_r">
					<form:errors path="bbstype" id="" name="bbstype.errors" cssClass="err" delimiter="</span><span name='bbstype.errors' class='err'>"/>
				</div>
			</td>
		</tr>
		<tr>
			<th scope="row" class="ln_r lft"><label for="bbscategory">BBSCATEGORY</label></th>
			<td class="lft">
				<div class="errdv1_l">
					<form:input path="bbscategory" cssErrorClass="type_text err" class="type_text" size="5" maxlength="5"/>
				</div>
				<div class="errdv1_r">
					<form:errors path="bbscategory" id="" name="bbscategory.errors" cssClass="err" delimiter="</span><span name='bbscategory.errors' class='err'>"/>
				</div>
			</td>
		</tr>
		<tr>
			<th scope="row" class="ln_r lft"><label for="priority">PRIORITY</label></th>
			<td class="lft">
				<div class="errdv1_l">
					<form:input path="priority" cssErrorClass="type_text err" class="type_text" size="5" maxlength="5"/>
				</div>
				<div class="errdv1_r">
					<form:errors path="priority" id="" name="priority.errors" cssClass="err" delimiter="</span><span name='priority.errors' class='err'>"/>
				</div>
			</td>
		</tr>
		<tr>
			<th scope="row" class="ln_r lft"><label for="hit">HIT</label></th>
			<td class="lft">
				<div class="errdv1_l">
					<form:input path="hit" cssErrorClass="type_text err" class="type_text" size="10" maxlength="25"/>
				</div>
				<div class="errdv1_r">
					<form:errors path="hit" id="" name="hit.errors" cssClass="err" delimiter="</span><span name='hit.errors' class='err'>"/>
				</div>
			</td>
		</tr>
		<tr>
			<th scope="row" class="ln_r lft"><label for="groupid">GROUPID</label></th>
			<td class="lft">
				<div class="errdv1_l">
					<form:input path="groupid" cssErrorClass="type_text err" class="type_text" size="10" maxlength="25"/>
				</div>
				<div class="errdv1_r">
					<form:errors path="groupid" id="" name="groupid.errors" cssClass="err" delimiter="</span><span name='groupid.errors' class='err'>"/>
				</div>
			</td>
		</tr>
		<tr>
			<th scope="row" class="ln_r lft"><label for="sortorder">SORTORDER</label></th>
			<td class="lft">
				<div class="errdv1_l">
					<form:input path="sortorder" cssErrorClass="type_text err" class="type_text" size="10" maxlength="25"/>
				</div>
				<div class="errdv1_r">
					<form:errors path="sortorder" id="" name="sortorder.errors" cssClass="err" delimiter="</span><span name='sortorder.errors' class='err'>"/>
				</div>
			</td>
		</tr>
		<tr>
			<th scope="row" class="ln_r lft"><label for="replydepth">REPLYDEPTH</label></th>
			<td class="lft">
				<div class="errdv1_l">
					<form:input path="replydepth" cssErrorClass="type_text err" class="type_text" size="10" maxlength="25"/>
				</div>
				<div class="errdv1_r">
					<form:errors path="replydepth" id="" name="replydepth.errors" cssClass="err" delimiter="</span><span name='replydepth.errors' class='err'>"/>
				</div>
			</td>
		</tr>
		<tr>
			<th scope="row" class="ln_r lft"><label for="parentkey">PARENTKEY</label></th>
			<td class="lft">
				<div class="errdv1_l">
					<form:input path="parentkey" cssErrorClass="type_text err" class="type_text" size="20" maxlength="128"/>
				</div>
				<div class="errdv1_r">
					<form:errors path="parentkey" id="" name="parentkey.errors" cssClass="err" delimiter="</span><span name='parentkey.errors' class='err'>"/>
				</div>
			</td>
		</tr>
		<tr>
			<th scope="row" class="ln_r lft"><label for="status">STATUS</label></th>
			<td class="lft">
				<div class="errdv1_l">
					<form:input path="status" value="USE" cssErrorClass="type_text err" class="type_text" size="5" maxlength="5"/>
				</div>
				<div class="errdv1_r">
					<form:errors path="status" id="" name="status.errors" cssClass="err" delimiter="</span><span name='status.errors' class='err'>"/>
				</div>
			</td>
		</tr>
		<tr>
			<th scope="row" class="ln_r lft"><label for="sitetype">SITETYPE</label></th>
			<td class="lft">
				<div class="errdv1_l">
					<form:input path="sitetype" cssErrorClass="type_text err" class="type_text" size="20" maxlength="70"/>
				</div>
				<div class="errdv1_r">
					<form:errors path="sitetype" id="" name="sitetype.errors" cssClass="err" delimiter="</span><span name='sitetype.errors' class='err'>"/>
				</div>
			</td>
		</tr>
		<tr>
			<th scope="row" class="ln_r lft"><label for="housekey">HOUSEKEY</label></th>
			<td class="lft">
				<div class="errdv1_l">
					<form:input path="housekey" cssErrorClass="type_text err" class="type_text" size="20" maxlength="128"/>
				</div>
				<div class="errdv1_r">
					<form:errors path="housekey" id="" name="housekey.errors" cssClass="err" delimiter="</span><span name='housekey.errors' class='err'>"/>
				</div>
			</td>
		</tr>
		<tr>
			<th scope="row" class="ln_r lft"><label for="companykey">COMPANYKEY</label></th>
			<td class="lft">
				<div class="errdv1_l">
					<form:input path="companykey" cssErrorClass="type_text err" class="type_text" size="20" maxlength="128"/>
				</div>
				<div class="errdv1_r">
					<form:errors path="companykey" id="" name="companykey.errors" cssClass="err" delimiter="</span><span name='companykey.errors' class='err'>"/>
				</div>
			</td>
		</tr>
		<tr>
			<th scope="row" class="ln_r lft"><label for="register">REGISTER</label></th>
			<td class="lft">
				<div class="errdv1_l">
					<form:input path="register" cssErrorClass="type_text err" class="type_text" size="20" maxlength="35"/>
				</div>
				<div class="errdv1_r">
					<form:errors path="register" id="" name="register.errors" cssClass="err" delimiter="</span><span name='register.errors' class='err'>"/>
				</div>
			</td>
		</tr>
		<tr>
			<th scope="row" class="ln_r lft"><label for="modifier">MODIFIER</label></th>
			<td class="lft">
				<div class="errdv1_l">
					<form:input path="modifier" cssErrorClass="type_text err" class="type_text" size="20" maxlength="35"/>
				</div>
				<div class="errdv1_r">
					<form:errors path="modifier" id="" name="modifier.errors" cssClass="err" delimiter="</span><span name='modifier.errors' class='err'>"/>
				</div>
			</td>
		</tr>
		-->
		</tbody>
	</table>
</div> <!--// bd_wrap -->

</form:form>

<br/><br/><br/>

</div><!-- // content -->
</div><!-- // layout_2 -->
