<%
/**  
 * <pre>
 * 에디터 목록화면
 * </pre>
 
 * @version 2014.05.12
 * @author 김지홍 
 */
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
<!--
$(function(){ 
  $("#dataList").jqGrid({
    url:"<c:url value='editor.do?command=findList'/>",
    datatype: "json",
    mtype: "POST",
    postData: {
    	page : function() { return $("#dataList").getGridParam("page"); },
    	rowCountPerPage : function() { return $("#dataList").getGridParam("rowNum"); },
//    	sortType : function() { return $("#dataList").getGridParam("sortname"); },
//    	sortValue : function() { return $("#dataList").getGridParam("sortorder"); },
//    	searchDateType : function() { return $("[name=searchDateType]").val(); },
//    	searchStartDate : function() { return $("[name=searchStartDate]").val(); },
//    	searchEndDate : function() { return $("[name=searchEndDate]").val(); },
//    	searchStatus : function() { return $("[name=searchStatus]").val(); },
//    	searchType1 : function() { return $("[name=searchType1]").val(); },
//    	searchValue1 : function() { return $("[name=searchValue1]").val(); }//,
//    	searchType2 : function() { return $("[name=searchType2]").val(); },
//    	searchValue2 : function() { return $("[name=searchValue2]").val(); },
//    	searchType3 : function() { return $("[name=searchType3]").val(); },
//    	searchValue3 : function() { return $("[name=searchValue3]").val(); }
    },	    
    colNames:['BBSKEY', 'BBSTYPE', 'BBSCATEGORY', 'PRIORITY', 'TITLE', '다음에디터', 'CONTENTS', 'HIT'
			, 'GROUPID', 'SORTORDER', 'REPLYDEPTH', 'PARENTKEY', 'STATUS', 'SITETYPE', 'HOUSEKEY'
			, 'COMPANYKEY'],
    colModel :[ 
		{name:"bbskey", index:"bbskey", width:70},
		{name:"bbstype", index:"bbstype", width:20},
		{name:"bbscategory", index:"bbscategory", width:20},
		{name:"priority", index:"priority", width:5},
		{name:"title", index:"title", width:220, formatter:viewFormatter},
		{name:"link_daumeditor", index:"", width:70, formatter:daumeditorFormatter},
		{name:"contents", index:"contents", width:-1},
		{name:"hit", index:"hit", width:25, align:"right"},

		{name:"groupid", index:"groupid", width:25, align:"right"},
		{name:"sortorder", index:"sortorder", width:25, align:"right"},
		{name:"replydepth", index:"replydepth", width:25, align:"right"},
		{name:"parentkey", index:"parentkey", width:30},
		{name:"status", index:"status", width:50},
		{name:"sitetype", index:"sitetype", width:30},
		{name:"housekey", index:"housekey", width:30},

		{name:"companykey", index:"companykey", width:30}
    ],
    pager: "#dataPager",
    page: ("${condition.page}" > 1 ? "${condition.page}" : 1),
    rowNum:("${condition.rowCountPerPage}" > 1 ? "${condition.rowCountPerPage}" : 20),
    rowList:[10,20,30],
    height: 200,
    //autowidth: true,
    rownumbers: true,
    sortname: "registdate",
    sortorder: "desc",
    viewrecords: true,
    gridview: true,
    multiselect: true,
    caption: "",
    emptyrecords: "",
    loadonce: false,
    loadComplete: function(data) {
    	//noData(data);
	},
	jsonReader : {
        root: "data",
        page: "page",
        total: "total",
        records: "records",
        repeatitems: false,
        cell: "cell",
        //id: "id"
        id: "bbskey"
	},
	onSelectRow: function(ids) { //row 선택시 처리. ids는 선택한 row
		//clickListPage("listForm", "dataList", "", ids, "findDetail", "bbskey"); // checkbox 클릭시 반응하는 것 막기 위해 주석 처리함
	}
  }); 
});

// 상세페이지 이동 formatter
function viewFormatter(cellvalue, options, rowObject) {
	var linkStr = "<a href=\"editor.do?command=view&selected_key="+rowObject.bbskey+"\">"+rowObject.title+"</a>";
	return linkStr;
}

// 수정페이지 이동 formatter (daumeditor 사용)
function daumeditorFormatter(cellvalue, options, rowObject) {
	var linkStr = "<a href=\"editor.do?command=findDetail&selected_key="+rowObject.bbskey+"&editorType=daumeditor\">GO</a>";
	return linkStr;
}

/*
 * onload시 실행
 */
$(document).ready(function(){

	// 검색 수행시 화면전환 없이 jqGrid 호출 하도록 
	$("#listForm").submit(function() {
		$("#dataList").clearGridData();
		
	    var newUrl = "<c:url value='editor.do?command=findList'/>" + $(this).serialize();
	    $("#dataList").jqGrid("setGridParam","url", newUrl).trigger("reloadGrid");
	    return false;
	});
});
//-->
</script>

<div class="layout_2">
<div class="content">

<div class="btn_container">
	<a href="<c:url value='editor.do?command=entry'/>">[등록]</a>
	<a href="<c:url value='editor.do?command=entry&editorType=daumeditor'/>">[등록 (daumeditor)]</a>
</div>

<div class="gy_box">
<table id="dataList"><tr><td/></tr></table> 
<div id="dataPager"></div>
</div>

</div><!-- // content -->
</div><!-- // layout_2 -->
