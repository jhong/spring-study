<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="net.study.common.BizCondition" %>
<%@ page import="net.study.common.BizConst" %>
<%@ page import="net.study.editor.EditorVo"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="java.util.Map" %>
<%@ page import="net.study.common.BizCondition" %>
<%@ page import="net.study.common.BizConst" %>
<%@ page import="net.study.attach.AttachVo"%>
<%
AttachVo form = (AttachVo)request.getAttribute("attachVo");
BizCondition condition = (BizCondition)request.getAttribute("condition");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>CKEditor - 이미지 첨부</title>
<script type="text/javascript">
<%
if (form != null) {
%>
window.parent.CKEDITOR.tools.callFunction("<%=condition.getString("funcNum")%>", "<%=request.getContextPath()%>/attach.do?command=downloadImage&filekey=<%=form.getFilekey()%>", "");
<%
}
%>
</script>
</head>
<body>
<%--
<div class="wrapper">
	<div class="header">
		<h1>파일 첨부</h1>
	</div>	
	<div class="body">
<!-- sub title -->
<div class="sub_title">
	<h2>첨부파일</h2>
</div>
<!--// sub title-->
<div class="bd_wrap">

<form:form commandName="attachVo" id="subForm" name="subForm" method="post" action="${pageContext.request.contextPath}/attach.do" enctype="multipart/form-data">

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
					<input type="submit" value="업로드"/>
				</div>
				<div class="errdv1_r">
				</div>
			</td>
		</tr>
		<%
		if (form != null) {
			String mimetype = form.getMimetype();
			if (mimetype != null && mimetype.indexOf("image") >= 0) {
		%>
		<tr>
			<th scope="row" class="ln_r lft"><label>미리보기</label></th>
			<td class="lft">
				<div class="errdv1_l">
					<img src="<%=request.getContextPath()%>/attach.do?command=downloadImage&filekey=<%=form.getFilekey()%>" height="70px"/>
				</div>
				<div class="errdv1_r">
				</div>
			</td>
		</tr>
		<%
			}
		%>
		<tr>
			<th scope="row" class="ln_r lft"><label>URL</label></th>
			<td class="lft">
				<div class="errdv1_l">
					<%=request.getContextPath()%>/attach.do?command=downloadImage&filekey=<%=form.getFilekey()%>
				</div>
				<div class="errdv1_r">
				</div>
			</td>
		</tr>
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
		<%
		}
		%>
		</tbody>
	</table>
</form:form>

</div> <!--// bd_wrap -->
	</div>
</div>
--%>
</body>
</html>
