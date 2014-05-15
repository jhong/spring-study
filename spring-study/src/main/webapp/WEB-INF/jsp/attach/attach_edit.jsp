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
<%@ page import="net.study.attach.AttachVo"%>
<%
AttachVo form = (AttachVo)request.getAttribute("attachVo");
BizCondition condition = (BizCondition)request.getAttribute("condition");
%>

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
	<input type="button" value="목록으로" onclick="goList();"/>
</div>

<form:form commandName="attachVo" id="subForm" name="subForm" method="post" action="${pageContext.request.contextPath}/attach.do" enctype="multipart/form-data">
<input type="hidden" name="command" />
<input type="hidden" name="dbmode" value="<%=condition.get("dbmode")%>"/>

<!-- sub title -->
<div class="sub_title">
	<h2>첨부파일</h2>
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
			<th scope="row" class="ln_r lft"><label for="file">파일</label></th>
			<td class="lft">
				<div class="errdv1_l">
					<input type="file" name="file" id="file" class="type_text" size="60" />
				</div>
				<div class="errdv1_r">
				</div>
			</td>
		</tr>
		<%
		String mimetype = form.getMimetype();
		if (mimetype != null && mimetype.indexOf("image") >= 0) {
		%>
		<tr>
			<th scope="row" class="ln_r lft"><label>미리보기</label></th>
			<td class="lft">
				<div class="errdv1_l">
					<img src="<%=request.getContextPath()%>/attach.do?command=downloadImage&filekey=<%=form.getFilekey()%>"/>
				</div>
				<div class="errdv1_r">
				</div>
			</td>
		</tr>
		<%
		}
		%>
		<tr>
			<th scope="row" class="ln_r lft"><label for="filekey">FILEKEY</label></th>
			<td class="lft">
				<div class="errdv1_l">
					<form:input path="filekey" cssErrorClass="type_text err" class="type_text" size="20" maxlength="128"/>
				</div>
				<div class="errdv1_r">
					<form:errors path="filekey" id="" name="filekey.errors" cssClass="err" delimiter="</span><span name='filekey.errors' class='err'>"/>
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
			<th scope="row" class="ln_r lft"><label for="filename">FILENAME</label></th>
			<td class="lft">
				<div class="errdv1_l">
					<form:input path="filename" cssErrorClass="type_text err" class="type_text" size="20" maxlength="128"/>
				</div>
				<div class="errdv1_r">
					<form:errors path="filename" id="" name="filename.errors" cssClass="err" delimiter="</span><span name='filename.errors' class='err'>"/>
				</div>
			</td>
		</tr>
		<tr>
			<th scope="row" class="ln_r lft"><label for="mimetype">MIMETYPE</label></th>
			<td class="lft">
				<div class="errdv1_l">
					<form:input path="mimetype" cssErrorClass="type_text err" class="type_text" size="20" maxlength="128"/>
				</div>
				<div class="errdv1_r">
					<form:errors path="mimetype" id="" name="mimetype.errors" cssClass="err" delimiter="</span><span name='mimetype.errors' class='err'>"/>
				</div>
			</td>
		</tr>
		<tr>
			<th scope="row" class="ln_r lft"><label for="charset">CHARSET</label></th>
			<td class="lft">
				<div class="errdv1_l">
					<form:input path="charset" cssErrorClass="type_text err" class="type_text" size="20" maxlength="35"/>
				</div>
				<div class="errdv1_r">
					<form:errors path="charset" id="" name="charset.errors" cssClass="err" delimiter="</span><span name='charset.errors' class='err'>"/>
				</div>
			</td>
		</tr>
		<tr>
			<th scope="row" class="ln_r lft"><label for="filesize">FILESIZE</label></th>
			<td class="lft">
				<div class="errdv1_l">
					<form:input path="filesize" cssErrorClass="type_text err" class="type_text" size="10" maxlength="25"/>
				</div>
				<div class="errdv1_r">
					<form:errors path="filesize" id="" name="filesize.errors" cssClass="err" delimiter="</span><span name='filesize.errors' class='err'>"/>
				</div>
			</td>
		</tr>
		<tr>
			<th scope="row" class="ln_r lft"><label for="filedesc">FILEDESC</label></th>
			<td class="lft">
				<div class="errdv1_l">
					<form:input path="filedesc" cssErrorClass="type_text err" class="type_text" size="20" maxlength="1024"/>
				</div>
				<div class="errdv1_r">
					<form:errors path="filedesc" id="" name="filedesc.errors" cssClass="err" delimiter="</span><span name='filedesc.errors' class='err'>"/>
				</div>
			</td>
		</tr>
		<tr>
			<th scope="row" class="ln_r lft"><label for="refNo">REF_NO</label></th>
			<td class="lft">
				<div class="errdv1_l">
					<form:input path="refNo" cssErrorClass="type_text err" class="type_text" size="20" maxlength="128"/>
				</div>
				<div class="errdv1_r">
					<form:errors path="refNo" id="" name="refNo.errors" cssClass="err" delimiter="</span><span name='refNo.errors' class='err'>"/>
				</div>
			</td>
		</tr>
		<tr>
			<th scope="row" class="ln_r lft"><label for="refNo2">REF_NO2</label></th>
			<td class="lft">
				<div class="errdv1_l">
					<form:input path="refNo2" cssErrorClass="type_text err" class="type_text" size="20" maxlength="128"/>
				</div>
				<div class="errdv1_r">
					<form:errors path="refNo2" id="" name="refNo2.errors" cssClass="err" delimiter="</span><span name='refNo2.errors' class='err'>"/>
				</div>
			</td>
		</tr>
		<tr>
			<th scope="row" class="ln_r lft"><label for="refNoSeq">REF_NO_SEQ</label></th>
			<td class="lft">
				<div class="errdv1_l">
					<form:input path="refNoSeq" cssErrorClass="type_text err" class="type_text" size="10" maxlength="7"/>
				</div>
				<div class="errdv1_r">
					<form:errors path="refNoSeq" id="" name="refNoSeq.errors" cssClass="err" delimiter="</span><span name='refNoSeq.errors' class='err'>"/>
				</div>
			</td>
		</tr>
		<tr>
			<th scope="row" class="ln_r lft"><label for="userid">USERID</label></th>
			<td class="lft">
				<div class="errdv1_l">
					<form:input path="userid" cssErrorClass="type_text err" class="type_text" size="20" maxlength="35"/>
				</div>
				<div class="errdv1_r">
					<form:errors path="userid" id="" name="userid.errors" cssClass="err" delimiter="</span><span name='userid.errors' class='err'>"/>
				</div>
			</td>
		</tr>
		</tbody>
	</table>
</div> <!--// bd_wrap -->

</form:form>

</div><!-- // content -->
</div><!-- // layout_2 -->
