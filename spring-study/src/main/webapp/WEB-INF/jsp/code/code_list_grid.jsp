<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page import="net.study.common.BizCondition"%>
<%
String contextPath = request.getContextPath();
BizCondition condition = (BizCondition)request.getAttribute("condition");
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

/*
 * 엑셀 다운로드
 */
function downloadExcel(){
	var form = document.forms["listForm"];
	form.command.value = "excelDownload";
	form.submit();
}

//-->
</script>
<div class="layout_2">
<div class="content">

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
	<br/>
	<input type="submit" name="search" value="검색" onclick="searchList();"/>
</fieldset>

</form>

<div class="btn_container">
	<a href="<%=contextPath%>/code.do?command=entry">[등록]</a>
	<a href="#" onclick="downloadExcel()">[엑셀 다운로드]</a>
</div>

<div>
<table id="dataList"><tr><td/></tr></table> 
<div id="dataPager"></div>
</div>

</div><!-- // content -->
</div><!-- // layout_2 -->

<script type="text/javascript">
<!--
$(function(){ 
  $("#dataList").jqGrid({
    url:"<%=contextPath%>/code.do?command=findListGrid",
    datatype: "json",
    mtype: "POST",
    postData: {
    	page : function() { return $("#dataList").getGridParam("page"); },
    	rowCountPerPage : function() { return $("#dataList").getGridParam("rowNum"); },
    	sortType : function() { return $("#dataList").getGridParam("sortname"); },
    	sortValue : function() { return $("#dataList").getGridParam("sortorder"); }/*,
    	searchType1 : function() { return $("[name=searchType1]").val(); },
    	searchValue1 : function() { return $("[name=searchValue1]").val(); },
    	searchType2 : function() { return $("[name=searchType2]").val(); },
    	searchValue2 : function() { return $("[name=searchValue2]").val(); }*/
    },	    
    colNames:['CODECATEGORYKEY', 'CODE', 'CODENAME', 'CODEEXPLAIN'
            , 'CODEENGNAME', 'STATUS', 'SORTORDER'
			],
    colModel :[ 
      	{name:"CODECATEGORYKEY", index:"codecategorykey", width:128},
   		{name:"CODE", index:"code", width:64, formatter:viewFormatter},
   		{name:"CODEEXPLAIN", index:"codeexplain", width:128},
   		{name:"CODENAME", index:"codename", width:128},
   		{name:"CODEENGNAME", index:"codeengname", width:128},
   		{name:"STATUS", index:"status", width:5},
   		{name:"SORTORDER", index:"sortorder", width:25, align:"right"} 
    ],
    pager: "#dataPager",
    page: ("<%=condition.getPage()%>" > 1 ? "<%=condition.getPage()%>" : 1),
    rowNum:("<%=condition.getRowCountPerPage()%>" > 1 ? "<%=condition.getRowCountPerPage()%>" : 20),
    rowList:[10,20,50,100],
    height: 200,
    //autowidth: true,
    rownumbers: true,
    sortname: "codecategorykey",
    sortorder: "asc",
    viewrecords: true,
    gridview: true,
    multiselect: true,
    caption: "",
    emptyrecords: "",
    loadonce: false,
    loadComplete: function(data) {
    	//noData(data)
	},
	jsonReader : {
        root: "data",
        page: "page",
        total: "total",
        records: "records",
        repeatitems: false,
        cell: "cell",
        //id: "id"
        id: "codecategorykey,code"
	},
	onSelectRow: function(ids) { //row 선택시 처리. ids는 선택한 row
	}
  }); 
});

//상세페이지 이동 formatter
function viewFormatter(cellvalue, options, rowObject) {
	var linkStr = "<a href=\"code.do?command=findDetail&codecategorykey="+rowObject.CODECATEGORYKEY+"&code="+rowObject.CODE+"\">"+rowObject.CODE+"</a>";
	return linkStr;
}

//-->
</script>
