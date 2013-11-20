<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="net.study.common.Properties"%>
<%
String contextPath = request.getContextPath();
%>
<script type="text/javascript" src="<%=contextPath%>/common/js/jquery-1.7.2.min.js"></script>

<fieldset>
	<legend>messages 사용 예</legend>
	<input type="button" id="btn_new" name="btn_new" value="<spring:message code="button.new" />"/>
	<input type="button" id="btn_save" name="btn_save" value="<spring:message code="button.save" />"/>
	<input type="button" id="btn_modify" name="btn_modify" value="<spring:message code="button.modify" />"/>
	<input type="button" id="btn_delete" name="btn_delete" value="<spring:message code="button.delete" />"/>
	<input type="button" id="btn_list" name="btn_list" value="<spring:message code="button.list" />"/>
</fieldset>

<fieldset>
	<legend>Properties 사용 예</legend>
	<ul>
		<li><code>Properties.getInt("condition.firstRowIndex")</code> : <%=Properties.getInt("condition.firstRowIndex")%></li>
		<li><code>Properties.getInt("condition.rowCountPerPage")</code> : <%=Properties.getInt("condition.rowCountPerPage")%></li>
	</ul>
</fieldset>