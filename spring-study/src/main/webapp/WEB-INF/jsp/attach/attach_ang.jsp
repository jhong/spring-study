<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="btn_container">
	<a href="#/rst/attaches">[findList]</a>
</div>
<script type="text/javascript">
//location.href="#/rst/attaches"; // 첨부파일 목록
</script>

<div data-ng-view=""></div>

<%-- 
<div class="layout_2">
<div class="content">

<div data-ng-controller="AttachCtrl">
	<ul>
		<li>result.page : {{result.page}}</li>
		<li>result.total : {{result.total}}</li>
		<li>result.records : {{result.records}}</li>
		<li>result.msg : {{result.msg}}</li>
	</ul>
	<table class="view">
		<thead>
		<tr>
			<th>filename</th>
			<th>mimetype</th>
			<th>filesize</th>
			<th>uploaddate</th>
			<th>filekey</th>
			<th>refno</th>
		</tr>
		</thead>
		<tbody data-ng-repeat="data in result.data">
		<tr>
			<td><a href="<%=request.getContextPath()%>/attachAng.do?command=findDetail">{{data.filename}}</a></td>
			<td>{{data.mimetype}}</td>
			<td>{{data.filesize}}</td>
			<td>{{data.uploaddate}}</td>
			<td>{{data.filekey}}</td>
			<td>{{data.refno}}</td>
		</tr>
		</tbody>
	</table>
</div>

</div><!-- // content -->
</div><!-- // layout_2 -->
--%>